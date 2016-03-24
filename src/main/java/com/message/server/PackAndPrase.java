package com.message.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.pjo.request.BaseMessage;
import com.message.until.MessageUtil;
import com.message.until.PropertyUntil;
import com.message.until.XStream;
import com.wechat.mp.aes.WXBizMsgCrypt;
import com.wechat.mp.aes.XMLParse;

public class PackAndPrase {
	
	private final static Log log = LogFactory.getLog(PackAndPrase.class);
	
	private HttpServletRequest request;
	private HttpServletResponse reponse;
	private WXBizMsgCrypt wXBizMsgCrypt;
	private BaseMessage baseMessage;
	
	public PackAndPrase(HttpServletRequest request, HttpServletResponse reponse) {
		super();
		this.request = request;
		this.reponse = reponse;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getReponse() {
		return reponse;
	}

	public void setReponse(HttpServletResponse reponse) {
		this.reponse = reponse;
	}

	public BaseMessage parse(){
		try {
			wXBizMsgCrypt=new WXBizMsgCrypt(PropertyUntil.get("Token"), PropertyUntil.get("encodingAesKey"), PropertyUntil.get("AppID"));
			String decryMsgInfo=wXBizMsgCrypt.decryptMsg(request.getParameter("msg_signature"), request.getParameter("timeStamp"), request.getParameter("nonce"), convertStreamToString(request.getInputStream()));
			InputStream getDecryInput=IOUtils.toInputStream(decryMsgInfo);
			Map<String, String> getMapInfo=XMLParse.parseXml(getDecryInput);
			baseMessage=MessageUtil.messageFactory(getMapInfo);
			return baseMessage;
			
		} catch (Exception e) {
			log.error("获取信息失败",e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void sendInfo(){
		PrintWriter out=null;
		try {
			String fromName=baseMessage.getFromUserName();
			baseMessage.setFromUserName(baseMessage.getToUserName());
			baseMessage.setToUserName(fromName);
			out = reponse.getWriter();
			String pln=wXBizMsgCrypt.encryptMsg(XStream.toXML(baseMessage),request.getParameter("timestamp"),request.getParameter("nonce"));
			out.println(pln);
		} catch (Exception e) {
			log.error("加密发送信息失败",e);
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}
		
	}

	public static String convertStreamToString(InputStream is) {    
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
        StringBuilder sb = new StringBuilder();
        String line = null;      
       try {      
           while ((line = reader.readLine()) != null) {      
                sb.append(line + "\n");      
            }      
        } catch (IOException e) {      
            e.printStackTrace();      
        } finally {      
           try {      
                is.close();      
            } catch (IOException e) {      
                e.printStackTrace();      
            }      
        } 
       return sb.toString();      
    }

}
