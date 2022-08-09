package com.xiaoming.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 测试
 * @author 赵明城
 * @date 2022/8/7
 */
@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name", "北京大学");
        model.addAttribute("age",150);
        return "/demo/view";
    }
}
