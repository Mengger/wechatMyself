package com.message.until;

import java.util.Date;
import java.util.Map;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.request.ImageMessage;
import com.message.pjo.request.LinkMessage;
import com.message.pjo.request.LocationMessage;
import com.message.pjo.request.TextMessage;
import com.message.pjo.request.VoiceMessage;

public class MessageUtil {

    /** 
     * 返回消息类型：文本 
     */  
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 返回消息类型：音乐 
     */  
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";  
  
    /** 
     * 返回消息类型：图文 
     */  
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";  
  
    /** 
     * 请求消息类型：文本 
     */  
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";  
  
    /** 
     * 请求消息类型：图片 
     */  
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";  
  
    /** 
     * 请求消息类型：链接 
     */  
    public static final String REQ_MESSAGE_TYPE_LINK = "link";  
  
    /** 
     * 请求消息类型：地理位置 
     */  
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";  
  
    /** 
     * 请求消息类型：音频 
     */  
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";  
  
    /** 
     * 请求消息类型：推送 
     */  
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";  
  
    /** 
     * 事件类型：subscribe(订阅) 
     */  
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";  
  
    /** 
     * 事件类型：unsubscribe(取消订阅) 
     */  
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";  
  
    /** 
     * 事件类型：CLICK(自定义菜单点击事件) 
     */  
    public static final String EVENT_TYPE_CLICK = "CLICK"; 
    
    public static BaseMessage messageFactory(Map<String, String> reqMap){
    	BaseMessage rtn=new BaseMessage();
    	
    	if(reqMap.get("MsgType").equals(REQ_MESSAGE_TYPE_TEXT)){//文本消息类型
    		rtn=new TextMessage();
    		((TextMessage) rtn).setContent(reqMap.get("Content"));
    	}else if(reqMap.get("MsgType").equals(REQ_MESSAGE_TYPE_IMAGE)){//图片消息类型
    		rtn=new ImageMessage();
    		((ImageMessage) rtn).setPicUrl(reqMap.get("PicUrl"));
    		((ImageMessage) rtn).setMediaId(reqMap.get("MediaId"));
    	}else if(reqMap.get("MsgType").equals(REQ_MESSAGE_TYPE_LINK)){//连接消息
    		rtn=new LinkMessage();
    		((LinkMessage) rtn).setTitle(reqMap.get("Title"));
    		((LinkMessage) rtn).setDescription(reqMap.get("Description"));
    		((LinkMessage) rtn).setUrl(reqMap.get("Url"));
    	}else if(reqMap.get("MsgType").equals(REQ_MESSAGE_TYPE_LOCATION)){//地理位置
    		rtn=new LocationMessage();
    		((LocationMessage) rtn).setLocation_X(reqMap.get("Location_X"));
    		((LocationMessage) rtn).setLocation_Y(reqMap.get("Location_Y"));
    		((LocationMessage) rtn).setScale(reqMap.get("Scale"));
    		((LocationMessage) rtn).setLabel(reqMap.get("Label"));
    	}else if(reqMap.get("MsgType").equals(REQ_MESSAGE_TYPE_VOICE)){//音频消息
    		rtn=new VoiceMessage();
    		((VoiceMessage) rtn).setMediaId(reqMap.get("MediaId"));
    		((VoiceMessage) rtn).setRecognition(reqMap.get("Recognition"));
    		((VoiceMessage) rtn).setFormat(reqMap.get("Format"));
    	}else if(reqMap.get("MsgType").equals(EVENT_TYPE_SUBSCRIBE)){//订阅消息
    		rtn=new BaseMessage();
    	}
    	
    	rtn.setCreateTime(new Date().getTime());
    	rtn.setFromUserName(reqMap.get("FromUserName"));
    	rtn.setMsgId(Long.valueOf(reqMap.get("MsgId")));
    	rtn.setToUserName(reqMap.get("ToUserName"));
    	rtn.setMsgType(reqMap.get("MsgType"));
    	
    	return rtn;
    }
  
  
    /** 
     * 对象转换成xml 
     *  
     * @param textMessage 消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(Object textMessage) {  
        return xstream.toXML(textMessage);  
    }  
  
    private static XStream xstream = new XStream();  
}
