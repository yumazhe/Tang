package com.tang.web.controller.backstage;

import com.tang.core.exceptions.TangException;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.version.VersionBO;
import com.tang.web.entities.version.VersionPO;
import com.tang.web.entities.version.VersionVO;
import com.tang.web.services.ITangVersionService;
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
@RequestMapping("/version")
public class TangVersionController {

    private static final Logger logger = LoggerFactory.getLogger(TangVersionController.class);

    @Autowired
    private ITangVersionService versionService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody save(VersionBO bo) throws Exception {

        if(bo == null){
            throw new TangException();
        }

        String name = bo.getName();
        if(name == null) {
            throw new TangException(" name must not be null.");
        }

        String description = bo.getDescription();
        if(description == null) {
            throw new TangException(" description must not be null.");
        }

        String appid = bo.getAppid();
        if(appid == null){
            throw new TangException("appid must not be null.");
        }

        int versionId = versionService.save(bo);

        ResultBody result = new ResultBody();
        result.setData(versionId);


        return result;
    }


    /**
     * 获取应用下的版本列表
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody list(@PathVariable Integer id) throws Exception {

        if(id == null) {
            throw new TangException(" id must not be null.");
        }

        List<VersionPO> pos = versionService.list(id);
        ResultBody result = new ResultBody();

        if(pos != null && pos.size() > 0){
            List<VersionVO> vos = new ArrayList<>();
            for(VersionPO po : pos){
                VersionVO vo = po.toVO();
                vos.add(vo);
            }
            result.setData(vos);
        }else {

        }

        return result;
    }




}