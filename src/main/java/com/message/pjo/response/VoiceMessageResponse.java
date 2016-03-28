package com.message.pjo.response;

import com.message.pjo.request.BaseMessage;
import com.message.pjo.response.unit.Voice;

public class VoiceMessageResponse extends BaseMessage {
	//Voice语音消息
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
}
