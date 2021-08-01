package com.allst.nginx;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * @author June
 * @since 2021年08月
 */
public class FastDFSDemo {
    public static void main(String[] args) {
        uploadFile();
    }

    /**
     * 文件上传
     */
    private static void uploadFile() {
        // 加载配置文件
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            // 创建client
            TrackerClient trackerClient = new TrackerClient();
            // 根据trackerClient获取连接,获取跟踪服务器对象
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            // 定义存储客户端
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 可以添加元数据信息，(也可以不添加)
            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("filename", "nginx-001.png");
            String fileId = storageClient1.upload_file1("allst-nginx/src/main/resources/images/nginx001.png", "png", list);
            // 上传成功返回文件存储路径：group1/M00/00/00/wKgAZGEGnUKADrmYAAAS9Z2qRZQ186.png
            System.out.println(fileId);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
}

