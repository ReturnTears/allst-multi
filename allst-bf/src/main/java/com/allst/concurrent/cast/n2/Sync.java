package com.allst.concurrent.cast.n2;

import com.allst.concurrent.cast.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;

/**
 * 同步执行
 * @author YiYa
 * @since 2020-01-08 下午 09:57
 */
@Slf4j(topic = "c.Sync")
public class Sync {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader(Constants.MP4_FULL_PATH);    // 同步
            int read = reader.read();
            log.debug(read + "");
            log.debug("do other things....");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
