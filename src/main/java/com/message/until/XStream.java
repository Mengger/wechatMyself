package com.message.until;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.message.pjo.response.Music;
import com.message.pjo.response.MusicMessage;


public class XStream {
	
	private final static Log log = LogFactory.getLog(XStream.class);

	/**
	 * 根据obj的属性  生成对应的xml格式
	 * @param xmlType
	 * @param obj
	 * @return
	 */
	public static String generaterFormateFromClass(Object obj){
		Class clazz=obj.getClass();
		Field []fields=clazz.getDeclaredFields();
		List<String> attributeList=new ArrayList<String>();
		for(Field field:fields){
			System.out.println(field.getName()+" ******************* "+field.getType());
			//field.
			attributeList.add(field.getName());
		}
		for(Field superfield:clazz.getSuperclass().getDeclaredFields()){
			System.out.println(superfield.getName()+" ******************* "+superfield.getType());
			attributeList.add(superfield.getName());
		}
		StringBuffer formate=new StringBuffer();
		formate.append("<xml>").append("\n");
		int i=1;
		List<String> methodNames=new ArrayList<String>();
		for(String attribute:attributeList){
			methodNames.add("get"+StringUtils.capitalise(attribute));
			formate.append("<").append(attribute).append("><![CDATA[%").append(i).append("$s]]></").append(attribute).append(">\n");
			i++;
		}
		formate.append("</xml>");
		return String.format(formate.toString(), executeNoInputMethod(obj,methodNames.toArray(new String[]{})).toArray());
	}
	
	public static List<Object> executeNoInputMethod(Object object,String []methods){
		Class clazz=object.getClass();
		List<Object> rtn=new ArrayList<Object>();
		for(String methodName:methods){
			try {
				Object obj=clazz.getMethod(methodName).invoke(object);
				if(null!=obj){
					rtn.add(obj);
				}else{
					rtn.add("");
				}
			} catch (Exception e) {
				log.error("执行方法"+methodName+"错误",e);
			} 
		}
		return rtn;
	}
	
	
	public static void main(String[] args) {
		MusicMessage aa=new MusicMessage();
		Music music=new Music();
		aa.setMusic(music);
		aa.setCreateTime(new Date().getTime());
		aa.setFromUserName("asdfasdfdsafsdf");
		System.out.println(generaterFormateFromClass(aa));
	}
}
