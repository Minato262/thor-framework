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
package com.thor.core.codec;

/**
 * Unicode 编码 常量枚举
 *
 * @author kay
 * @version v1.0
 */
public enum Unicode {

    /**
     * UTF-8（8-bit Unicode Transformation Format）是一种针对Unicode的可变长度字符编码，
     * 又称万国码。标准化为RFC 3629
     */
    UTF_8("UTF-8");

    private final String code;

    Unicode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
