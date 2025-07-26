package com.hclx.hclx_ai1.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hclx.hclx_ai1.entity.Role;
import com.hclx.hclx_ai1.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author springBoot-Learning
 * @since 2024-12-14
 */

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
   private IRoleService iRoleService;
@GetMapping("/getAllRole")
    public JSONObject queryRole(){
List<Role> list=iRoleService.list();
JSONObject result=new JSONObject();
result.put("code","200");
result.put("data",list);



        return result;
    }
}
