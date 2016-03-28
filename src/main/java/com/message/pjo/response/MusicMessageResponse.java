package com.message.pjo.response;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.response.unit.Music;

public class MusicMessageResponse extends BaseMessage {
	// 音乐  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    } 
}
