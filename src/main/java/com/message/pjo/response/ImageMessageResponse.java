package com.message.pjo.response;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.response.unit.Image;

public class ImageMessageResponse extends BaseMessage {
	//回复图片消息
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
	
}
