package com.message.until;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.message.pjo.response.ArticlesMessageResponse;
import com.message.pjo.response.MusicMessageResponse;
import com.message.pjo.response.unit.Music;
import com.message.pjo.response.unit.item;


public class XStream {
	
	private final static Log log = LogFactory.getLog(XStream.class);
	
	/**
	 * 根据入参 判断是否需要继续下探
	 * @param clazz
	 * @return
	 */
	public static boolean isDepend(Class clazz){
		if(!(clazz.toString().contains("class")||clazz.toString().contains("interface"))){
			return false;
		}else if("String".equals(clazz.getSimpleName())){
			return false;
		}
		return true;
	}

	/**
	 * 根据obj的属性  生成对应的xml格式
	 * @param xmlType
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj){
		Class clazz=obj.getClass();
		Field []fields=clazz.getDeclaredFields();
		List<String> attributeList=new ArrayList<String>();
		StringBuffer attributeStatus=new StringBuffer();
		for(Field field:fields){
			attributeList.add(field.getName());
			if (isDepend(field.getType())) {
				attributeStatus.append("y");
			}else{
				attributeStatus.append("n");
			}
		}
		for(Field superfield:clazz.getSuperclass().getDeclaredFields()){
			attributeList.add(superfield.getName());
			if (isDepend(superfield.getType())) {
				attributeStatus.append("y");
			}else{
				attributeStatus.append("n");
			}
		}
		
		byte[] byteStatus= attributeStatus.toString().getBytes();

		return generatePartInfo(obj, attributeList, byteStatus);
	}
	
	public static String replaceHeader(Object obj,String replace){
		String rtn="";
		if(obj instanceof  java.lang.Iterable){
			rtn+="<"+replace+">\n";
			for(Object ooj:((List)obj)){
				rtn+=toXML(ooj)+"\n";
			}
			rtn+="</"+replace+">\n";
		}else{
			rtn=toXML(obj);
			String target=obj.getClass().getSimpleName();
			int size=target.length();
			rtn=rtn.substring(size+2);
			int surplue=rtn.length();
			rtn=rtn.substring(0,surplue-size-3);
			rtn="<"+replace+">"+rtn+"</"+replace+">";
		}
		return rtn;
	}
	
	public static String generatePartInfo(Object obj,List<String> attributeList,byte[] byteStatus){
		String name=obj.getClass().getSimpleName();
		StringBuffer formate=new StringBuffer();
		formate.append("<").append(name).append(">").append("\n");
		int i=1;
		List<String> methodNames=new ArrayList<String>();
		int num=1;
		for(String attribute:attributeList){
			if(byteStatus[i-1]==110){//110代表n  121代表y			
				methodNames.add("get"+StringUtils.capitalise(attribute));
				formate.append("<").append(attribute).append("><![CDATA[%").append(num).append("$s]]></").append(attribute).append(">\n");
				num++;
			}else{
				try {
					String[] methods={"get"+StringUtils.capitalise(attribute)};
					
					formate .append(replaceHeader(executeNoInputMethod(obj, methods).get(0),attribute)).append("\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			i++;
		}
		formate.append("</").append(name).append(">");
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
		ArticlesMessageResponse rep=new ArticlesMessageResponse();
		rep.setCreateTime(new Date().getTime());
		rep.setFromUserName("FromUser");
		rep.setMsgId(1945641652L);
		rep.setMsgType("TEXT");
		rep.setToUserName("TOUser");
		
		item item=new item();
		item.setTitle("This is a test ,don't care");
		item.setPicUrl("http://inews.gtimg.com/newsapp_bt/0/227088706/1000");
		item.setDescription("I said this is a test ,don't care!");
		item.setUrl("http://view.inews.qq.com/a/NEW2016032805019904");
		
		item item1=new item();
		item1.setTitle("This is a test ,don't care111111111111111111");
		item1.setPicUrl("http://inews.gtimg.com/newsapp_bt/0/227088706/1000");
		item1.setDescription("I said this is a test ,don't care!");
		item1.setUrl("http://view.inews.qq.com/a/NEW2016032805019904");
		
		List<item> aa=new ArrayList<item>();
		aa.add(item);
		aa.add(item1);
		rep.setArticleCount(2);
		rep.setArticles(aa);
		
		System.out.println(replaceHeader(rep,"xml"));
	
	}
}
