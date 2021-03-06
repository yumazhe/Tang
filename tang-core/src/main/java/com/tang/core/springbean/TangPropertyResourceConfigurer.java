package com.tang.core.springbean;

import com.tang.core.callback.CallBack;
import com.tang.core.callback.LoadPropertyCallBackImpl;
import com.tang.core.zookeeper.ZookeeperUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by yuma on 2019/12/7.
 * copy from PropertyResourceConfigurer
 */
public abstract class TangPropertyResourceConfigurer extends TangPropertiesLoaderSupport implements BeanFactoryPostProcessor, PriorityOrdered {
    private int order = 2147483647;

    public TangPropertyResourceConfigurer() {
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {

            // 合并处理配置属性
            Properties mergedProps = this.mergeProperties();

            // TODO 注册监听事件, 无需热加载
//            CallBack callBack = new LoadPropertyCallBackImpl(this, mergedProps, beanFactory);
//            ZookeeperUtil.INSTANCE.register(callBack);

            this.convertProperties(mergedProps);
            this.processProperties(beanFactory, mergedProps);
        } catch (IOException e) {
            throw new BeanInitializationException("Could not load properties", e);
        }
    }


    public void convertProperties(Properties props) {
        Enumeration propertyNames = props.propertyNames();

        while(propertyNames.hasMoreElements()) {
            String propertyName = (String)propertyNames.nextElement();
            String propertyValue = props.getProperty(propertyName);

            String convertedValue = this.convertProperty(propertyName, propertyValue);
            if (!ObjectUtils.nullSafeEquals(propertyValue, convertedValue)) {
                props.setProperty(propertyName, convertedValue);
            }
        }

    }

    protected String convertProperty(String propertyName, String propertyValue) {
        return this.convertPropertyValue(propertyValue);
    }

    protected String convertPropertyValue(String originalValue) {
        return originalValue;
    }

    public abstract void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException;

}
