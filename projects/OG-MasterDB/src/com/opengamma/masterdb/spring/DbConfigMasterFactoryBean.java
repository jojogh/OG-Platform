/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.spring;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.change.JmsChangeManager;
import com.opengamma.masterdb.config.DbConfigMaster;

/**
 * Spring factory bean to create the database config master.
 */
@BeanDefinition
public class DbConfigMasterFactoryBean extends AbstractDbMasterFactoryBean<DbConfigMaster> {

  /**
   * Creates an instance.
   */
  public DbConfigMasterFactoryBean() {
    super(DbConfigMaster.class);
  }

  //-------------------------------------------------------------------------
  @Override
  protected DbConfigMaster createObject() {
    DbConfigMaster master = new DbConfigMaster(getDbConnector());
    if (getUniqueIdScheme() != null) {
      master.setUniqueIdScheme(getUniqueIdScheme());
    }
    if (getMaxRetries() != null) {
      master.setMaxRetries(getMaxRetries());
    }
    if (getJmsConnector() != null) {
      JmsChangeManager cm = new JmsChangeManager(getJmsConnector().ensureTopicName(getJmsChangeManagerTopic()));
      master.setChangeManager(cm);
      cm.start();
    }
    return master;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DbConfigMasterFactoryBean}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static DbConfigMasterFactoryBean.Meta meta() {
    return DbConfigMasterFactoryBean.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(DbConfigMasterFactoryBean.Meta.INSTANCE);
  }

  @Override
  public DbConfigMasterFactoryBean.Meta metaBean() {
    return DbConfigMasterFactoryBean.Meta.INSTANCE;
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
   * The meta-bean for {@code DbConfigMasterFactoryBean}.
   */
  public static class Meta extends AbstractDbMasterFactoryBean.Meta<DbConfigMaster> {
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
    public BeanBuilder<? extends DbConfigMasterFactoryBean> builder() {
      return new DirectBeanBuilder<DbConfigMasterFactoryBean>(new DbConfigMasterFactoryBean());
    }

    @Override
    public Class<? extends DbConfigMasterFactoryBean> beanType() {
      return DbConfigMasterFactoryBean.class;
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
