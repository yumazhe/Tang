package com.tang.core.springbean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

/**
 * copy from PropertiesLoaderSupport
 */
public abstract class TangPropertiesLoaderSupport {
    private static final Logger logger = LoggerFactory.getLogger(TangPropertiesLoaderSupport.class);
    @Nullable
    protected Properties[] localProperties;
    protected boolean localOverride = false;
    @Nullable
    private Resource[] locations;
    private boolean ignoreResourceNotFound = false;
    @Nullable
    private String fileEncoding;
    private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

    /**
     * 合并处理 properties文件中配置的属性
     *
     * @return
     * @throws IOException
     */
    protected Properties mergeProperties() throws IOException {
        Properties result = new Properties();
        if (this.localOverride) {
            this.loadProperties(result);
        }

        if (this.localProperties != null) {
            Properties[] properties = this.localProperties;
            int len = properties.length;

            for (int i = 0; i < len; ++i) {
                Properties localProp = properties[i];
                CollectionUtils.mergePropertiesIntoMap(localProp, result);
            }
        }

        if (!this.localOverride) {
            this.loadProperties(result);
        }

        return result;
    }

    /**
     * 加载配置文件
     *
     * @param props
     * @throws IOException
     */
    protected void loadProperties(Properties props) throws IOException {
        if (this.locations != null) {
            Resource[] resources = this.locations;
            int len = resources.length;

            for (int i = 0; i < len; ++i) {
                Resource location = resources[i];

                try {
                    TangPropertiesLoaderUtils.fillProperties(props, new EncodedResource(location, this.fileEncoding), this.propertiesPersister);
                } catch (UnknownHostException | FileNotFoundException e) {
                    if (!this.ignoreResourceNotFound) {
                        throw e;
                    }
                }


                File file = location.getFile();
                String filename = file.getName();

                // 打印数据
                print(filename, props);
            }
        }

    }

    private void print(String filename, Properties props) {
        logger.debug("加载文件名称为:{}", filename);
        Set<String> keys = props.stringPropertyNames();
        for (String key : keys) {
            logger.debug("  -- {}={}", key, props.getProperty(key));
        }
    }


    public void setProperties(Properties properties) {
        this.localProperties = new Properties[]{properties};
    }

    public void setPropertiesArray(Properties... propertiesArray) {
        this.localProperties = propertiesArray;
    }

    public void setLocation(Resource location) {
        this.locations = new Resource[]{location};
    }

    public void setLocations(Resource... locations) {
        this.locations = locations;
    }

    public void setLocalOverride(boolean localOverride) {
        this.localOverride = localOverride;
    }

    public void setIgnoreResourceNotFound(boolean ignoreResourceNotFound) {
        this.ignoreResourceNotFound = ignoreResourceNotFound;
    }

    public void setFileEncoding(String encoding) {
        this.fileEncoding = encoding;
    }

    public void setPropertiesPersister(@Nullable PropertiesPersister propertiesPersister) {
        this.propertiesPersister = (PropertiesPersister) (propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
    }


}
