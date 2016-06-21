package wechatMyself;

import com.message.until.HttpResponse;
import com.message.until.HttpUtils;

public class TestMain {

	public void main() {
		HttpUtils rr=new HttpUtils();
		String strURL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=TFOu0AOd-RkEg0Crvgyk_uvPYu8EnNr7sNdkzPsswYKv3rCsIY00DX5f7AlD9aMxWe-ZzfHwpRt5rfqB27kUzZDj23gIXrIwheBBRi8HJkdrYL8fFqux70R6dHJVKBlmWIMbAIAFRV";
		String json="{\"button\":[{	\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{	\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
		
		HttpResponse rq=rr.doHttpPostJson(strURL, json, null);
		System.out.println(rq.getResponseText());
	}
}
