package com.thor.core;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link NestedExceptionUtils} 的测试类。
 *
 * @author kay
 * @since v1.0
 */
public class NestedExceptionUtilsTest {

    private RuntimeException runtimeException = new RuntimeException();

    @Test
    public void buildMessage() {
        Assert.assertNotNull(NestedExceptionUtils.buildMessage("this is test", runtimeException));

        runtimeException = new RuntimeException("this is test");
        Assert.assertNotNull(NestedExceptionUtils.buildMessage("", runtimeException));

        Assert.assertNull(NestedExceptionUtils.buildMessage(null, null));
        Assert.assertNotNull(NestedExceptionUtils.buildMessage("", null));
    }

    @Test
    public void getRootCause() {
        Assert.assertNull(NestedExceptionUtils.getRootCause(runtimeException));
    }
}
