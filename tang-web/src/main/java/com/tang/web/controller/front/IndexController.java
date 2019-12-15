package com.tang.web.controller.front;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {


    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}