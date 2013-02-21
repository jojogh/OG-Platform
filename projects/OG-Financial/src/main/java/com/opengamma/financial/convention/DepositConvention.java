/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

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

import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.money.Currency;

/**
 *
 */
@BeanDefinition
public class DepositConvention extends Convention {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The day count.
   */
  @PropertyDefinition(validate = "notNull")
  private DayCount _dayCount;

  /**
   * The business day convention.
   */
  @PropertyDefinition(validate = "notNull")
  private BusinessDayConvention _businessDayConvention;

  /**
   * The number of days to settle.
   */
  @PropertyDefinition
  private int _daysToSettle;

  /**
   * Should dates follow the end-of-month rule.
   */
  @PropertyDefinition
  private boolean _isEOM;

  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _currency;

  /**
   * The region calendar.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalId _regionCalendar;

  /**
   * For the builder.
   */
  public DepositConvention() {
    super();
  }

  public DepositConvention(final String name, final ExternalIdBundle externalIdBundle, final DayCount dayCount, final BusinessDayConvention businessDayConvention,
      final int daysToSettle, final boolean isEOM, final Currency currency, final ExternalId regionCalendar) {
    super(name, externalIdBundle);
    setDayCount(dayCount);
    setBusinessDayConvention(businessDayConvention);
    setDaysToSettle(daysToSettle);
    setIsEOM(isEOM);
    setCurrency(currency);
    setRegionCalendar(regionCalendar);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DepositConvention}.
   * @return the meta-bean, not null
   */
  public static DepositConvention.Meta meta() {
    return DepositConvention.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(DepositConvention.Meta.INSTANCE);
  }

