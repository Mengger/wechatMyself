package com.test.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.wechat.mp.aes.DicSort;
import com.wechat.mp.aes.WXBizMsgCrypt;
import com.wechat.mp.aes.XMLParse;

public class TokenServlet extends HttpServlet {
	private final static Log log = LogFactory.getLog(TokenServlet.class);
	public TokenServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			String []readyIn=new String[3];
			String timeStamp=request.getParameter("timestamp");
			String nonce=request.getParameter("nonce");
			readyIn[0]="TokenTest";
			readyIn[1]=timeStamp;
			readyIn[2]=nonce;
			String rtn=DicSort.getSHA(readyIn);
			String rqu=request.getParameter("signature");
			if(rqu.equals(rtn)){
				String tnt=request.getParameter("echostr");
				if(null==tnt){
					
					String readInputStreamString=convertStreamToString(request.getInputStream());
					log.error(readInputStreamString);
					
					String encodingAesKey="108FCjedazgrCLC7Kpo8dIXz5hSbGrfTpR0NP8E0zKA";
					String appId="wx56247977be201113";
					WXBizMsgCrypt wXBizMsgCrypt=new WXBizMsgCrypt("TokenTest", encodingAesKey, appId);
					
					String msgSignature=request.getParameter("msg_signature");
					String decryMsgInfo=wXBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, readInputStreamString);
					log.error("********decryMsgInfo************"+decryMsgInfo);
					
					InputStream getDecryInput=IOUtils.toInputStream(decryMsgInfo);
					Map<String, String> getMapInfo=XMLParse.parseXml(getDecryInput);
					
					log.error(JSON.toJSONString(getMapInfo));
					
					
					String tt=XMLParse.generateMessage(getMapInfo.get("FromUserName"), getMapInfo.get("ToUserName"), getMapInfo.get("CreateTime"), getMapInfo.get("MsgType"), "hello word");
					
					log.error(tt);
					
					String outAnswer=wXBizMsgCrypt.encryptMsg(tt, timeStamp, nonce);
					out.println(outAnswer);
					log.error(outAnswer);
				}
				if(null!=tnt){					
					out.println(tnt);
				}
				log.error(tnt);
			}
		} catch (Exception e) {
			log.error("something is wrong！",e);
		}
		out.flush();
		out.close();
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

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
