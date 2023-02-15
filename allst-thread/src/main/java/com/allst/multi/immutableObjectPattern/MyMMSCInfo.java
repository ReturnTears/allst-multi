package com.allst.multi.immutableObjectPattern;

/**
 * 业务相关数据
 *
 * @author Hutu
 * @since 2023-02-15 下午 10:28
 */
public final class MyMMSCInfo {
    // 设备编号
    private final String deviceID;
    // 对应url
    private final String url;
    // 支持最大附件大小
    private final int maxAttachmentSizeInBytes;

    public MyMMSCInfo(String deviceID, String url, int maxAttachmentSizeInBytes) {
        this.deviceID = deviceID;
        this.url = url;
        this.maxAttachmentSizeInBytes = maxAttachmentSizeInBytes;
    }

    public MyMMSCInfo(MyMMSCInfo prototype) {
        this.deviceID = prototype.deviceID;
        this.url = prototype.url;
        this.maxAttachmentSizeInBytes = prototype.maxAttachmentSizeInBytes;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachmentSizeInBytes() {
        return maxAttachmentSizeInBytes;
    }
}
