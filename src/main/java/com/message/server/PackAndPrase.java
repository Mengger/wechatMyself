package com.message.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.message.pjo.request.BaseMessage;
import com.message.pjo.request.ImageMessage;
import com.message.pjo.request.TextMessage;
import com.message.pjo.response.ArticlesMessageResponse;
import com.message.pjo.response.ImageMessageResponse;
import com.message.pjo.response.unit.Image;
import com.message.pjo.response.unit.item;
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
			String decryMsgInfo=wXBizMsgCrypt.decryptMsg(request.getParameter("msg_signature"), request.getParameter("timestamp"), request.getParameter("nonce"), convertStreamToString(request.getInputStream()));
			InputStream getDecryInput=IOUtils.toInputStream(decryMsgInfo);
			Map<String, String> getMapInfo=XMLParse.parseXml(getDecryInput);
			baseMessage=MessageUtil.messageFactory(getMapInfo);
			log.error("BaseMessage____"+JSON.toJSONString(baseMessage));
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
			
			String pln=null;
			if ("image".equals(baseMessage.getMsgType())) {
				Image image=new Image();
				image.setMediaId(((ImageMessage) baseMessage).getMediaId());
				ImageMessageResponse rep=new ImageMessageResponse();
				rep.setCreateTime(baseMessage.getCreateTime());
				rep.setFromUserName(baseMessage.getFromUserName());
				rep.setMsgId(baseMessage.getMsgId());
				rep.setMsgType(baseMessage.getMsgType());
				rep.setToUserName(baseMessage.getToUserName());
				rep.setImage(image);
				
				log.error("super___________"+JSON.toJSONString(rep));
				((ImageMessageResponse)rep).setImage(image);
				log.error("XStream.replaceHeader(ImageMessageResponse)________"+XStream.replaceHeader(rep,"xml"));
				pln=wXBizMsgCrypt.encryptMsg(XStream.replaceHeader(rep,"xml"),request.getParameter("timestamp"),request.getParameter("nonce"));
			
			}else if("text".equals(baseMessage.getMsgType())&&"笑话".equals(((TextMessage)baseMessage).getContent())){
				ArticlesMessageResponse rep=new ArticlesMessageResponse();
				rep.setCreateTime(baseMessage.getCreateTime());
				rep.setFromUserName(baseMessage.getFromUserName());
				rep.setMsgId(baseMessage.getMsgId());
				rep.setMsgType("news");
				rep.setToUserName(baseMessage.getToUserName());
				
				item item=new item();
				item.setTitle("this is a test ,don't care");
				item.setPicUrl("http://139.129.93.111/photo/1000.jpg");
				item.setDescription("I said this is a test ,don't care!");
				item.setUrl("http://view.inews.qq.com/a/NEW2016032805019904");
				
				List<item> aa=new ArrayList<item>();
				aa.add(item);
				
				rep.setArticleCount(1);
				rep.setArticles(aa);
				
				log.error("XStream.replaceHeader(NewsMessageResponse)________"+XStream.replaceHeader(rep,"xml"));
				pln=wXBizMsgCrypt.encryptMsg(XStream.replaceHeader(rep,"xml"),request.getParameter("timestamp"),request.getParameter("nonce"));
		
				
			}else{
				log.error("XStream.replaceHeader(baseMessage)________"+XStream.replaceHeader(baseMessage,"xml"));
				pln=wXBizMsgCrypt.encryptMsg(XStream.replaceHeader(baseMessage,"xml"),request.getParameter("timestamp"),request.getParameter("nonce"));
			}
			
			
			
			
			log.error("out_p______"+pln);
			out.println(pln);
		} catch (Exception e) {
			log.error("加密发送信息失败",e);
			e.printStackTrace();
		}finally{
			if(out!=null){				
				out.flush();
				out.close();
			}
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
