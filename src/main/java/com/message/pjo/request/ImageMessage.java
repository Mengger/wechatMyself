package com.message.pjo.request;

public class ImageMessage extends BaseMessage {
	// 图片链接  
    private String PicUrl;  
    // 媒体ID  
    private String MediaId;  
    
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }
}
