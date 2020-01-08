package com.allst.concurrent.cast.n2;

import com.allst.concurrent.cast.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
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
            try {
                FileReader fileReader = new FileReader(Constants.MP4_FULL_PATH);
                int read = fileReader.read();
                log.debug(read + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        log.debug("do other things....");
    }

}
