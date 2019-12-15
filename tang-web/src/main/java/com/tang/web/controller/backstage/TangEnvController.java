package com.tang.web.controller.backstage;

import com.tang.core.exceptions.TangException;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.env.EnvBO;
import com.tang.web.entities.env.EnvPO;
import com.tang.web.entities.env.EnvVO;
import com.tang.web.services.ITangEnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 版本管理
 */
@RestController
@RequestMapping("/env")
public class TangEnvController {

    private static final Logger logger = LoggerFactory.getLogger(TangEnvController.class);

    @Autowired
    private ITangEnvService envService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody save(EnvBO bo) throws Exception {

        if(bo == null){
            throw new TangException();
        }

        String code = bo.getCode();
        if(code == null) {
            throw new TangException(" code must not be null.");
        }

        String description = bo.getDescription();
        if(description == null) {
            throw new TangException(" description must not be null.");
        }

        String app = bo.getAppid();
        if(app == null){
            throw new TangException("appid must not be null.");
        }

        String version = bo.getVersionid();
        if(version == null){
            throw new TangException("versionid must not be null.");
        }

        int envId = envService.save(bo);

        ResultBody result = new ResultBody();

        result.setData(envId);

        return result;
    }


    @RequestMapping(value = "list/{appid}/{versionid}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody list(@PathVariable Integer appid, @PathVariable Integer versionid) throws Exception {

        List<EnvPO> pos = envService.list(appid, versionid);
        ResultBody result = new ResultBody();

        if(pos != null && pos.size() > 0){
            List<EnvVO> vos = new ArrayList<>();
            for(EnvPO po : pos){
                EnvVO vo = po.toVO();
                vos.add(vo);
            }
            result.setData(vos);
        }else {

        }

        return result;
    }




}