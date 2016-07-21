package com.ryanst.app.widget;

/**
 * Created by kevin on 16/5/31.
 */
public class AudioEvent {
    public static final int PLAY_START = 100;// 开始播放
    public static final int PLAY_STOP = 101;//停止播放
    public static final int PLAY_COMPLETE = 102;//播放完成
    public static final int PLAY_ERROR = 103;//播放错误
    public static final int PLAY_FAIL = 104;//播放失败
    public static final int PLAY_UPDATE = 105;
    public static final int LOAD_COMPLETE = 106;

    private int type;
    private int currentDuraion;
    private int progress;
    private int totalDuration;
    private String message;

    public AudioEvent() {
    }

    public AudioEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCurrentDuraion() {
        return currentDuraion;
    }

    public void setCurrentDuraion(int currentDuraion) {
        this.currentDuraion = currentDuraion;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
