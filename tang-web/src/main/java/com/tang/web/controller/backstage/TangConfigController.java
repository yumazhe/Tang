package com.tang.web.controller.backstage;

import com.tang.core.config.TangConstant;
import com.tang.core.exceptions.TangException;
import com.tang.core.utils.StringUtil;
import com.tang.core.zookeeper.ZookeeperUtil;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.config.ConfigBO;
import com.tang.web.entities.config.ConfigPO;
import com.tang.web.entities.config.ConfigVO;
import com.tang.web.services.ITangAppService;
import com.tang.web.services.ITangConfigService;
import com.tang.web.services.ITangEnvService;
import com.tang.web.services.ITangVersionService;
import com.tang.web.utils.FileUtil;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * 配置管理
 */
@RestController
@RequestMapping("/config")
public class TangConfigController {

    private static final Logger logger = LoggerFactory.getLogger(TangConfigController.class);

    //字符串数值的个数，不够左端补零，6表示6位数
    private static final int count = 6;

    @Autowired
    private ITangConfigService configService;

    @Autowired
    private ITangAppService appService;

    @Autowired
    private ITangVersionService versionService;

    @Autowired
    private ITangEnvService envService;

    /**
     * 保存配置信息
     *
     * @param bo
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody save(ConfigBO bo, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        if (bo == null) {
            throw new TangException();
        }

        // 属性名称
        String name = bo.getName();
        if (name == null) {
            throw new TangException(" name must not be null.");
        }

        // 环境配置id
        String envid = bo.getEnvid();
        if (envid == null) {
            throw new TangException("envid must not be null.");
        }

        // 版本id
        String version = bo.getVersionid();
        if (version == null) {
            throw new TangException("versionid must not be null.");
        }

        // 应用id
        String appid = bo.getAppid();
        if (appid == null) {
            throw new TangException("appid must not be null.");
        }

        // 配置类型
        String type = bo.getType();
        if (type.equals("1")) {
            String content = bo.getContent();
            if (content == null) {
                throw new TangException(" content must not be null.");
            }

        } else {
            String content = FileUtil.getContent(file);
            bo.setContent(content);

        }

        int configId = configService.save(bo);

        // 应用名称
        String appName = appService.findAppNameById(Integer.valueOf(appid));

        // 版本名称
        String versionName = versionService.findVersionNameById(Integer.valueOf(version));

        // 环境名称
        String envName = envService.findEnvNameById(Integer.valueOf(envid));

        // 属性名称
        String fileName = name;

        // 生产节点路径:/tang/app_name/version/env/file
        String path = StringUtil.generateNode(fileName, envName, versionName, appName);

        // 数据内容
        final String content = bo.getContent();

        saveZKNode(path, content);

        ResultBody result = new ResultBody();
        result.setData(configId);


        return result;
    }


    /**
     * 获取配置列表
     *
     * @param appid     应用id
     * @param versionid 版本id
     * @param envid     环境id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list/{appid}/{versionid}/{envid}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody list(@PathVariable Integer appid, @PathVariable Integer versionid, @PathVariable Integer envid) throws Exception {

        List<ConfigPO> pos = configService.list(appid, versionid, envid);
        ResultBody result = new ResultBody();

        if (pos != null && pos.size() > 0) {
            List<ConfigVO> vos = new ArrayList<>();
            for (ConfigPO po : pos) {
                ConfigVO vo = po.toVO();
                vos.add(vo);
            }
            result.setData(vos);
        } else {

        }

        return result;
    }

    /**
     * 获取配置详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody detail(@PathVariable Integer id) throws Exception {

        if (id == null) {
            throw new TangException("id must not be null.");
        }
        ConfigPO po = configService.queryById(id);
        ResultBody result = new ResultBody();

        ConfigVO vo = po.toVO();
        result.setData(vo);

        return result;
    }

    /**
     * 更新配置信息
     *
     * @param id
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody update(@PathVariable Integer id, String content, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        if (id == null) {
            throw new TangException("id must not be null.");
        }
        if (content == null) {
            throw new TangException("content must not be null.");
        }

        configService.update(id, content);

        // 生产节点路径:/tang/app_name/version/env/file

        ConfigPO po = configService.queryById(id);
        // 应用名称
        String appName = appService.findAppNameById(po.getAppId());

        // 版本名称
        String versionName = versionService.findVersionNameById(po.getVersionId());

        // 环境名称
        String envName = envService.findEnvNameById(po.getEnvId());

        // 属性名称
        String fileName = configService.findConfigNameById(id);

        String path = StringUtil.generateNode(fileName, envName, versionName, appName);

        saveZKNode(path, content);

        ResultBody result = new ResultBody();

        return result;
    }

    /**
     * 更新zk节点数据
     *
     * @param path    节点
     * @param content 节点内容
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void saveZKNode(String path, String content) throws KeeperException, InterruptedException {
        // 判断 属性内容是否超出1M字节大小
        if (content.length() > TangConstant.zk_value_max_size) {
            // 超过1M 则需要分节点存储

            List<String> datas = StringUtil.split(content, TangConstant.zk_value_max_size);

            int node = 0;
            for (String data : datas) {

                // 配置子节点
                path = path + TangConstant.default_file_separator + StringUtil.formatByFillZero(node, 6);
                ZookeeperUtil.INSTANCE.write(path, data);
                node++;
            }


        } else {
            ZookeeperUtil.INSTANCE.write(path, content);
        }
    }


    /**
     * TODO 上传文件
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody upload(@RequestParam("file") MultipartFile file) throws IOException {
        ResultBody result = new ResultBody();

        if (file.isEmpty()) {
            result.setMsg("上传失败，请选择文件");
            return result;
        }


        // 获取文件
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        if (!type.equals(".properties")) {
            result.setMsg("必须为properties文件");
            return result;
        }

        InputStream in = null;
        try {
            // 解析properties文件
            in = file.getInputStream();

            Properties properties = new Properties();

            properties.load(in);

            StringBuffer sb = new StringBuffer();
            Set<Map.Entry<Object, Object>> set = properties.entrySet();
            for (Map.Entry entry : set) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }

            String content = sb.toString();
            System.out.println(content.length());
            System.out.println(content);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return result;

    }


}