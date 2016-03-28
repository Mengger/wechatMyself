package com.message.pjo.response.unit;

public class Video {
	
	 // 图文消息名称  
    private String Title;  
    // 图文消息描述  
    private String Description;  
 // 媒体ID  
    private String MediaId;
    
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}  
    
}
