package com.tang.core.springbean;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

import com.tang.core.config.TangConfig;
import com.tang.core.config.TangConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.ResourceUtils;

/**
 * copy from PropertiesLoaderUtils
 */
public abstract class TangPropertiesLoaderUtils {

    private static final Logger logger = LoggerFactory.getLogger(TangPropertiesLoaderUtils.class);

    public static Properties loadProperties(EncodedResource resource) throws IOException {
        Properties props = new Properties();
        fillProperties(props, resource);
        return props;
    }

    public static void fillProperties(Properties props, EncodedResource resource) throws IOException {
        fillProperties(props, resource, new DefaultPropertiesPersister());
    }

    /**
     * 填充属性
     * @param props
     * @param resource
     * @param persister
     * @throws IOException
     */
    static void fillProperties(Properties props, EncodedResource resource, PropertiesPersister persister) throws IOException {
        InputStream stream = null;
        Reader reader = null;

        try {
            String filename = resource.getResource().getFilename();

            // xml格式文件
            if (filename != null && filename.endsWith(TangConstant.XML_FILE_EXTENSION)) {
                stream = resource.getInputStream();
                persister.loadFromXml(props, stream);
            } else if (resource.requiresReader()) {
                reader = resource.getReader();
                persister.load(props, reader);
            } else {
                // 判断文件是从本地获取还是从远程获取
                if(TangConfig.ignore(filename)){
                    // 需要从本地获取数据
                    logger.warn("the file[{}] need read from local.", filename);
                    stream = resource.getInputStream();
                    persister.load(props, stream);

                }else{
                    // 需要从zookeeper获取数据
                    logger.info("the file[{}] need read from remote.", filename);
                    stream = TangConfig.readFromRemote(filename);
                    if(stream == null){
                        throw new RuntimeException("can't get ["+filename+"]'s inputstream from remote.");
                    }
                    // 加载属性
                    persister.load(props, stream);
                }
            }
        } finally {
            if (stream != null) {
                stream.close();
            }

            if (reader != null) {
                reader.close();
            }

        }

    }

    public static Properties loadProperties(Resource resource) throws IOException {
        Properties props = new Properties();
        fillProperties(props, resource);
        return props;
    }

    public static void fillProperties(Properties props, Resource resource) throws IOException {
        InputStream is = resource.getInputStream();

        try {
            String filename = resource.getFilename();
            if (filename != null && filename.endsWith(TangConstant.XML_FILE_EXTENSION)) {
                props.loadFromXML(is);
            } else {
                props.load(is);
            }
        } finally {
            is.close();
        }

    }

    public static Properties loadAllProperties(String resourceName) throws IOException {
        return loadAllProperties(resourceName, (ClassLoader)null);
    }

    public static Properties loadAllProperties(String resourceName, @Nullable ClassLoader classLoader) throws IOException {
        Assert.notNull(resourceName, "Resource name must not be null");
        ClassLoader classLoaderToUse = classLoader;
        if (classLoader == null) {
            classLoaderToUse = ClassUtils.getDefaultClassLoader();
        }

        Enumeration<URL> urls = classLoaderToUse != null ? classLoaderToUse.getResources(resourceName) : ClassLoader.getSystemResources(resourceName);
        Properties props = new Properties();

        while(urls.hasMoreElements()) {
            URL url = (URL)urls.nextElement();
            URLConnection con = url.openConnection();
            ResourceUtils.useCachesIfNecessary(con);
            InputStream is = con.getInputStream();

            try {
                if (resourceName.endsWith(TangConstant.XML_FILE_EXTENSION)) {
                    props.loadFromXML(is);
                } else {
                    props.load(is);
                }
            } finally {
                is.close();
            }
        }

        return props;
    }
}
