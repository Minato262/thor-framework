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
package com.thor.container.resoure;

/**
 * 类资源载入失败异常
 *
 * @author kay
 * @version v2.0
 */
class ClassResourceLoadFailureException extends ClassResourceRuntimeException {
    private static final long serialVersionUID = -6814085112361052234L;

    /**
     * 默认构造器
     */
    ClassResourceLoadFailureException() {
        super();
    }
}
