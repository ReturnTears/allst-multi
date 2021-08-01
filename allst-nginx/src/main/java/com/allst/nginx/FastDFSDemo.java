package com.allst.nginx;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 操作FastDFS文件系统API，需确保服务已启动，启动过程查看README-NGINX.md文档
 *
 * 以下三个静态方法可以提取出静态块或者提取出一个公共方法
 *
 * @author June
 * @since 2021年08月
 */
public class FastDFSDemo {
    public static void main(String[] args) {
        // uploadFile();
        // queryFile();
        downloadFile();
    }

    /**
     * 文件上传
     */
    private static void uploadFile() {
        try {
            // 加载配置文件
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

    /**
     * 文件信息查看
     */
    private static void queryFile() {
        try {
            // 加载配置文件
            ClientGlobal.initByProperties("fastdfs-client.properties");
            // 创建tracker客户端
            TrackerClient tc = new TrackerClient();
            // 根据tracker客户端创建连接 获取到跟踪服务器对象
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            // 定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            // 查询文件信息
            FileInfo fileInfo = client.query_file_info1("group1/M00/00/00/wKgAZGEGnUKADrmYAAAS9Z2qRZQ186.png");
            // 打印：source_ip_addr = 192.168.0.100, file_size = 4853, create_timestamp = 2021-08-01 21:10:26, crc32 = -1649785452
            System.out.println(fileInfo);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件
     */
    private static void downloadFile() {
        try {
            // 加载配置文件
            ClientGlobal.initByProperties("fastdfs-client.properties");
            // 创建tracker客户端
            TrackerClient tc = new TrackerClient();
            // 根据tracker客户端创建连接 获取到跟踪服务器对象
            TrackerServer ts = tc.getConnection();
            StorageServer ss = null;
            // 定义storage客户端
            StorageClient1 client = new StorageClient1(ts, ss);
            // 下载
            byte[] bs = client.download_file1("group1/M00/00/00/wKgAZGEGnUKADrmYAAAS9Z2qRZQ186.png");
            // 下载成功会在工程目录下生成一个mydfs.png文件
            FileOutputStream fos = new FileOutputStream(new File("mydfs.png"));
            fos.write(bs);
            fos.close();
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }
}

