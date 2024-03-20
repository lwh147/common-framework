package com.lwh147.common.web.component;

import com.lwh147.common.util.constant.NumberConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;

/**
 * Banner打印，默认开启
 *
 * @author lwh
 * @date 2021/12/7 16:49
 **/
@Slf4j
@Component
@ConditionalOnProperty(name = "web.enable-banner-print", havingValue = "true", matchIfMissing = true)
public class BannerPrinter {
    /**
     * Banner存储文件名
     **/
    private static final String FILE = "AppInfoBanner.txt";

    /**
     * PostConstruct阶段完成Banner的打印
     **/
    @PostConstruct
    public void print() {
        URL url = this.getClass().getClassLoader().getResource(FILE);
        if (url == null) {
            log.warn("未找到{}文件", FILE);
            return;
        }
        File file = new File(url.getPath());
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder sb = new StringBuilder();
                int charRead;
                char[] buffer = new char[NumberConstant.DEFAULT_BUFFERED_SIZE];
                while ((charRead = bufferedReader.read(buffer)) > 0) {
                    sb.append(buffer, 0, charRead);
                }
                System.out.println(sb.toString());
                bufferedReader.close();
                fileReader.close();
            } catch (FileNotFoundException e) {
                log.warn("未找到{}文件", FILE, e);
            } catch (IOException e) {
                log.warn("{}文件读取失败", FILE, e);
            }
        } else {
            log.warn("未找到{}文件", FILE);
        }
    }
}