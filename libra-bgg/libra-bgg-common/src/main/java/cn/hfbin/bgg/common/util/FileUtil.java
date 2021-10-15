package cn.hfbin.bgg.common.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author: huangfubin
 * @Description: FileUtil 类
 * @Date: 2021/10/14
 */
public class FileUtil {

    public static InputStream getInputStream(ApplicationContext applicationContext, String path) {
        if (StringUtils.isEmpty(path)) {
            System.out.println("load file failed, path entity : " + path);
            return null;
        }
        try {
            String filePath = applicationContext.getEnvironment().resolvePlaceholders(path);
            InputStream inputStream = applicationContext.getResource(filePath).getInputStream();
            System.out.println("load file success : " +  path);
            return inputStream;
        } catch (Exception e) {
            System.out.println("load file failed : " + path);
        }
        return null;
    }

    public static String getText(ApplicationContext applicationContext, String path) {
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(applicationContext, path);
            if (inputStream != null) {
                try {
                    return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    System.out.println("load file failed : " + path);
                }
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}