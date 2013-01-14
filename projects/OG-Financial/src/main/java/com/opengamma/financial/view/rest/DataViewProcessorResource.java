/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.view.rest;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.fudgemsg.FudgeContext;

import com.opengamma.core.config.impl.DataConfigSourceResource;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.engine.marketdata.ExternalIdBundleLookup;
import com.opengamma.engine.marketdata.snapshot.MarketDataSnapshotter;
import com.opengamma.engine.view.ViewProcess;
import com.opengamma.engine.view.ViewProcessor;
import com.opengamma.engine.view.client.ViewClient;
import com.opengamma.financial.analytics.volatility.cube.VolatilityCubeDefinitionSource;
import com.opengamma.financial.marketdatasnapshot.MarketDataSnapshotterImpl;
import com.opengamma.financial.rest.AbstractRestfulJmsResultPublisherExpiryJob;
import com.opengamma.id.UniqueId;
import com.opengamma.livedata.UserPrincipal;
import com.opengamma.transport.jaxrs.FudgeRest;
import com.opengamma.util.jms.JmsConnector;
import com.opengamma.util.rest.AbstractDataResource;

/**
 * RESTful resource for a {@link ViewProcessor}.
 */
public class DataViewProcessorResource extends AbstractDataResource {

  /**
   * The period after which, if a view client has not been accessed, it may be shut down.
   */
  public static final long VIEW_CLIENT_TIMEOUT_MILLIS = 30000;
  /**
   * URI path to the config source.
   */
  public static final String PATH_CONFIG_SOURCE = "configSource";
  /**
   * URI path to the market data repository.
   */
  public static final String PATH_NAMED_MARKET_DATA_SPEC_REPOSITORY = "namedMarketDataSpecRepository";
  /**
   * URI path to the name.
   */
  public static final String PATH_NAME = "name";
  /**
   * URI path to the clients.
   */
  public static final String PATH_CLIENTS = "clients";
  /**
   * URI path to the processes.
   */
  public static final String PATH_PROCESSES = "processes";
  /**
   * URI path to the cycles.
   */
  public static final String PATH_CYCLES = "cycles";
  /**
   * URI path to the snapshotter.
   */
  public static final String PATH_SNAPSHOTTER = "marketDataSnapshotter";

  /**
   * The identifier lookup.
   */
  private final ExternalIdBundleLookup _identifierLookup;
  /**
   * The view processor.
   */
  private final ViewProcessor _viewProcessor;
  /**
   * The volatility cube definition.
   */
  private final VolatilityCubeDefinitionSource _volatilityCubeDefinitionSource;
  /**
   * The connection factory.
   */
  private final JmsConnector _jmsConnector;
  /**
   * The executor service.
   */
  private final ScheduledExecutorService _scheduler;
  /**
   * The stale view client expiry job. 
   */
  @SuppressWarnings("unused")
  private final AbstractRestfulJmsResultPublisherExpiryJob<DataViewClientResource> _expiryJob;
  /**
   * The cycle manager.
   */
  private final AtomicReference<DataViewCycleManagerResource> _cycleManagerResource = new AtomicReference<DataViewCycleManagerResource>();
  /**
   * The view clients.
   */
  private final ConcurrentMap<UniqueId, DataViewClientResource> _createdViewClients = new ConcurrentHashMap<UniqueId, DataViewClientResource>();

  /**
   * Creates an instance.
   * 
   * @param securitySource a security source to use for resolving things, null to not resolve
   * @param viewProcessor the view processor, not null
   * @param volatilityCubeDefinitionSource the volatility cube, not null
   * @param jmsConnector the JMS connector, not null
   * @param fudgeContext the Fudge context, not null
   * @param scheduler the scheduler, not null
   */
  public DataViewProcessorResource(final SecuritySource securitySource, final ViewProcessor viewProcessor, final VolatilityCubeDefinitionSource volatilityCubeDefinitionSource,
      final JmsConnector jmsConnector, final FudgeContext fudgeContext, final ScheduledExecutorService scheduler) {
    _identifierLookup = new ExternalIdBundleLookup(securitySource);
    _viewProcessor = viewProcessor;
    _volatilityCubeDefinitionSource = volatilityCubeDefinitionSource;
    _jmsConnector = jmsConnector;
    _scheduler = scheduler;
    _expiryJob = new AbstractRestfulJmsResultPublisherExpiryJob<DataViewClientResource>(VIEW_CLIENT_TIMEOUT_MILLIS, scheduler) {
      @Override
      protected Collection<DataViewClientResource> getResources() {
        return _createdViewClients.values();
      }
    };
  }

  /**
   * Gets the viewProcessor field.
   * @return the viewProcessor
   */
  public ViewProcessor getViewProcessor() {
    return _viewProcessor;
  }

  //-------------------------------------------------------------------------
  @GET
  public Response getHateaos(@Context final UriInfo uriInfo) {
    return hateoasResponse(uriInfo);
  }

  @GET
  @Path(PATH_NAME)
  public Response getName() {
    return responseOk(_viewProcessor.getName());
  }

