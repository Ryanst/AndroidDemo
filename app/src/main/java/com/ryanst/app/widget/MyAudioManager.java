package com.ryanst.app.widget;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.rlib.util.MD5Util;
import com.ryanst.app.R;
import com.ryanst.app.util.LogUtil;
import com.ryanst.app.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 15/9/29.
 * 消息的回调通过EventBus 通知
 */
public class MyAudioManager {
    protected static final String TAG = "MyAudioManager";

    private MediaPlayer mMediaPlayer;
    private String mVoicePath;

    private Timer mTimer = new Timer();
    private PlayingTimerTask mPlayingTimerTask;

    private Context mContext;

    private static final int MSG_UPDATE_PLAYING = 0;
    private static final int MSG_PLAY_VOICE = 1;
    private static final int MSG_OWNLOAD_AUDIO_FAIL = 3;
    private static final int MSG_CREATE_FILE_FAIL = 4;

    public MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * @author Kevin Lin
     * @version 1.0
     * @Description: 更新播放进度
     * @date 2014-1-14
     */
    private class PlayingTimerTask extends TimerTask {
        public void run() {
            if (instance == null || mMediaPlayer == null) {
                LogUtil.e(" instance == null || mMediaPlayer == null ");
                return;
            }
            try {
                LogUtil.e("mMedia player  " + mMediaPlayer.isPlaying());
                if (mMediaPlayer.isPlaying()) {
                    mHandler.sendEmptyMessage(MSG_UPDATE_PLAYING);
                }
            } catch (IllegalStateException e) {
                LogUtil.e(e.toString());
            }

        }
    }

    private static MyAudioManager instance;

//    public static MyAudioManager getIntance(SeaPlayingListener l, Context context) {
//        if (instance == null) {
//            instance = new MyAudioManager(context);
//        }
//        return instance;
//    }

    public static MyAudioManager getIntance(Context context) {
        if (instance == null) {
            instance = new MyAudioManager(context);
        }
        return instance;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_PLAYING:
                    if (instance == null || mMediaPlayer == null) {
                        LogUtil.e(" instance == null || mMediaPlayer == null ");
                        return;
                    }
                    int duration = mMediaPlayer.getDuration();
                    LogUtil.e("duration" + duration);
                    if (duration > 0) {
                        AudioEvent audioEvent = new AudioEvent();
                        audioEvent.setType(AudioEvent.PLAY_UPDATE);
                        audioEvent.setCurrentDuraion(mMediaPlayer.getCurrentPosition());
                        audioEvent.setTotalDuration(mMediaPlayer.getDuration());
                        EventBus.getDefault().post(audioEvent);
                    }
                    break;
                case MSG_PLAY_VOICE:
                    String url = (String) msg.obj;
                    LogUtil.e("handler url is " + url);
                    if (url.equals(mVoicePath)) {
                        String filePath = Constant.AUDIO_CACHE_PATH + "/" + getFileName(url);
                        startPlayingVoiceByCache(filePath);
                    }
                    break;
                case MSG_OWNLOAD_AUDIO_FAIL:
                    ToastUtil.showToastLong(mContext, "语音下载失败");
//                    if (mPlayingListener != null) {
//                        mPlayingListener.onStopPlaying();
//                    }
                    EventBus.getDefault().post(new AudioEvent(AudioEvent.PLAY_STOP));
                    break;
                case MSG_CREATE_FILE_FAIL:
                    ToastUtil.showToastLong(mContext, "缓存创建失败");
                    break;
                default:
                    break;
            }

        }

    };


    private MyAudioManager(Context context) {
        mMediaPlayer = new MediaPlayer();
        mContext = context;
    }

//    private MyAudioManager(SeaPlayingListener l, Context context) {
//        mMediaPlayer = new MediaPlayer();
//        mContext = context;
//    }


    /**
     * @param l
     * @Description: 传入播放监听回调
     * @author Kevin Lin
     * @date 2014-1-14
     */
//    public void setPlayingListener(SeaPlayingListener l) {
//        stopPlaying();
//        stopVoicePlayingTimer();

//        this.mPlayingListener = l;
//        LogUtil.e(" playing listener is " + mPlayingListener);
//    }

