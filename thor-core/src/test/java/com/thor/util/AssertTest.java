package com.thor.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 断言测试
 *
 * @author kay
 * @since v1.0
 */
public class AssertTest {

    @Test
    public void isTrue() {
        Assert.isTrue(true, "The class must be true");
    }

    @Test
    public void notTrue() {
        Assert.notTrue(false, "The class must be false");
    }

    @Test
    public void isNull() {
        Assert.isNull(null, "The class must be null");
    }

    @Test
    public void notNull() {
        Object object = new Object();
        Assert.notNull(object, "The class must not be null");
    }

    @Test
    public void isEmpty() {
        Assert.isEmpty("", "The class must be empty");
    }

    @Test
    public void notEmpty() {
        Assert.notEmpty("this is test.", "The class must not be empty");
    }

    @Test
    public void noNullElements() {
        List<String> list = new ArrayList<String>();
        list.add("null");
        Assert.noNullElements(list, "The collection must contain non-null elements");

        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        Assert.noNullElements(map.values(), "The collection must contain non-null elements");
    }
}
