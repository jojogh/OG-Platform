/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.component.factory.master;

import java.util.LinkedHashMap;
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

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.component.factory.ComponentInfoAttributes;
import com.opengamma.master.security.SecurityDocument;
import com.opengamma.master.security.SecurityMaster;
import com.opengamma.master.security.impl.DataSecurityMasterResource;
import com.opengamma.master.security.impl.DynamicDelegatingSecurityMaster;
import com.opengamma.master.security.impl.RemoteSecurityMaster;
import org.joda.beans.Bean;

/**
 * Component factory for delegating master that maintains a map of delegate masters
 * <p/>
 * Register as both a generic SecurityMaster as well as the concrete type
 * DynamicDelegatingSecurityMasterComponentFactory, to allow command processor
 * to access out of band methods like:
 * {@link DynamicDelegatingSecurityMaster#register(String, SecurityMaster)},
 * {@link DynamicDelegatingSecurityMaster#deregister(String)} and
 * {@link DynamicDelegatingSecurityMaster#add(String, SecurityDocument)}
 */
@BeanDefinition
public class DynamicDelegatingSecurityMasterComponentFactory extends AbstractComponentFactory {

  /** The classifier that the factory should publish under. */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  /** The flag determining whether the component should be published by REST (default true). */
  @PropertyDefinition
  private boolean _publishRest = true;

  @Override
  public void init(final ComponentRepository repo, final LinkedHashMap<String, String> configuration) {

    final DynamicDelegatingSecurityMaster master = new DynamicDelegatingSecurityMaster();

    final ComponentInfo info = new ComponentInfo(SecurityMaster.class, getClassifier());
    info.addAttribute(ComponentInfoAttributes.LEVEL, 1);
    info.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteSecurityMaster.class);
    repo.registerComponent(info, master);

    if (isPublishRest()) {
      repo.getRestComponents().publish(info, new DataSecurityMasterResource(master));
    }

    final ComponentInfo concreteInfo = new ComponentInfo(DynamicDelegatingSecurityMaster.class, getClassifier());
    concreteInfo.addAttribute(ComponentInfoAttributes.LEVEL, 1);

    repo.registerComponent(info, concreteInfo);

  }


  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DynamicDelegatingSecurityMasterComponentFactory}.
   * @return the meta-bean, not null
   */
  public static DynamicDelegatingSecurityMasterComponentFactory.Meta meta() {
    return DynamicDelegatingSecurityMasterComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DynamicDelegatingSecurityMasterComponentFactory.Meta.INSTANCE);
  }

  @Override
  public DynamicDelegatingSecurityMasterComponentFactory.Meta metaBean() {
    return DynamicDelegatingSecurityMasterComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the flag determining whether the component should be published by REST (default true).
   * @return the value of the property
   */
  public boolean isPublishRest() {
    return _publishRest;
  }

  /**
   * Sets the flag determining whether the component should be published by REST (default true).
   * @param publishRest  the new value of the property
   */
  public void setPublishRest(boolean publishRest) {
    this._publishRest = publishRest;
  }

  /**
   * Gets the the {@code publishRest} property.
   * @return the property, not null
   */
  public final Property<Boolean> publishRest() {
    return metaBean().publishRest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public DynamicDelegatingSecurityMasterComponentFactory clone() {
    return (DynamicDelegatingSecurityMasterComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DynamicDelegatingSecurityMasterComponentFactory other = (DynamicDelegatingSecurityMasterComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          (isPublishRest() == other.isPublishRest()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(isPublishRest());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("DynamicDelegatingSecurityMasterComponentFactory{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("classifier").append('=').append(JodaBeanUtils.toString(getClassifier())).append(',').append(' ');
    buf.append("publishRest").append('=').append(JodaBeanUtils.toString(isPublishRest())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DynamicDelegatingSecurityMasterComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", DynamicDelegatingSecurityMasterComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code publishRest} property.
     */
    private final MetaProperty<Boolean> _publishRest = DirectMetaProperty.ofReadWrite(
        this, "publishRest", DynamicDelegatingSecurityMasterComponentFactory.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "publishRest");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case -614707837:  // publishRest
          return _publishRest;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends DynamicDelegatingSecurityMasterComponentFactory> builder() {
      return new DirectBeanBuilder<DynamicDelegatingSecurityMasterComponentFactory>(new DynamicDelegatingSecurityMasterComponentFactory());
    }

    @Override
    public Class<? extends DynamicDelegatingSecurityMasterComponentFactory> beanType() {
      return DynamicDelegatingSecurityMasterComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code publishRest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> publishRest() {
      return _publishRest;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((DynamicDelegatingSecurityMasterComponentFactory) bean).getClassifier();
        case -614707837:  // publishRest
          return ((DynamicDelegatingSecurityMasterComponentFactory) bean).isPublishRest();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((DynamicDelegatingSecurityMasterComponentFactory) bean).setClassifier((String) newValue);
          return;
        case -614707837:  // publishRest
          ((DynamicDelegatingSecurityMasterComponentFactory) bean).setPublishRest((Boolean) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((DynamicDelegatingSecurityMasterComponentFactory) bean)._classifier, "classifier");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
