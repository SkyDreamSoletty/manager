package com.manager.common.Util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

public class WeixinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    public static String getWeChatUniqueId(String appId, String secret, String grantType, String jsCode) throws IOException {
        logger.info("获取用户唯一ID参数："+appId+":"+secret+":"+grantType+":"+jsCode);
        Map<String,Object> data = new HashMap<String, Object>();
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+secret+"&grant_type="+grantType+"&js_code="+jsCode;
            JSONObject json = Util.GetInfo(url);
            logger.info("微信返回值："+json.toJSONString());
            if(json.getString("openid")==null){
                return null;
            }else{
                String OpenId = json.getString("openid");
                data.put("openid",OpenId);
                return OpenId;
            }
    }

    /**
     * 获取支付所需签名
     * @param bizObj
     * @param key
     * @return
     * @throws Exception
     */
    public static String getPayCustomSign(Map<String, String> bizObj,String key) throws Exception {
        String bizString = FormatBizQueryParaMap(bizObj, false);
        return sign(bizString, key);
    }

    /**
     * 字典排序
     * @param paraMap
     * @param urlencode
     * @return
     * @throws Exception
     */
    public static String FormatBizQueryParaMap(Map<String, String> paraMap,
                                               boolean urlencode) throws Exception {
        String buff = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(paraMap.entrySet());
            Collections.sort(infoIds,
                    new Comparator<Map.Entry<String, String>>() {
                        public int compare(Map.Entry<String, String> o1,
                                           Map.Entry<String, String> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });
            for (int i = 0; i < infoIds.size(); i++) {
                Map.Entry<String, String> item = infoIds.get(i);
                //System.out.println(item.getKey());
                if (item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (urlencode) {
                        val = URLEncoder.encode(val, "utf-8");
                    }
                    buff += key + "=" + val + "&";
                }
            }
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return buff;
    }

    //支付所需签名处调用此方法
    public static String sign(String content, String key)
            throws Exception{
        String signStr = "";
        signStr = content + "&key=" + key;
        return MD5(signStr).toUpperCase();
    }

    //上一方法，MD5加密处理
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
