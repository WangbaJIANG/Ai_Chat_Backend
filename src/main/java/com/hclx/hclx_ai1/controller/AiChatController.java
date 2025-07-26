package com.hclx.hclx_ai1.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import com.hclx.hclx_ai1.entity.ChatMSG;
import com.hclx.hclx_ai1.entity.User;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.json.Cookie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/ai_chat")
public class AiChatController {

    @Autowired
    ChatClient chatClient;

    @Resource
    RedisTemplate<String,List<ChatMSG>> redisTemplate;


    HashMap<String, List<Message>> map=new HashMap<>();

    @GetMapping("/say")
    public String say(@NotBlank(message = "查询消息不可为空") String msg,
                      @NotBlank(message = "userid不可为空")String userId){

        //根据userId加载历史记录
        List<ChatMSG> msgList = redisTemplate.opsForValue().get(userId);


        if (msgList==null){
            msgList=new LinkedList<>();

        } else if (msgList.size()>10) {
            msgList=msgList.subList(msgList.size()-10, msgList.size());

        }

        //3.将用户消息添加至历史记录
        msgList.add(ChatMSG.user(msg));
        //4.发送请求 调用ai
        String content = chatClient.prompt().messages(msgList.stream().map(ChatMSG::transfer).collect(Collectors.toList())).call().content();
        //将gpt返回的消息追加到历史记录中
        msgList.add(ChatMSG.ai(content));
        //6.存入redis
        redisTemplate.opsForValue().set(userId,msgList);


        return content ;
    }
    @GetMapping("/getId")
    public String getId(){
        String userId= UUID.randomUUID().toString();
        //key:userId
        //value:list
        //
//        List<Message> list=new ArrayList<>();
//        map.put(userId,list);

        List<ChatMSG> list=new LinkedList<>();
        //向redis存储数据
        redisTemplate.opsForValue().set(userId,list);

        return userId;
    }

    @PostMapping("/getHistory")
    public JSONObject getHistory(@RequestBody User user){
        String userId = user.getId();
        System.out.println(userId+"shiu");
        JSONObject result=new JSONObject();
        List<ChatMSG> msgList = redisTemplate.opsForValue().get(userId);
        if (!msgList.isEmpty()){
            JSONArray jsonArray=new JSONArray();
            for(ChatMSG chatMSG :msgList)
            {     JSONObject res=new JSONObject();
                System.out.println(chatMSG.getMsg());
                res.put("message",chatMSG.getMsg());
                res.put("type",chatMSG.getType());
                jsonArray.add(res);
                System.out.println("P");
            }

            result.put("history",jsonArray);
        }
        else {

            result.put("code","500");
            result.put("msg","历史记录为空哦");

        }
        System.out.println("这是用户id"+user.getId());


        return result;
    }
}
