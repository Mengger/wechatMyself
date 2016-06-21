package com.message.until;

/**
 * Created with IntelliJ IDEA.
 * User: zht
 * Date: 15-4-16
 * Time: 下午8:07
 * HTTP操作返回的结果
 */
public class HttpResponse {
    protected String responseText;  //返回的文本
    protected String session;       //返回的SESSION文本信息

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
