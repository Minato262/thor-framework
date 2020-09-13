package com.thor.util;

import com.sun.istack.internal.Nullable;

import java.util.Collection;

/**
 * 断言工具
 *
 * @author kay
 * @since v1.0
 */
public class Assert {

    /**
     * 检测对象为 true 的断言.
     * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param expression 需要检测的布尔类型
     * @param message    如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果需要检测的布尔类型是 false
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测对象为 false 的断言.
     * <pre class="code">Assert.notTrue(i &gt; 0, "The value must be greater than zero");</pre>
     *
     * @param expression 需要检测的布尔类型
     * @param message    如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果需要检测的布尔类型是 true
     */
    public static void notTrue(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测对象为 null 的断言.
     * <pre class="code">Assert.isNull(clazz, "The class must be null");</pre>
     *
     * @param object  需要检测的对象
     * @param message 如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果检测失败，则抛出参数异常
     */
    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测对象不能为 null 的断言.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  需要检测的对象
     * @param message 如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果检测失败，则抛出参数异常
     */
    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测字符串为空的断言.
     * <pre class="code">Assert.isEmpty(string, "The class must be empty");</pre>
     *
     * @param str     需要检测的字符串类型
     * @param message 如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果需要检测的布尔类型是 false
     */
    public static void isEmpty(@Nullable String str, String message) {
        if (str != null && !"".equals(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测字符串不为空的断言.
     * <pre class="code">Assert.notEmpty(string, "The class must not be empty");</pre>
     *
     * @param str     需要检测的字符串类型
     * @param message 如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果需要检测的布尔类型是 true
     */
    public static void notEmpty(@Nullable String str, String message) {
        if (str == null || "".equals(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检测集合元素不为null的断言.
     * <pre class="code">Assert.noNullElements(collection, "The collection must contain non-null elements");</pre>
     *
     * @param collection 需要检测的集合元素
     * @param message    如果检测失败，则需要抛出的参数异常信息
     * @throws IllegalArgumentException 如果需要检测的布尔类型是 true
     */
    public static void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }
}
