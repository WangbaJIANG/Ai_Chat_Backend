package com.hclx.hclx_ai1.entity;

import lombok.Data;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;

@Data
public class ChatMSG {
    //消息类型：用户消息，ai助手消息

    private  String type;
    //消息内容
    private  String msg;
    public static  final  String USER="user";
    public static  final  String AI="ai";

    public  static ChatMSG user(String msg){
        ChatMSG chatMSG=new ChatMSG();
        chatMSG.setMsg(msg);
        chatMSG.setType(USER);
        return chatMSG;
    }
    public  static ChatMSG ai(String msg){
        ChatMSG chatMSG=new ChatMSG();
        chatMSG.setMsg(msg);
        chatMSG.setType(AI);
        return chatMSG;
    }
    //将ChatMSG实体类对象转为对应的UserMSG或AssitantMSG对象
//redis无法存储Java 的object对象 需序列化与反序列化，而UserMSG和AssistantMessage不支持序列化
    public AbstractMessage transfer(){
        if (USER.equals(type)){
            return  new UserMessage(msg);
        }
        if (AI.equals(type)){
            return  new AssistantMessage(msg);

        }
        return null;
    }


}
