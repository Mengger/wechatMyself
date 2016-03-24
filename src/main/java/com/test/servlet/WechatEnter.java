package com.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.message.pjo.request.BaseMessage;
import com.message.server.PackAndPrase;
import com.message.until.PropertyUntil;
import com.message.until.XStream;
import com.wechat.mp.aes.AesException;
import com.wechat.mp.aes.DicSort;
import com.wechat.mp.aes.WXBizMsgCrypt;

public class WechatEnter extends HttpServlet {
	private final static Log log = LogFactory.getLog(WechatEnter.class);
	/**
	 * Constructor of the object.
	 */
	public WechatEnter() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String []readyIn=new String[3];
		String timeStamp=request.getParameter("timestamp");//获取消息生成时间
		String nonce=request.getParameter("nonce");//获取随机数
		readyIn[0]=PropertyUntil.get("Token");//获取该公众号的令牌
		readyIn[1]=timeStamp;
		readyIn[2]=nonce;
		
		String generateSignature=DicSort.getSHA(readyIn);//按照对应的规则生成消息校验位
		
		//获取消息校验位
		String signature=request.getParameter("signature");
		if(generateSignature.equals(signature)){
			PackAndPrase packAndPrase=new PackAndPrase(request, response);
			
			packAndPrase.parse();
			
			
			
			
			packAndPrase.sendInfo();
			
		}
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
