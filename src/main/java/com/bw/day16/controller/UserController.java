package com.bw.day16.controller;

import com.bw.day16.beans.Users;
import com.bw.day16.service.UsersService;
import com.bw.day16.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @Title: UserController
* @Description: 用户控制层
* @Company: myth
* @author: pankun
* @date: 2018/12/19
* @return:
*/
@Controller
public class UserController {

    @Autowired
    private UsersService usersService;

    @RequestMapping("listUser")
    public String listUser(Model model,@RequestParam(defaultValue = "0") Integer page){
        Integer rows=3;
        PageHelper.startPage(page,rows);
        List<Users> list=usersService.listUsers();
        PageInfo<Users> info=new PageInfo<Users>(list,"listUser");
        model.addAttribute("info",info);
        return "listuser";
    }
}
