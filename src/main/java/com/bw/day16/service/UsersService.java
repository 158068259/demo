package com.bw.day16.service;

import com.bw.day16.beans.Users;
import com.bw.day16.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;


     /**
      * @method:  listUsers
      * @description: 查询所有用户信息
      * @date: 2018/12/19
      * @author: pankun
      * @return
      */
    public List<Users> listUsers(){
        return usersMapper.selectAll();
    }
}
