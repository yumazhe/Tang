package com.tang.web.controller.backstage;

import com.tang.core.exceptions.TangException;
import com.tang.web.common.ResultBody;
import com.tang.web.entities.user.LoginVO;
import com.tang.web.entities.user.UserBO;
import com.tang.web.services.ITangUserService;
import com.tang.web.utils.CommonUtil;
import com.tang.web.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class TangUserController {

    private static final Logger logger = LoggerFactory.getLogger(TangUserController.class);

    @Value("${user_login_expired}")
    private int  expiredTime;

    @Autowired
    private ITangUserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResultBody login(UserBO bo) throws Exception {

        if(bo == null){
            throw new TangException();
        }

        String name = bo.getName();
        if(name == null) {
            throw new TangException(" name must not be null.");
        }

        String password = bo.getPassword();
        if(password == null) {
            throw new TangException(" password must not be null.");
        }

        String nowPwd = userService.getUserPasswordByName(name);
        ResultBody result = new ResultBody();

        if(password.equals(nowPwd)){
            result.setMsg("登录成功");
            LoginVO vo = new LoginVO();
            Date now = new Date();

            // 生成token
            String token = MD5.getMD5(name+now.getTime());
            vo.setName(name);
            vo.setToken(token);

            // 设置过期时间 user_login_expired

            vo.setTime(CommonUtil.transTime(now, expiredTime));
            result.setData(vo);
        }else{
            result.setCode(-1);
            result.setMsg("用户名或密码不正确");
        }
        return result;
    }




}