package com.lwh147.common.web.autoconfigure;

import com.lwh147.common.core.constant.NumberConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Objects;

/**
 * 打印AppInfoBanner
 *
 * @author lwh
 * @date 2021/12/7 16:49
 **/
@Slf4j
@Component
public class BannerPrinter {
    private static final String FILE = "AppInfoBanner.txt";

    @PostConstruct
    public void print() {
        File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(FILE)).getPath());
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
        }
    }
}
