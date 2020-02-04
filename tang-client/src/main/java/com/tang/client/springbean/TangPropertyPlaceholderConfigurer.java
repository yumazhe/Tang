package com.tang.client.springbean;

import com.tang.core.config.TangConfig;
import com.tang.core.exceptions.TangException;
import com.tang.core.springbean.TangPlaceholderConfigurerSupport;
import com.tang.core.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Constants;
import org.springframework.core.SpringProperties;
import org.springframework.lang.Nullable;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by yuma on 2019/11/16.
 * copy from PropertyPlaceholderConfigurer
 * 加载resources路径下的文件
 * 使用方法：
 *      <bean id="propertyConfigurer" class="com.tang.client.springbean.TangPropertyPlaceholderConfigurer">
 *          <property name="root_path" value="/META-INF/tang/server-${CONFIG_ENV}.properties"/>
 *         <property name="locations">
 *             <list>
 *                 <value>classpath:dubbo.properties</value>
 *                 <value>classpath:env/random-${CONFIG_ENV}.properties</value>
 *             </list>
 *         </property>
 *     </bean>
 */
public class TangPropertyPlaceholderConfigurer extends TangPlaceholderConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(TangPropertyPlaceholderConfigurer.class);

    /**
     * 指定配置文件位置
     */
    private String root_path;

    public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;
    public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;
    public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;
    private static final Constants constants = new Constants(PropertyPlaceholderConfigurer.class);
    private int systemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;
    private boolean searchSystemEnvironment = !SpringProperties.getFlag("spring.getenv.ignore");

    public void setSystemPropertiesModeName(String constantName) throws IllegalArgumentException {
        this.systemPropertiesMode = constants.asNumber(constantName).intValue();
    }

    public void setSystemPropertiesMode(int systemPropertiesMode) {
        this.systemPropertiesMode = systemPropertiesMode;
    }

    public void setSearchSystemEnvironment(boolean searchSystemEnvironment) {
        this.searchSystemEnvironment = searchSystemEnvironment;
    }

    @Nullable
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        String propVal = null;
        if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
            propVal = this.resolveSystemProperty(placeholder);
        }

        if (propVal == null) {
            propVal = this.resolvePlaceholder(placeholder, props);
        }

        if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
            propVal = this.resolveSystemProperty(placeholder);
        }

        return propVal;
    }

    @Nullable
    protected String resolvePlaceholder(String placeholder, Properties props) {
        return props.getProperty(placeholder);
    }

    @Nullable
    protected String resolveSystemProperty(String key) {
        try {
            String value = System.getProperty(key);
            if (value == null && this.searchSystemEnvironment) {
                value = System.getenv(key);
            }

            return value;
        } catch (Throwable throwable) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not access system property '" + key + "': " + throwable);
            }

            return null;
        }
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        StringValueResolver valueResolver = new TangPropertyPlaceholderConfigurer.PlaceholderResolvingStringValueResolver(props);
        this.doProcessProperties(beanFactoryToProcess, valueResolver);

        // 打印数据
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            logger.debug(keyStr + " = " + value);
        }
    }

    private final class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {
        private final Properties props;

        private PropertyPlaceholderConfigurerResolver(Properties props) {
            this.props = props;
        }

        @Override
        @Nullable
        public String resolvePlaceholder(String placeholderName) {
            return TangPropertyPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, this.props, TangPropertyPlaceholderConfigurer.this.systemPropertiesMode);
        }
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {
        public final PropertyPlaceholderHelper helper;
        public final PropertyPlaceholderHelper.PlaceholderResolver resolver;

        public PlaceholderResolvingStringValueResolver(Properties props) {
            this.helper = new PropertyPlaceholderHelper(TangPropertyPlaceholderConfigurer.this.placeholderPrefix, TangPropertyPlaceholderConfigurer.this.placeholderSuffix, TangPropertyPlaceholderConfigurer.this.valueSeparator, TangPropertyPlaceholderConfigurer.this.ignoreUnresolvablePlaceholders);
            this.resolver = TangPropertyPlaceholderConfigurer.this.new PropertyPlaceholderConfigurerResolver(props);
        }

        @Override
        @Nullable
        public String resolveStringValue(String strVal) throws BeansException {
            String resolved = this.helper.replacePlaceholders(strVal, this.resolver);
            if (TangPropertyPlaceholderConfigurer.this.trimValues) {
                resolved = resolved.trim();
            }
            return resolved.equals(TangPropertyPlaceholderConfigurer.this.nullValue) ? null : resolved;
        }
    }

    /**
     * 设置配置中心设置的配置文件
     * @param root_path
     * @throws IOException
     */
    public void setRoot_path(String root_path) throws IOException {
        if(root_path == null || root_path.trim().length() == 0){
            throw new TangException("root_path必须配置");
        }

        logger.debug("the root path is [{}]", root_path);
        // 解析 classpath:env/user-${CONFIG_ENV}.properties 参数
        root_path = StringUtil.parseStringValue(root_path);

        // 加载输入流
        InputStream in = this.getClass().getResourceAsStream(root_path);
        if(in == null ){
            throw new TangException("the file ["+root_path+"] is not exist.");
        }

        Properties props = new Properties();
        InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF-8");
        props.load(inputStreamReader);

        // 初始化配置文件
        TangConfig.init(props);
    }


}

