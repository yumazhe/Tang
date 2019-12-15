package com.tang.web.controller.backstage;

import com.tang.core.exceptions.TangException;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.config.ConfigBO;
import com.tang.web.entities.config.ConfigPO;
import com.tang.web.entities.config.ConfigVO;
import com.tang.web.services.ITangConfigService;
import com.tang.web.utils.FileUtil;
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

    @Autowired
    private ITangConfigService configService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody save(ConfigBO bo, @RequestParam(value="file", required = false) MultipartFile file) throws Exception {

        if(bo == null){
            throw new TangException();
        }

        String name = bo.getName();
        if(name == null) {
            throw new TangException(" name must not be null.");
        }

        String envid = bo.getEnvid();
        if(envid == null){
            throw new TangException("envid must not be null.");
        }

        String version = bo.getVersionid();
        if(version == null){
            throw new TangException("versionid must not be null.");
        }

        String appid = bo.getAppid();
        if(appid == null){
            throw new TangException("appid must not be null.");
        }

        String type = bo.getType();
        if(type.equals("1")){
            String content = bo.getContent();
            if(content == null) {
                throw new TangException(" content must not be null.");
            }

        }else{

            String content = FileUtil.getContent(file);
            bo.setContent(content);

        }



        int configId = configService.save(bo);

        //TODO 创建 配置节点

        ResultBody result = new ResultBody();
        result.setData(configId);


        return result;
    }


    /**
     * 获取配置列表
     * @param appid 应用id
     * @param versionid 版本id
     * @param envid 环境id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list/{appid}/{versionid}/{envid}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody list(@PathVariable Integer appid, @PathVariable Integer versionid, @PathVariable Integer envid) throws Exception {

        List<ConfigPO> pos = configService.list(appid, versionid, envid);
        ResultBody result = new ResultBody();

        if(pos != null && pos.size() > 0){
            List<ConfigVO> vos = new ArrayList<>();
            for(ConfigPO po : pos){
                ConfigVO vo = po.toVO();
                vos.add(vo);
            }
            result.setData(vos);
        }else {

        }

        return result;
    }

    /**
     * 获取配置详情
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody detail(@PathVariable Integer id) throws Exception {

        if(id == null){
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
     * @param id
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody update(@PathVariable Integer id, String content, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        if(id == null){
            throw new TangException("id must not be null.");
        }
        if(content == null){
            throw new TangException("content must not be null.");
        }

        configService.update(id, content);

        // TODO 更新配置节点


        ResultBody result = new ResultBody();

        return result;
    }

    /**
     * 上传文件
     * @param appid 应用
     * @param versionid 版本
     * @param envid 环境
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "upload/{appid}/{versionid}/{envid}", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody uploadFile(@PathVariable Integer appid, @PathVariable Integer versionid, @PathVariable Integer envid) throws Exception {


        // TODO 更新配置节点


        ResultBody result = new ResultBody();

        return result;
    }
    /**
     * 上传文件
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
            for(Map.Entry entry : set){
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