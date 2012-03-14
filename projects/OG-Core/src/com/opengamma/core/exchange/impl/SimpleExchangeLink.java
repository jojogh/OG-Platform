/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.core.exchange.impl;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.AbstractLink;
import com.opengamma.core.exchange.Exchange;
import com.opengamma.core.exchange.ExchangeLink;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.ObjectId;
import com.opengamma.id.UniqueId;
import com.opengamma.util.ArgumentChecker;

/**
 * A flexible link between an object and an exchange.
 * <p>
 * A exchange link represents a connection from an entity to an exchange.
 * The connection can be held by an {@code ObjectId} or an {@code ExternalIdBundle}.
 * <p>
 * This class is mutable and not thread-safe.
 * It is intended to be used in the engine via the read-only {@code ExchangeLink} interface.
 */
@BeanDefinition
public class SimpleExchangeLink extends AbstractLink<Exchange>
    implements ExchangeLink {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * Obtains an instance from an exchange, storing the object identifier
   * if possible and the external identifier bundle if not.
   * 
   * @param exchange  the exchange to store, not null
   * @return the link with one identifier set, not null
   */
  public static SimpleExchangeLink of(Exchange exchange) {
    ArgumentChecker.notNull(exchange, "exchange");
    SimpleExchangeLink link = new SimpleExchangeLink();
    if (exchange.getUniqueId() != null) {
      link.setObjectId(exchange.getUniqueId().getObjectId());
    } else {
      link.setExternalId(exchange.getExternalIdBundle());
    }
    return link;
  }

  /**
   * Obtains an instance from an exchange, storing the external identifier bundle.
   * 
   * @param exchange  the exchange to store, not null
   * @return the link with identifier bundle set, not null
   */
  public static SimpleExchangeLink ofBundleId(Exchange exchange) {
    ArgumentChecker.notNull(exchange, "exchange");
    SimpleExchangeLink link = new SimpleExchangeLink(exchange.getExternalIdBundle());
    link.setExternalId(exchange.getExternalIdBundle());
    return link;
  }

  //-------------------------------------------------------------------------
  /**
   * Creates an new instance.
   */
  public SimpleExchangeLink() {
    super();
  }

  /**
   * Creates a link from an object identifier.
   * 
   * @param objectId  the object identifier, not null
   */
  public SimpleExchangeLink(final ObjectId objectId) {
    super(objectId);
  }

  /**
   * Creates a link from a unique identifier, only storing the object identifier.
   * 
   * @param uniqueId  the unique identifier, not null
   */
  public SimpleExchangeLink(final UniqueId uniqueId) {
    super(uniqueId);
  }

  /**
   * Creates a link from an external identifier.
   * 
   * @param externalId  the external identifier, not null
   */
  public SimpleExchangeLink(final ExternalId externalId) {
    super(externalId);
  }

  /**
   * Creates a link from an external identifier bundle.
   * 
   * @param bundle  the identifier bundle, not null
   */
  public SimpleExchangeLink(final ExternalIdBundle bundle) {
    super(bundle);
  }

  /**
   * Clones the specified link, sharing the target exchange.
   * 
   * @param linkToClone  the link to clone, not null
   */
  public SimpleExchangeLink(ExchangeLink linkToClone) {
    super();
    setObjectId(linkToClone.getObjectId());
    setExternalId(linkToClone.getExternalId());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SimpleExchangeLink}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static SimpleExchangeLink.Meta meta() {
    return SimpleExchangeLink.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(SimpleExchangeLink.Meta.INSTANCE);
  }

  @Override
  public SimpleExchangeLink.Meta metaBean() {
    return SimpleExchangeLink.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SimpleExchangeLink}.
   */
  public static class Meta extends AbstractLink.Meta<Exchange> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends SimpleExchangeLink> builder() {
      return new DirectBeanBuilder<SimpleExchangeLink>(new SimpleExchangeLink());
    }

    @Override
    public Class<? extends SimpleExchangeLink> beanType() {
      return SimpleExchangeLink.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
