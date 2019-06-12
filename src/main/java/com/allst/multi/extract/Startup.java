package com.allst.multi.extract;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 程序入口
 * @author June
 * @version 1.0
 * @date 2018-06-27
 */
public class Startup {

    /**
     * 数据源路径
     */
    private static final String DATA_DIR = "E:\\TestData\\test";

    /**
     * 定义List集合
     */
    public static List errorLog = new ArrayList();

    public static void main(String[] args) {
        try {
            String dirName = DATA_DIR;
            File dir = new File(dirName);
            File[] dataFiles = dir.listFiles(pathname -> {
                if (StringUtils.indexOf(pathname.getName(), ".txt") != -1) {
                    return true;
                }
                return false;
            });
            for (File file : dataFiles) {
                /**
                 * 1.启动请求日志和结束日志数据ETL线程
                 * 2.用来描述将数据从来源端经过抽取（extract）、转换（transform）、加载（load）至目的端的过程
                 */
                System.out.println("startEtl starting...");
                startEtl(file);
                System.out.println("startEtl end....");
                // 3.写错误日志到txt
                writeErrorLogs("E:\\TestData\\test/"+file.getName()+"_error.txt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ETL开始方法
     * @param log
     */
    private static void startEtl(File log) {
        try {
            DataContainer dataContainer = new DataContainer();
            final CountDownLatch countDownLatch = new CountDownLatch(5);
            ThreadPool.getInstance().dispatchReadDataThread(new ReadDataService(log, dataContainer, countDownLatch));
            ThreadPool.getInstance().dispatchInsertDataThread(new InsertDataService(getBatchInsertSQL(), dataContainer, countDownLatch));
            ThreadPool.getInstance().dispatchInsertDataThread(new InsertDataService(getBatchInsertSQL(), dataContainer, countDownLatch));
            ThreadPool.getInstance().dispatchInsertDataThread(new InsertDataService(getBatchInsertSQL(), dataContainer, countDownLatch));
            ThreadPool.getInstance().dispatchInsertDataThread(new InsertDataService(getBatchInsertSQL(), dataContainer, countDownLatch));
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBatchInsertSQL(){
        return " insert into  t_log_requester1 (SENDER_ID, RECEIVE_ID, RECEIVE_TIME)  values (?,?,?) ";
    }

    private static void writeErrorLogs(String path){
        for (Object log : errorLog) {
            appendErrorLogsToTXT(path, (String)log);
        }
        errorLog.clear();
    }

    private static void appendErrorLogsToTXT(String path,String content){
        try {
            FileWriter output = new FileWriter(path,true);
            output.write("\n");
            output.write(content);
            output.close();
        } catch (Exception e) {
            System.out.println("write to file error");
        }
    }
}
