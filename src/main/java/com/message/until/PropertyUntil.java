package com.message.until;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUntil {
	
	/**
	 * 根据key值  获取wechat.properties中的值
	 * @param key
	 * @return
	 */
	public static String get(String key){
		String rtn=null;
		InputStream inputStream=null;        
		try {
			System.out.println(System.getProperty("user.dir"));
			inputStream = new FileInputStream("src/wechat.properties");        
			Properties dbProps =  new  Properties();        
			dbProps.load(inputStream);
			rtn=String.valueOf(dbProps.get(key));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=inputStream){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}   
		}
		return rtn;
	}
}
