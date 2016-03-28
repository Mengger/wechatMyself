package com.message.pjo.response;

import com.message.pjo.request.BaseMessage;

public class TextMessageResponse extends BaseMessage {
	// 回复的消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}
