package com.thor.core.util;

import com.thor.core.codec.Unicode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidParameterException;

/**
 * 解码常用工具类
 *
 * @author kay
 * @version v1.0
 */
public class Decoder {

    /**
     * 转译成 uft-8 类型字符串
     *
     * @param source 来源（一定不能为空）
     * @return 转译后的字符串
     * @throws InvalidParameterException 如果字符串为空
     */
    public static String decode(String source) {
        Assert.isNotEmpty(source);
        try {
            return URLDecoder.decode(source, Unicode.UTF_8.getCode());
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
