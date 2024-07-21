package com.bai.pojo;


public class LoginLog {
    private int log_id;
    private String account;
    private String logInTime;
    private String deviceName;

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(String logInTime) {
        this.logInTime = logInTime;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "log_id=" + log_id +
                ", account='" + account + '\'' +
                ", logInTime='" + logInTime + '\'' +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

    public LoginLog(int log_id, String account, String logInTime, String deviceName) {
        this.log_id = log_id;
        this.account = account;
        this.logInTime = logInTime;
        this.deviceName = deviceName;
    }
}
