package com.manager.common.Util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final CloseableHttpClient httpClient;
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }


    /**
     * 获取token
     * @return
     * @throws IOException
     */
    public static JSONObject GetInfo(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        JSONObject jsons = JSONObject.parseObject(result);
        return jsons;
    }

    /**
     * 生成二维码及小程序码
     * @param access_token
     * @param width
     * @param scene
     * @param page
     * @param smallAppPath
     * @param autoColor
     * @param lineColor
     * @param tag
     * @return
     * @throws Exception
     */
    public static InputStream GetPostUrl(String access_token, String width, String scene, String page,String smallAppPath,String autoColor, String lineColor,Integer tag) throws Exception {
        String url = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if(tag.intValue()==0){
            url ="https://api.weixin.qq.com/wxa/getwxacode?access_token=";//微信小程序码
            if(autoColor!=null){
                map.put("auto_color",autoColor);
            }
            if(lineColor!=null){
                map.put("line_color",lineColor);
            }
            map.put("path", smallAppPath);//你二维码中跳向的地址
        }else if(tag.intValue() == 1){
            url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
            map.put("scene",scene);
            map.put("page", page);//你二维码中跳向的地址
            if(autoColor!=null){
                map.put("auto_color",autoColor);
            }
            if(lineColor!=null){
                map.put("line_color",lineColor);
            }
        }else{
            url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=";//微信小程序二维码
            map.put("path", smallAppPath);//你二维码中跳向的地址
        }

        map.put("width", width);//图片大小
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(map));
        System.out.println(json);
        InputStream  res= HttpPost(url+ access_token, json.toString(),null,null);
        String resStr = res.toString();
        return res;
    }

    /**
     * 根据地址获取文件流并生成文件
     * @param url
     * @param jsonData
     * @param fileName
     * @param filePath
     * @return
     * @throws UnsupportedEncodingException
     */
    public static InputStream HttpPost(String url, String jsonData, String fileName, String filePath) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
        // 拼接参数
        StringEntity se = new StringEntity(jsonData);
        se.setContentType("application/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"UTF-8"));
        httpPost.setEntity(se);
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status core :" + statusCode);
            }
            System.out.println("========HttpResponseProxy：========" + statusCode);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instreams = entity.getContent();
                inputStream = instreams;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 获取token
     * @param appId
     * @param secret
     * @return
     * @throws IOException
     */
    public static String GetToken(String appId,String secret) throws IOException {
        HttpGet httpGet = new HttpGet(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                        + appId + "&secret="
                        + secret );
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse res = httpClient.execute(httpGet);
        HttpEntity entity = res.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        JSONObject jsons = JSONObject.parseObject(result);
//        缓存
        String access_token = jsons.getString("access_token");
        return access_token;
    }

    /* @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByInputStream(InputStream instreams,String imgPath){

        int stateInt = 1;
        if(instreams != null){
            try {
                File file=new File(imgPath);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);

                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
                try {
                    instreams.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return stateInt;
    }
    /**
     * 创建目录
     * @param path
     */
    public static void createDirectory(String path) {
        File file = new File(path);
        if (file.exists()) return;
        file.getParentFile().mkdirs();
    }

    public static void inputstreamtofile(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    public static String createFile (String path) throws IOException {
        File file = new File(path);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        return file.getPath();
    }

    // 通过get请求得到读取器响应数据的数据流
    public static Map<String, Object> getInputStreamByGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
//				int state = conn.getResponseCode();
//				if(state==200){
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            Map<String, Object> map = new HashMap<String, Object>();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                String type = conn.getContentType();
                Integer size = conn.getContentLength();
//						type = type.substring(type.indexOf("/") + 1,
//								type.indexOf("/") + 4);
                type = type.substring(type.indexOf("/") + 1);
                map.put("is", inputStream);
                map.put("type", type);
                map.put("size", size);
                // return inputStream;
                return map;
            }
//				}

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }



}
