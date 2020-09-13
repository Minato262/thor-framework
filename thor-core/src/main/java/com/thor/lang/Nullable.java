package com.thor.lang;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.When;
import java.lang.annotation.*;

/**
 * 一个基础的框架注释，用于声明在某些情况下被注释的元素不能是{@code null}。
 *
 * <p>利用 JSR-305 元注释将Java中的为空的 JSR-305 工具。
 *
 * @author kay
 * @since v1.0
 * @see NonNull
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Nonnull(when = When.MAYBE)
@TypeQualifierNickname
public @interface Nullable {
}
