package com.message.until;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: zht
 * Date: 15-4-15
 * Time: 下午3:36
 * HTTP操作工具类
 */
public class HttpUtils {
    public static final String POST = "POST";
    public static final String GET = "GET";

    /**
     * HTTP POST 操作，参数是JSON格式
     * @param strURL        URL地址
     * @param json          JSON参数
     * @param session       登录的SESSION数据，第一次登录为空
     * @return  返回字符串数据
     */
    public static HttpResponse doHttpPostJson(String strURL, String json, String session) {
        return doHttpPost(strURL, true, json, session);
    }

    /**
     * HTTP POST操作，参数不是JSON格式
     * @param strURL        URL地址
     * @param param         参数
     * @param session       登录的SESSION数据，第一次登录为空
     * @return  返回字符串数据
     */
    public static HttpResponse doHttpPost(String strURL, String param, String session) {
        return doHttpPost(strURL, false, param, session);
    }

    /**
     * HTTP POST操作
     * @param strURL        URL地址
     * @param jsonParam     参数是否是JSON格式
     * @param param         参数，可以是JSON
     * @param session       登录的SESSION数据，第一次登录为空
     * @return  返回字符串数据
     */
    private static HttpResponse doHttpPost(String strURL, boolean jsonParam, String param, String session) {
        DataOutputStream out = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod(POST); // 设置请求方式
            connection.setRequestProperty("Accept", "*/*"); // 设置接收数据的格式
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
            if(jsonParam) {
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8"); //JSON格式发送
            }
            else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); //普通格式发送
            }
            if(session != null) {
                connection.setRequestProperty("Cookie", session); //设置Session信息
            }
            //连接
            connection.connect();

            if(param != null) {
                // 写入数据
                out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(param); //参数
                out.flush();
                out.close();
            }

            // 使用 BufferedReader 输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            //获取Session，Cookie:JSESSIONID=B15EC4BB0B75644D04116FD3E516C684;
            String sessionText = connection.getHeaderField("Set-Cookie"); //获取SESSION
            connection.disconnect(); //断开连接

            HttpResponse httpResponse = new HttpResponse();
            httpResponse.setResponseText(builder.toString());
            httpResponse.setSession(sessionText);
            return httpResponse;
        }
        catch (Exception e) {
            System.out.println("HTTP POST请求失败：" + e.getLocalizedMessage());
        }
        finally {
            close(out); //关闭流
            close(reader); //关闭流
        }

        return null;
    }

    /**
     * 关闭
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        }
        catch (Exception e) {
            System.out.println("关闭失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
