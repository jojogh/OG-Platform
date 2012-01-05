/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.marketdatasnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.marketdatasnapshot.impl.ManageableMarketDataSnapshot;
import com.opengamma.id.ObjectId;
import com.opengamma.id.ObjectIdentifiable;
import com.opengamma.master.AbstractDocument;
import com.opengamma.master.AbstractSearchRequest;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.PublicSPI;
import com.opengamma.util.RegexUtils;

/**
 * Request for searching for market data snapshots.
 * <p>
 * Documents will be returned that match the search criteria.
 * This class provides the ability to page the results and to search
 * as at a specific version and correction instant.
 */
@PublicSPI
@BeanDefinition
public class MarketDataSnapshotSearchRequest extends AbstractSearchRequest implements Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The set of marketDataSnapshot object identifiers, null to not limit by marketDataSnapshot object identifiers.
   * Note that an empty set will return no marketDataSnapshots.
   */
  @PropertyDefinition(set = "manual")
  private List<ObjectId> _snapshotIds;
  /**
   * The market data snapshot name, wildcards allowed, null to not match on name.
   */
  @PropertyDefinition
  private String _name;
  /**
   * Whether to include the snapshot data in the search results.
   * Set to true to include the data, or false to omit it. Defaults to true.
   * Note that a master may ignore this value and always return the full data.
   */
  @PropertyDefinition
  private boolean _includeData = true;

  /**
   * Creates an instance.
   */
  public MarketDataSnapshotSearchRequest() {
  }

  //-------------------------------------------------------------------------
  /**
   * Adds a single marketDataSnapshot object identifier to the set.
   * 
   * @param marketDataSnapshotId  the marketDataSnapshot object identifier to add, not null
   */
  public void addMarketDataSnapshotId(ObjectIdentifiable marketDataSnapshotId) {
    ArgumentChecker.notNull(marketDataSnapshotId, "marketDataSnapshotId");
    if (_snapshotIds == null) {
      _snapshotIds = new ArrayList<ObjectId>();
    }
    _snapshotIds.add(marketDataSnapshotId.getObjectId());
  }

  /**
   * Sets the set of marketDataSnapshot object identifiers, null to not limit by marketDataSnapshot object identifiers.
   * Note that an empty set will return no marketDataSnapshots.
   * 
   * @param marketDataSnapshotIds  the new marketDataSnapshot identifiers, null clears the marketDataSnapshot id search
   */
  public void setSnapshotIds(Iterable<? extends ObjectIdentifiable> marketDataSnapshotIds) {
    if (marketDataSnapshotIds == null) {
      _snapshotIds = null;
    } else {
      _snapshotIds = new ArrayList<ObjectId>();
      for (ObjectIdentifiable marketDataSnapshotId : marketDataSnapshotIds) {
        _snapshotIds.add(marketDataSnapshotId.getObjectId());
      }
    }
  }

  //-------------------------------------------------------------------------
  @Override
  public boolean matches(final AbstractDocument obj) {
    if (obj instanceof MarketDataSnapshotDocument == false) {
      return false;
    }
    final MarketDataSnapshotDocument document = (MarketDataSnapshotDocument) obj;
    final ManageableMarketDataSnapshot marketDataSnapshot = document.getSnapshot();
    if (getSnapshotIds() != null && getSnapshotIds().contains(document.getObjectId()) == false) {
      return false;
    }
    if (getName() != null && RegexUtils.wildcardMatch(getName(), marketDataSnapshot.getName()) == false) {
      return false;
    }
    return true;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MarketDataSnapshotSearchRequest}.
   * @return the meta-bean, not null
   */
  public static MarketDataSnapshotSearchRequest.Meta meta() {
    return MarketDataSnapshotSearchRequest.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(MarketDataSnapshotSearchRequest.Meta.INSTANCE);
  }

  @Override
  public MarketDataSnapshotSearchRequest.Meta metaBean() {
    return MarketDataSnapshotSearchRequest.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -168607148:  // snapshotIds
        return getSnapshotIds();
      case 3373707:  // name
        return getName();
      case 274670706:  // includeData
        return isIncludeData();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -168607148:  // snapshotIds
        setSnapshotIds((List<ObjectId>) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
      case 274670706:  // includeData
        setIncludeData((Boolean) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MarketDataSnapshotSearchRequest other = (MarketDataSnapshotSearchRequest) obj;
      return JodaBeanUtils.equal(getSnapshotIds(), other.getSnapshotIds()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(isIncludeData(), other.isIncludeData()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getSnapshotIds());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIncludeData());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the set of marketDataSnapshot object identifiers, null to not limit by marketDataSnapshot object identifiers.
   * Note that an empty set will return no marketDataSnapshots.
   * @return the value of the property
   */
  public List<ObjectId> getSnapshotIds() {
    return _snapshotIds;
  }

  /**
   * Gets the the {@code snapshotIds} property.
   * Note that an empty set will return no marketDataSnapshots.
   * @return the property, not null
   */
  public final Property<List<ObjectId>> snapshotIds() {
    return metaBean().snapshotIds().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data snapshot name, wildcards allowed, null to not match on name.
   * @return the value of the property
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the market data snapshot name, wildcards allowed, null to not match on name.
   * @param name  the new value of the property
   */
  public void setName(String name) {
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets whether to include the snapshot data in the search results.
   * Set to true to include the data, or false to omit it. Defaults to true.
   * Note that a master may ignore this value and always return the full data.
   * @return the value of the property
   */
  public boolean isIncludeData() {
    return _includeData;
  }

  /**
   * Sets whether to include the snapshot data in the search results.
   * Set to true to include the data, or false to omit it. Defaults to true.
   * Note that a master may ignore this value and always return the full data.
   * @param includeData  the new value of the property
   */
  public void setIncludeData(boolean includeData) {
    this._includeData = includeData;
  }

  /**
   * Gets the the {@code includeData} property.
   * Set to true to include the data, or false to omit it. Defaults to true.
   * Note that a master may ignore this value and always return the full data.
   * @return the property, not null
   */
  public final Property<Boolean> includeData() {
    return metaBean().includeData().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MarketDataSnapshotSearchRequest}.
   */
  public static class Meta extends AbstractSearchRequest.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code snapshotIds} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<ObjectId>> _snapshotIds = DirectMetaProperty.ofReadWrite(
        this, "snapshotIds", MarketDataSnapshotSearchRequest.class, (Class) List.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", MarketDataSnapshotSearchRequest.class, String.class);
    /**
     * The meta-property for the {@code includeData} property.
     */
    private final MetaProperty<Boolean> _includeData = DirectMetaProperty.ofReadWrite(
        this, "includeData", MarketDataSnapshotSearchRequest.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "snapshotIds",
        "name",
        "includeData");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -168607148:  // snapshotIds
          return _snapshotIds;
        case 3373707:  // name
          return _name;
        case 274670706:  // includeData
          return _includeData;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends MarketDataSnapshotSearchRequest> builder() {
      return new DirectBeanBuilder<MarketDataSnapshotSearchRequest>(new MarketDataSnapshotSearchRequest());
    }

    @Override
    public Class<? extends MarketDataSnapshotSearchRequest> beanType() {
      return MarketDataSnapshotSearchRequest.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code snapshotIds} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<ObjectId>> snapshotIds() {
      return _snapshotIds;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code includeData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> includeData() {
      return _includeData;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
