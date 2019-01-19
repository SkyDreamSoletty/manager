package com.manager.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session){
            return "index";
    }

    public void menu(HttpSession session, HttpServletResponse response, HttpServletRequest request) {

    }

    public void IndexInfo(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("name","test");
        System.out.println("test");
    }
}
