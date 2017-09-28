package com.naver.intern.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping
    public ModelAndView test(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");
        Map<String, String> map = new HashMap<>();
        map.put("test", "test");
        modelAndView.addObject("test", map);

        return modelAndView;
    }
}
