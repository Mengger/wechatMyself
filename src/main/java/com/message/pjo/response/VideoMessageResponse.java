package com.message.pjo.response;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.response.unit.Video;
public class VideoMessageResponse  extends BaseMessage {
	//video消息
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
	
}
