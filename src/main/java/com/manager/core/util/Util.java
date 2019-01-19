package com.manager.core.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Util {

    private static int sequence = 10000;

    public static SimpleDateFormat StampFmt = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");
    public static SimpleDateFormat DateFmt = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DateTimeFmt = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat DateZipFmt = new SimpleDateFormat("yyMMdd");
    public static SimpleDateFormat TimeZipFmt = new SimpleDateFormat("HHmm");

    public static SimpleDateFormat StampZipFmt = new SimpleDateFormat(
            "yyyyMMddHHmmssSSS");

    /**
     * 字符串空值转换为默认值
     *
     * @param ori Object 原始字符串
     * @param def String 默认字符串
     * @return String 转换结果
     */
    public static String nvl(Object ori, String def) {
        if (ori == null) {
            return def;
        } else {
            return ori.toString();
        }
    }

    /**
     * 字符串是否为数字
     *
     * @param ori String 原始字符串
     * @return boolean 判断结果
     */
    public static boolean isNum(String ori) {
        try {
            Double temp = Double.parseDouble(ori);
            if (temp > 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * 取当前时间
     *
     * @return 格式： yyyyMMddHHmmss + 序列号
     */
    public static String RunTimeSequence() {
        return Util.CurrentTime("yyMMddhhmmss") + (sequence++);
    }

    /**
     * 取当前时间
     *
     * @param format 格式示例： yyyyMMddHHmmss
     * @return
     */
    public static String CurrentTime(String format) {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat(format);
        return outFormat.format(now);
    }

    /**
     * 取Unix时间
     *
     * @param date
     * @return
     */
    public static long getUnixTime(Date date) {
        if (null == date) {
            return 0;
        }
        return date.getTime() / 1000;
    }

    public static long getUnixTime2(String format, Date d)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        DateFormat df = DateFormat.getDateInstance();
        String date = df.format(d);
        Date date1 = sdf.parse(date);
        return getUnixTime(date1);
    }

    /**
     * 生成随机数
     *
     * @param length int 随机数长度
     * @return int 随机数
     */
    public static int BuildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 过滤HTML代码
     *
     * @param input String HTML字符串
     * @return String 过滤后结果
     */
    public static String FilterHtml(String input) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        return input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<([^>]*)>", "");
    }

    /**
     * 将类似userName转换成user_name形式
     *
     * @param s CameCase字符串
     * @return 转换后的字符串
     */
    public static String CamelCaseToDb(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c) && i != 0) {
                builder.append("_");
            }
            builder.append(Character.toLowerCase(c));
        }
        return builder.toString();
    }

    //sql注入校验
    public static boolean sqlValidate(String str) {  
        str = str.toLowerCase();//统一转为小写  
        String badStr = 
        		"'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +  
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|like'|and|exec|execute|insert|create|drop|" +  
                "table|from|grant|use|group_concat|column_name|" +  
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加  
        String[] badStrs = badStr.split("\\|");
        String[] split = str.split(" ");
        List<String> list = Arrays.asList(badStrs);
        List<String> asList = Arrays.asList(split);
        for (String string : list) {
        	if(asList.contains(string)){
        		return true;
        	}
		}
        return false;
    }  

    public static String encodeAscii(String str) {
        StringBuilder sbu = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(" ");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    public static String decodeAscii(String code) {
        StringBuilder sbu = new StringBuilder();
        String[] chars = code.split(",");
        for (String s : chars) {
            sbu.append((char) Integer.parseInt(s));
        }
        return sbu.toString();
    }
}
