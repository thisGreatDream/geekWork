package com.geek.jvmwork;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author: lishuangqiang
 * @Date: 2021/11/7
 * @Description:测试类
 */
public class LoadClassTest {

    public static void main(String[] args) throws Exception {
        // 相关参数
        final String className = "Hello";
        // 加载相应的类
        Class<?> clazz = new MyClassLoader().findClass(className);
        if (Objects.nonNull(clazz)) {
            System.out.println("clazz为" + clazz);
            for (Method m : clazz.getDeclaredMethods()) {
                System.out.println("类名为" + clazz.getSimpleName());
                System.out.println("方法名为" + m.getName());
                // 创建对象
                Object instance = clazz.getDeclaredConstructor().newInstance();
                // 调用实例方法
                Method method = clazz.getMethod(m.getName());
                System.out.println("执行方法");
                method.invoke(instance);
            }
        }

    }
}