//    public SeaPlayingListener getmPlayingListener() {
//        return mPlayingListener;
//    }

    /**
     * @param l
     * @Description: 传入录音监听回调
     * @author Kevin Lin
     * @date 2014-1-14
     */

    /**
     * @return
     * @Description: 获取音频播放MediaPlayer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * @param path
     * @Description: 设置音频播放地址
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void setPlayingVoicePath(String path) {
        mVoicePath = path;
    }


    /**
     * @return
     * @Description: 获取音频播放地址
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public String getPlayingVoicePath() {
        return mVoicePath;
    }

    /**
     * @return
     * @Description: 是否正在播放
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public boolean startPlayingVoice(final String voiceUrl) {
        mVoicePath = voiceUrl;
        /**
         * here need to add new method to download message
         */
        downLoadCache(voiceUrl);
        return true;
    }

    /**
     * 暂停后,重新播放
     */
    public void resumePlayingAudio(String url) {
        LogUtil.e("resume playing audio  " + mMediaPlayer);
        if (mMediaPlayer != null) {
            int length = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.seekTo(length);
            mMediaPlayer.start();
        }
    }

    /**
     * @param voiceUrl 音频地址
     * @return
     * @Description: 播放音频
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public boolean startPlayingVoiceByCache(final String voiceUrl) {
        LogUtil.d("start play " + voiceUrl);
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                LogUtil.e("mMediaPlayer != null && mMediaPlayer.isPlaying()");
                stopPlaying();
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
//                    if (mPlayingListener != null) {
//                        mPlayingListener.onPlayingComplete(mp);
//                    }
                    EventBus.getDefault().post(new AudioEvent(AudioEvent.PLAY_COMPLETE));
                    if (mMediaPlayer != null) {
                        mMediaPlayer.stop();
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    stopVoicePlayingTimer();
                }
            });
            mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                    if (mPlayingListener != null) {
//                        mPlayingListener.onLoadingUpdate(mp);
//                    }
//                    EventBus.getDefault().post(new AudioEvent(AudioEvent.PLAY_COMPLETE));

                }
            });
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    if (mPlayingListener != null) {
//                        mPlayingListener.onLoadingError(mp);
//                    }
                    EventBus.getDefault().post(new AudioEvent(AudioEvent.PLAY_ERROR));

                    stopVoicePlayingTimer();
                    return false;
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    LogUtil.e("prepared .......");
                    mp.start();
//                    LogUtil.e("" + mPlayingListener);
//                    if (mPlayingListener != null) {
//                        mPlayingListener.onLoadingComplete(mp);
//                    }
                    AudioEvent audioEvent = new AudioEvent(AudioEvent.LOAD_COMPLETE);
                    audioEvent.setTotalDuration(mp.getDuration());
                    EventBus.getDefault().post(audioEvent);

                    startVoicePlayingTimer();
                }
            });
            mMediaPlayer.setDataSource(voiceUrl);
            mMediaPlayer.prepareAsync();

        } catch (Exception e) {
            e.printStackTrace();
//            if (mPlayingListener != null) {
//                mPlayingListener.onPlayingFailed(e);
//            }
            EventBus.getDefault().post(new AudioEvent(AudioEvent.PLAY_FAIL));

            return false;
        }
        return true;
    }


    /**
     * @Description: 终止播放视频
     * @author Kevin Lin
     * @date 2014-1-14
     */
    public void stopPlaying() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying() || mMediaPlayer.isLooping()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
        stopVoicePlayingTimer();

    }

    public void pausePlaying() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    /**
     * @Description: 开启播放进度更新Timer
     * @author Kevin Lin
     * @date 2014-1-14
     */
    private void startVoicePlayingTimer() {
        LogUtil.e("start voice playing timer");
        if (mPlayingTimerTask != null) {
            mPlayingTimerTask.cancel();
        }
        mPlayingTimerTask = new PlayingTimerTask();
        mTimer.schedule(mPlayingTimerTask, 0, 500);
    }

    private void stopVoicePlayingTimer() {
        if (mPlayingTimerTask != null) {
            mPlayingTimerTask.cancel();
            mPlayingTimerTask = null;
        }
    }

    private ExecutorService pool = Executors.newSingleThreadExecutor();
    private Map<String, CacheThread> threadMap = new LinkedHashMap<>();

    public class CacheThread extends Thread {
        private String url;

        public CacheThread(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            FileOutputStream fos = null;
            try {
                URL urlPath = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlPath.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    LogUtil.e("response code is " + httpURLConnection.getResponseCode() + "不允许下载，请检查服务端配置接口。");
                    mHandler.sendEmptyMessage(MSG_CREATE_FILE_FAIL);
                    return;
                }
                if (inputStream == null) {
                    Log.e("net error", "net error");
                    return;
                }
                long total = httpURLConnection.getContentLength();
                String fileName = getFileName(url);
                File savePath = new File(Constant.AUDIO_CACHE_PATH);
                if (!savePath.exists())
                    savePath.mkdirs();
                File apkFile = new File(savePath, fileName);
                if (apkFile.exists() && apkFile.length() == total) {
                    //file exist and file size == total
                    LogUtil.e("file exist and the size is same.");

                } else {
                    if (apkFile.exists()) {
                        apkFile.deleteOnExit();
                    }
                    apkFile.getParentFile().mkdirs();
                    boolean createFileResult = apkFile.createNewFile();
                    if (!createFileResult) {
                        mHandler.sendEmptyMessage(MSG_CREATE_FILE_FAIL);
                        return;
                    }
                    fos = new FileOutputStream(apkFile);
                    byte[] buf = new byte[2048];
                    int count = 0;
                    int length = -1;
                    LogUtil.d("total :" + total);
                    while ((length = inputStream.read(buf)) != -1) {
                        fos.write(buf, 0, length);
                        count += length;
                        if (downloadVoiceListener != null) {
                            int progress = (int) (count * 100 / total);
                            downloadVoiceListener.progress(progress);
                        }
                    }
                    fos.close();
                }
                threadMap.remove(url);
                Message message = new Message();
                message.what = MSG_PLAY_VOICE;
                message.obj = url;
                mHandler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                mHandler.sendEmptyMessage(MSG_OWNLOAD_AUDIO_FAIL);
                threadMap.remove(url);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            //开启map中的等待队列.
            startThread();
        }
    }

    public void downLoadCache(String url) {
        CacheThread thread = threadMap.get(url);
        if (thread != null) {
            return;
        }
        thread = new CacheThread(url);
        threadMap.put(url, thread);
        startThread();
    }

    private void startThread() {
        if (!threadMap.isEmpty()) {
            CacheThread thread = (CacheThread) ((Map.Entry) threadMap.entrySet().iterator().next()).getValue();
            pool.execute(thread);
        }
    }

    private DownloadVoiceListener downloadVoiceListener;

    public interface DownloadVoiceListener {
        void progress(int progress);
    }

    public void setDownloadVoiceListener(DownloadVoiceListener downloadVoiceListener) {
        this.downloadVoiceListener = downloadVoiceListener;
    }

    public static String getFileName(String url) {
        String fileName = new StringBuffer().append(MD5Util.getStringMD5(url)).append(".o").toString();
        return fileName;
    }
}