  @Path(PATH_CONFIG_SOURCE)
  public DataConfigSourceResource getConfigSource() {
    return new DataConfigSourceResource(_viewProcessor.getConfigSource());
  }

  @Path(PATH_NAMED_MARKET_DATA_SPEC_REPOSITORY)
  public DataNamedMarketDataSpecificationRepositoryResource getLiveMarketDataSourceRegistry() {
    return new DataNamedMarketDataSpecificationRepositoryResource(_viewProcessor.getNamedMarketDataSpecificationRepository());
  }

  @Path(PATH_SNAPSHOTTER)
  public DataMarketDataSnapshotterResource getMarketDataSnapshotterImpl() {
    final MarketDataSnapshotter snp = new MarketDataSnapshotterImpl(_identifierLookup, _volatilityCubeDefinitionSource);
    return new DataMarketDataSnapshotterResource(_viewProcessor, snp);
  }
  
  //-------------------------------------------------------------------------
  @Path(PATH_PROCESSES + "/{viewProcessId}")
  public DataViewProcessResource getViewProcess(@PathParam("viewProcessId") final String viewProcessId) {
    final ViewProcess view = _viewProcessor.getViewProcess(UniqueId.parse(viewProcessId));
    return new DataViewProcessResource(view);
  }

  //-------------------------------------------------------------------------
  @Path(PATH_CLIENTS + "/{viewClientId}")
  public DataViewClientResource getViewClient(@Context final UriInfo uriInfo, @PathParam("viewClientId") final String viewClientIdString) {
    final UniqueId viewClientId = UniqueId.parse(viewClientIdString);
    final DataViewClientResource viewClientResource = _createdViewClients.get(viewClientId);
    if (viewClientResource != null) {
      return viewClientResource;
    }
    final ViewClient viewClient = _viewProcessor.getViewClient(viewClientId);
    final URI viewProcessorUri = getViewProcessorUri(uriInfo);
    return createViewClientResource(viewClient, viewProcessorUri);
  }

  @POST
  @Path(PATH_CLIENTS)
  @Consumes(FudgeRest.MEDIA)
  public Response createViewClient(@Context final UriInfo uriInfo, final UserPrincipal user) {
    final ViewClient client = _viewProcessor.createViewClient(user);
    final URI viewProcessorUri = getViewProcessorUri(uriInfo);
    // Required for heartbeating, but also acts as an optimisation for getViewClient because view clients created
    // through the REST API should be accessed again through the same API, potentially many times.  
    final DataViewClientResource viewClientResource = createViewClientResource(client, viewProcessorUri);
    _createdViewClients.put(client.getUniqueId(), viewClientResource);
    final URI createdUri = uriClient(uriInfo.getRequestUri(), client.getUniqueId());
    return responseCreated(createdUri);
  }

  //-------------------------------------------------------------------------
  @Path(PATH_CYCLES)
  public DataViewCycleManagerResource getViewCycleManager(@Context final UriInfo uriInfo) {
    return getOrCreateDataViewCycleManagerResource(getViewProcessorUri(uriInfo));
  }

  //-------------------------------------------------------------------------
  public static URI uriViewProcess(final URI baseUri, final UniqueId viewProcessId) {
    // WARNING: '/' characters could well appear in the view name
    // There is a bug(?) in UriBuilder where, even though segment() is meant to treat the item as a single path segment
    // and therefore encode '/' characters, it does not encode '/' characters which come from a variable substitution.
    return UriBuilder.fromUri(baseUri).path("processes").segment(viewProcessId.toString()).build();
  }

  public static URI uriClient(final URI clientsBaseUri, final UniqueId viewClientId) {
    return UriBuilder.fromUri(clientsBaseUri).segment(viewClientId.toString()).build();
  }

  private URI getViewProcessorUri(final UriInfo uriInfo) {
    return uriInfo.getBaseUri().resolve(UriBuilder.fromUri(uriInfo.getMatchedURIs().get(1)).build());
  }

  private DataViewCycleManagerResource getOrCreateDataViewCycleManagerResource(final URI viewProcessorUri) {
    DataViewCycleManagerResource resource = _cycleManagerResource.get();
    if (resource == null) {
      final URI baseUri = UriBuilder.fromUri(viewProcessorUri).path(PATH_CYCLES).build();
      final DataViewCycleManagerResource newResource = new DataViewCycleManagerResource(baseUri, _viewProcessor.getViewCycleManager());
      if (_cycleManagerResource.compareAndSet(null, newResource)) {
        resource = newResource;
        final DataViewCycleManagerResource.ReleaseExpiredReferencesRunnable task = newResource.createReleaseExpiredReferencesTask();
        task.setScheduler(_scheduler);
      } else {
        resource = _cycleManagerResource.get();
      }
    }
    return resource;
  }

  private DataViewClientResource createViewClientResource(final ViewClient viewClient, final URI viewProcessorUri) {
    final DataViewCycleManagerResource cycleManagerResource = getOrCreateDataViewCycleManagerResource(viewProcessorUri);
    return new DataViewClientResource(viewClient, cycleManagerResource, _jmsConnector, _scheduler);
  }

}
