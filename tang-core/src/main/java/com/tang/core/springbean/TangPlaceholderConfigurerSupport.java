package com.tang.core.springbean;

import com.tang.core.config.TangConstant;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.*;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * Created by yuma on 2019/12/7.
 *
 * copy from PlaceholderConfigurerSupport
 */
public abstract class TangPlaceholderConfigurerSupport extends TangPropertyResourceConfigurer implements BeanNameAware, BeanFactoryAware {


    protected String placeholderPrefix = TangConstant.DEFAULT_PLACEHOLDER_PREFIX;
    protected String placeholderSuffix = TangConstant.DEFAULT_PLACEHOLDER_SUFFIX;
    @Nullable
    protected String valueSeparator = TangConstant.DEFAULT_VALUE_SEPARATOR;
    protected boolean trimValues = false;
    @Nullable
    protected String nullValue;
    protected boolean ignoreUnresolvablePlaceholders = false;
    @Nullable
    private String beanName;
    @Nullable
    private BeanFactory beanFactory;

    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess, StringValueResolver valueResolver) {
        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        String[] temps = beanNames;
        int len = beanNames.length;

        for(int i = 0; i < len; ++i) {
            String curName = temps[i];
            if (!curName.equals(this.beanName) || !beanFactoryToProcess.equals(this.beanFactory)) {
                BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);

                try {
                    visitor.visitBeanDefinition(bd);
                } catch (Exception e) {
                    throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName, e.getMessage(), e);
                }
            }
        }

        beanFactoryToProcess.resolveAliases(valueResolver);
        beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
    }



    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    public void setValueSeparator(@Nullable String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    public void setTrimValues(boolean trimValues) {
        this.trimValues = trimValues;
    }

    public void setNullValue(String nullValue) {
        this.nullValue = nullValue;
    }

    public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
        this.ignoreUnresolvablePlaceholders = ignoreUnresolvablePlaceholders;
    }
    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}