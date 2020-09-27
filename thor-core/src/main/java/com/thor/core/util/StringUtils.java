/*
 * Copyright 2017-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thor.core.util;

/**
 * 字符串常用工具类
 *
 * <p>用于简化对字符串的常规处理</p>
 *
 * @author kay
 * @version v1.0
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * <p>
     *    eg:
     *    <br>StringUtils.isEmpty(null)   = true  </br>
     *    <br>StringUtils.isEmpty("")     = true  </br>
     *    <br>StringUtils.isEmpty("null") = false </br>
     *    <br>StringUtils.isEmpty("aBCS") = false </br>
     * </p>
     *
     * @param str 需要判断的字符串
     * @return 判断是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 将字符串的首字母变为小写
     * <p>
     *    eg:
     *    <br>StringUtils.toLowercaseFirst("0")    = "0"   </br>
     *    <br>StringUtils.toLowercaseFirst("10")   = "10"  </br>
     *    <br>StringUtils.toLowercaseFirst("ABCS") = "aBCS"</br>
     *    <br>StringUtils.toLowercaseFirst("aBCS") = "aBCS"</br>
     * </p>
     *
     * @param str 需要首字母小写的字符串（一定不能为空）
     * @return 处理后的字符串（首字母为小写）
     * @throws IllegalArgumentException 如果字符串为空，则抛出异常
     */
    public static String toLowercaseFirst(String str) {
        Assert.isNotEmpty(str);
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        else {
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
    }
}
