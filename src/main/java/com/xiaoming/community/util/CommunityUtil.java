package com.xiaoming.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;
import java.util.UUID;

/**
 * 通用方法
 *
 * @author 赵明城
 * @date 2022/8/17
 */
public class CommunityUtil {

    /**
     * 生成随机字符串
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * MD5加密
     *
     * @param key
     * @return
     */
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /**
     * 装换Json字符串方法
     *
     * @param code
     * @param msg
     * @param map
     * @return
     */
    public static String getJsonString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    /**
     * 装换Json字符串方法(多态)
     *
     * @param code
     * @param msg
     * @return
     */
    public static String getJsonString(int code, String msg) {

        return getJsonString(code, msg, null);
    }

    /**
     * 装换Json字符串方法(多态)
     *
     * @param code
     * @return
     */
    public static String getJsonString(int code) {

        return getJsonString(code, null, null);
    }

}
