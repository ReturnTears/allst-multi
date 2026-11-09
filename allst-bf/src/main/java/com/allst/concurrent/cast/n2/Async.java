package com.allst.concurrent.cast.n2;

import com.allst.concurrent.cast.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;

/**
 * 异步执行
 * @author YiYa
 * @since 2020-01-08 下午 10:09
 */
@Slf4j(topic = "c.Async")
public class Async {

    public static void main(String[] args) {
        new Thread(() -> {
            try (FileReader fileReader = new FileReader(Constants.MP4_FULL_PATH)) {
                char[] buffer = new char[1024];
                int charsRead;
                StringBuilder content = new StringBuilder();

                // read(char[]) 返回实际读取的字符数，-1 表示已到文件末尾
                while ((charsRead = fileReader.read(buffer)) != -1) {
                    content.append(buffer, 0, charsRead);
                }
                log.info("read content : {}", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        log.debug("do other things....");
    }

}
