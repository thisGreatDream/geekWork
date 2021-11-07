package com.geek.jvmwork;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: lishuangqiang
 * @Date: 2021/11/7
 * @Description:自定义一个类加载器，用于将字节码转换为class对象
 */
public class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        // 获取输入流
        String suffix = ".xlass";
        String name = className + suffix;
        Resource resource = new ClassPathResource(name);
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            // 读取数据
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray);
            // 转换
            byte[] classBytes = decode(byteArray);
            // 通知底层定义这个类
            return defineClass(className, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
        }
        return null;
    }

    /**
     * 解码
     *
     * @param byteArray
     */
    private static byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (255 - byteArray[i]);
        }
        return targetArray;
    }

    /**
     * 关闭
     *
     * @param res
     */
    private static void close(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}