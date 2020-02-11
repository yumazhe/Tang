package com.tang.core.callback;

import com.tang.core.springbean.TangPropertyResourceConfigurer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.PropertiesPersister;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by yuma on 2020/2/7.
 * 加载properties文件回调逻辑实现类
 */
public class LoadPropertyCallBackImpl implements CallBack {
    private TangPropertyResourceConfigurer configurer;
    private Properties props;
    private ConfigurableListableBeanFactory beanFactory;

    public LoadPropertyCallBackImpl(TangPropertyResourceConfigurer configurer, Properties props, ConfigurableListableBeanFactory beanFactory) {
        this.configurer = configurer;
        this.props = props;
        this.beanFactory = beanFactory;
    }

    @Override
    public void doHandler(byte[] data) throws IOException {
        // 加载配置文件，TODO 注：已初始化的数据尚不能热加载， 需要变更sping的注解加载机制
        props.load(new ByteArrayInputStream(data));
        configurer.convertProperties(props);
        configurer.processProperties(beanFactory, props);
    }
}
