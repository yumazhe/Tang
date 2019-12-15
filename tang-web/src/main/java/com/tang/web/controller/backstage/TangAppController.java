package com.tang.web.controller.backstage;

import com.tang.core.exceptions.TangException;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.app.AppBO;
import com.tang.web.entities.app.AppPO;
import com.tang.web.entities.app.AppVO;
import com.tang.web.services.ITangAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用管理
 */
@RestController
@RequestMapping("/app")
public class TangAppController {

    private static final Logger logger = LoggerFactory.getLogger(TangAppController.class);

    @Autowired
    private ITangAppService appService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody save(AppBO app) throws Exception {

        if(app == null){
            throw new TangException();
        }

        String name = app.getName();
        if(name == null) {
            throw new TangException(" name must not be null.");
        }

        String description = app.getDescription();
        if(description == null) {
            throw new TangException(" description must not be null.");
        }

        int id = appService.save(app);

        ResultBody result = new ResultBody();
        result.setData(id);

        return result;
    }


    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public ResultBody list() throws Exception {

        List<AppPO> pos = appService.list();
        ResultBody result = new ResultBody();

        if(pos != null && pos.size() > 0){
            List<AppVO> vos = new ArrayList<>();
            for(AppPO po : pos){
                AppVO vo = po.toVO();
                vos.add(vo);
            }
            result.setData(vos);
        }else {

        }

        return result;
    }




}