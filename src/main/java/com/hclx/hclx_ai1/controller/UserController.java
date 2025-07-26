package com.hclx.hclx_ai1.controller;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hclx.hclx_ai1.entity.ChatMSG;
import com.hclx.hclx_ai1.entity.User;
import com.hclx.hclx_ai1.service.IUserService;
import com.hclx.hclx_ai1.utils.AESEncryption;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author springBoot-Learning
 * @since 2024-12-10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Resource
    RedisTemplate<String, List<ChatMSG>> redisTemplate;



    @PostMapping("/register")
    public JSONObject register(@RequestBody User user){
        JSONObject res=new JSONObject();

        String userId= UUID.randomUUID().toString();

        user.setId(userId);

         System.out.println(user);
         //查找重复nickname

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        long count = userService.count(queryWrapper);
        System.out.println(count);
        if (count!=0){
            res.put("code",500);
            res.put("msg","注册失败,用户名已存在");
        }else{
            try {
                user.setPassword(AESEncryption.encrypt(AESEncryption.get16bitUUID(userId),user.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            boolean b = userService.save(user);
            if (b){
                System.out.println("yizhice"+b);
                res.put("code",200);
                res.put("msg","注册成功");
                List<ChatMSG> list=new LinkedList<>();
                //向redis存储数据
                redisTemplate.opsForValue().set(userId,list);}
            else{
                res.put("code",500);
                res.put("msg","注册失败");}
            System.out.println(user);
        }

        //

        return  res;

    }
    @PostMapping("/login")
    public  JSONObject login(@RequestBody User user){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("username",user.getUsername());
        User queryUser=userService.getOne(queryWrapper);
        try {
            String password = AESEncryption.decrypt(AESEncryption.get16bitUUID(queryUser.getId()), queryUser.getPassword());
            System.out.println(user);
            System.out.println(password);
            JSONObject res=new JSONObject();
            if (password.equals(user.getPassword())){
                System.out.println("yes");
                res.put("code",200);
                res.put("msg","登录成功");
                res.put("data",queryUser);

            }
            else {
                res.put("code","500");
                res.put("message","登录失败");

            }

            return  res;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

@PostMapping("/deleteAccount")
    public JSONObject delete(@RequestBody User user){
    QueryWrapper queryWrapper=new QueryWrapper();
    queryWrapper.eq("id",user.getId());
    long count = userService.count(queryWrapper);
    JSONObject result=new JSONObject();
    System.out.println(count);
    System.out.println("2-00000000000000"+user.getId());
    if (count!=0){
        System.out.println("nihao");
        userService.remove(queryWrapper);
        result.put("code","200");

    }
    else {
    result.put("code","500");}

    return result;
}

@PostMapping("/changePwd")
    public JSONObject changePwd(@RequestBody JSONObject user) throws Exception {



        String oldpassword=user.getString("oldpassword");
        String password=user.getString("password");
        String id=user.getString("id");



        User saveuser=userService.getById(id);
        String oldpw = AESEncryption.decrypt(AESEncryption.get16bitUUID(id),saveuser.getPassword());
         JSONObject result=new JSONObject();
        if (oldpassword.equals(oldpw))
        {
            String newpasword = AESEncryption.encrypt(AESEncryption.get16bitUUID(id), password);
            saveuser.setPassword(newpasword);
            boolean update = userService.updateById(saveuser);

            if (update){
                result.put("code","200");
                result.put("msg","更改成功，即将回到登录页");
            }else {
                result.put("code","500");
                result.put("msg","更改失败");

            }


        }
        else {
            result.put("code","500");
            result.put("msg","原密码不一致");
        }
    return result ;
}
@PostMapping("/userList")
public JSONArray userList() throws Exception {
        JSONArray result=new JSONArray();
        QueryWrapper queryWrapper=new QueryWrapper();
    List<User> userlist = userService.list();
    for (User user1:userlist){
        JSONObject user=new JSONObject();
        user.put("id",user1.getId());
        user.put("username",user1.getUsername());
        user.put("nickname",user1.getNickname());
        String password = AESEncryption.decrypt(AESEncryption.get16bitUUID(user1.getId()), user1.getPassword());
        user.put("password",password);
        result.add(user);
        System.out.println("ok");

    }

    return result;
}

}