  @Override
  public DepositConvention.Meta metaBean() {
    return DepositConvention.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1905311443:  // dayCount
        return getDayCount();
      case -1002835891:  // businessDayConvention
        return getBusinessDayConvention();
      case 379523357:  // daysToSettle
        return getDaysToSettle();
      case 100464505:  // isEOM
        return isIsEOM();
      case 575402001:  // currency
        return getCurrency();
      case 1932874322:  // regionCalendar
        return getRegionCalendar();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1905311443:  // dayCount
        setDayCount((DayCount) newValue);
        return;
      case -1002835891:  // businessDayConvention
        setBusinessDayConvention((BusinessDayConvention) newValue);
        return;
      case 379523357:  // daysToSettle
        setDaysToSettle((Integer) newValue);
        return;
      case 100464505:  // isEOM
        setIsEOM((Boolean) newValue);
        return;
      case 575402001:  // currency
        setCurrency((Currency) newValue);
        return;
      case 1932874322:  // regionCalendar
        setRegionCalendar((ExternalId) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_dayCount, "dayCount");
    JodaBeanUtils.notNull(_businessDayConvention, "businessDayConvention");
    JodaBeanUtils.notNull(_currency, "currency");
    JodaBeanUtils.notNull(_regionCalendar, "regionCalendar");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DepositConvention other = (DepositConvention) obj;
      return JodaBeanUtils.equal(getDayCount(), other.getDayCount()) &&
          JodaBeanUtils.equal(getBusinessDayConvention(), other.getBusinessDayConvention()) &&
          JodaBeanUtils.equal(getDaysToSettle(), other.getDaysToSettle()) &&
          JodaBeanUtils.equal(isIsEOM(), other.isIsEOM()) &&
          JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getRegionCalendar(), other.getRegionCalendar()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getDayCount());
    hash += hash * 31 + JodaBeanUtils.hashCode(getBusinessDayConvention());
    hash += hash * 31 + JodaBeanUtils.hashCode(getDaysToSettle());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsEOM());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRegionCalendar());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the day count.
   * @return the value of the property, not null
   */
  public DayCount getDayCount() {
    return _dayCount;
  }

  /**
   * Sets the day count.
   * @param dayCount  the new value of the property, not null
   */
  public void setDayCount(DayCount dayCount) {
    JodaBeanUtils.notNull(dayCount, "dayCount");
    this._dayCount = dayCount;
  }

  /**
   * Gets the the {@code dayCount} property.
   * @return the property, not null
   */
  public final Property<DayCount> dayCount() {
    return metaBean().dayCount().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the business day convention.
   * @return the value of the property, not null
   */
  public BusinessDayConvention getBusinessDayConvention() {
    return _businessDayConvention;
  }

  /**
   * Sets the business day convention.
   * @param businessDayConvention  the new value of the property, not null
   */
  public void setBusinessDayConvention(BusinessDayConvention businessDayConvention) {
    JodaBeanUtils.notNull(businessDayConvention, "businessDayConvention");
    this._businessDayConvention = businessDayConvention;
  }

  /**
   * Gets the the {@code businessDayConvention} property.
   * @return the property, not null
   */
  public final Property<BusinessDayConvention> businessDayConvention() {
    return metaBean().businessDayConvention().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of days to settle.
   * @return the value of the property
   */
  public int getDaysToSettle() {
    return _daysToSettle;
  }

  /**
   * Sets the number of days to settle.
   * @param daysToSettle  the new value of the property
   */
  public void setDaysToSettle(int daysToSettle) {
    this._daysToSettle = daysToSettle;
  }

  /**
   * Gets the the {@code daysToSettle} property.
   * @return the property, not null
   */
  public final Property<Integer> daysToSettle() {
    return metaBean().daysToSettle().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets should dates follow the end-of-month rule.
   * @return the value of the property
   */
  public boolean isIsEOM() {
    return _isEOM;
  }

  /**
   * Sets should dates follow the end-of-month rule.
   * @param isEOM  the new value of the property
   */
  public void setIsEOM(boolean isEOM) {
    this._isEOM = isEOM;
  }

  /**
   * Gets the the {@code isEOM} property.
   * @return the property, not null
   */
  public final Property<Boolean> isEOM() {
    return metaBean().isEOM().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property, not null
   */
  public void setCurrency(Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<Currency> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the region calendar.
   * @return the value of the property, not null
   */
  public ExternalId getRegionCalendar() {
    return _regionCalendar;
  }

  /**
   * Sets the region calendar.
   * @param regionCalendar  the new value of the property, not null
   */
  public void setRegionCalendar(ExternalId regionCalendar) {
    JodaBeanUtils.notNull(regionCalendar, "regionCalendar");
    this._regionCalendar = regionCalendar;
  }

  /**
   * Gets the the {@code regionCalendar} property.
   * @return the property, not null
   */
  public final Property<ExternalId> regionCalendar() {
    return metaBean().regionCalendar().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DepositConvention}.
   */
  public static class Meta extends Convention.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code dayCount} property.
     */
    private final MetaProperty<DayCount> _dayCount = DirectMetaProperty.ofReadWrite(
        this, "dayCount", DepositConvention.class, DayCount.class);
    /**
     * The meta-property for the {@code businessDayConvention} property.
     */
    private final MetaProperty<BusinessDayConvention> _businessDayConvention = DirectMetaProperty.ofReadWrite(
        this, "businessDayConvention", DepositConvention.class, BusinessDayConvention.class);
    /**
     * The meta-property for the {@code daysToSettle} property.
     */
    private final MetaProperty<Integer> _daysToSettle = DirectMetaProperty.ofReadWrite(
        this, "daysToSettle", DepositConvention.class, Integer.TYPE);
    /**
     * The meta-property for the {@code isEOM} property.
     */
    private final MetaProperty<Boolean> _isEOM = DirectMetaProperty.ofReadWrite(
        this, "isEOM", DepositConvention.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", DepositConvention.class, Currency.class);
    /**
     * The meta-property for the {@code regionCalendar} property.
     */
    private final MetaProperty<ExternalId> _regionCalendar = DirectMetaProperty.ofReadWrite(
        this, "regionCalendar", DepositConvention.class, ExternalId.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "dayCount",
        "businessDayConvention",
        "daysToSettle",
        "isEOM",
        "currency",
        "regionCalendar");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1905311443:  // dayCount
          return _dayCount;
        case -1002835891:  // businessDayConvention
          return _businessDayConvention;
        case 379523357:  // daysToSettle
          return _daysToSettle;
        case 100464505:  // isEOM
          return _isEOM;
        case 575402001:  // currency
          return _currency;
        case 1932874322:  // regionCalendar
          return _regionCalendar;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DepositConvention> builder() {
      return new DirectBeanBuilder<DepositConvention>(new DepositConvention());
    }

    @Override
    public Class<? extends DepositConvention> beanType() {
      return DepositConvention.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code dayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> dayCount() {
      return _dayCount;
    }

    /**
     * The meta-property for the {@code businessDayConvention} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<BusinessDayConvention> businessDayConvention() {
      return _businessDayConvention;
    }

    /**
     * The meta-property for the {@code daysToSettle} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> daysToSettle() {
      return _daysToSettle;
    }

    /**
     * The meta-property for the {@code isEOM} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isEOM() {
      return _isEOM;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code regionCalendar} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalId> regionCalendar() {
      return _regionCalendar;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
