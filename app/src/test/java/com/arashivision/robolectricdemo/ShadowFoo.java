package com.arashivision.robolectricdemo;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 15:01.
 */
@Implements(Foo.class)
public class ShadowFoo {
    /**
     * 通过@RealObject注解可以访问原始对象，但注意，通过@RealObject注解的变量调用方法，依然会调用Shadow类的方法，而不是原始类的方法 只能用来访问原始类的field
     */
    @RealObject
    Foo mFoo;

    /*需要一个无参构造方法*/
    public ShadowFoo() {
    }

    /**
     * 对应原始类的构造方法
     *
     * @param name 对应原始类构造方法的传入参数
     */
    public void __constructor__(String name) {
        mFoo.name = name;
    }

    /**
     * 原始对象的方法被调用的时候，Robolectric会根据方法签名查找对应的Shadow方法并调用
     */
    @Implementation
    public String getName() {
        return "Hello, I'm a shadow of Foo: " + mFoo.name;
    }

    @Implementation
    public void setName(String name) {
        mFoo.name = name;
    }
}