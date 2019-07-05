package cn.leadeon.sprjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: he.l
 * @create: 2019-07-04 17:21
 **/
@Controller
public class SystemController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
