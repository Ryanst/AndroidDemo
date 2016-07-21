package com.ryanst.app.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ryanst.app.R;
import com.ryanst.app.core.BaseFragment;
import com.ryanst.app.databinding.FragmentPlayVoiceBinding;
import com.ryanst.app.util.LogUtil;
import com.ryanst.app.util.ToastUtil;
import com.ryanst.app.widget.AudioEvent;
import com.ryanst.app.widget.MyAudioManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevin on 16/7/19.
 */
public class VoicePlayFragment extends BaseFragment {
    private FragmentPlayVoiceBinding playVoiceBinding;

    public VoicePlayFragment() {
    }

    public VoicePlayFragment(String voiceUrl) {
        this.url = voiceUrl;
    }

    private final static int STATE_IDLE = 0;
    private final static int STATE_LOAD = 1;
    private final static int STATE_PLAY = 2;
    private final static int STATE_PAUSE = 3;
    private final static int STATE_REPLAY = 4;
    private final static int STATE_COMPLETE = 5;

    private final static int HANDLER_REFRESHBUTTON = 100;
    private final static int HANDLER_PROGRESS = 101;
    private final static int HANDLER_DOWNLOAD = 102;


    private MyAudioManager audioManager;
    private String url = "http://file.aibeike.com/english/bbe3f6bc-2e14-4b98-a446-acee4e3fcc47.mp3";

    private SeekBar seekBar;
    private ImageView playButton;
    private TextView textViewCostTime;
    private TextView textViewTotalTime;
    private int currentPlayState = 0;
    private int currentProgress = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_REFRESHBUTTON:
                    setPlayButton();
                    setSeekBarCanSeekAble();
                    break;
                case HANDLER_PROGRESS:
                    int position = msg.arg1;
                    int progress = msg.arg2;
                    seekBar.setProgress(progress);
                    setCostTime(position);
                    break;
                case HANDLER_DOWNLOAD:
                    progress = msg.arg1;
                    playVoiceBinding.seekBar.setProgress(progress);
                    break;
                default:
                    break;

            }
        }
    };

    public void setUrl(String url) {
        this.url = url;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        playVoiceBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_play_voice, container, false);
        initView();
        EventBus.getDefault().register(this);
        return playVoiceBinding.getRoot();
    }

    private void initView() {
        audioManager = MyAudioManager.getIntance(getContext());
        audioManager.setDownloadVoiceListener(new MyAudioManager.DownloadVoiceListener() {
            @Override
            public void progress(int progress) {
                Message msg = new Message();
                msg.what = HANDLER_DOWNLOAD;
                msg.arg1 = progress;
                handler.sendMessage(msg);
            }
        });
        seekBar = playVoiceBinding.seekBar;
        playVoiceBinding.seekBar.setMax(100);
        textViewCostTime = playVoiceBinding.costText;
        textViewTotalTime = playVoiceBinding.totalText;
        playButton = playVoiceBinding.play;
        playButton.setImageResource(R.drawable.media_play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(url)) {
                    ToastUtil.showToastLong(context, "语音url为空");
                    return;
                }
                switch (currentPlayState) {
                    case STATE_IDLE:
                        currentPlayState = STATE_PLAY;
                        break;
                    case STATE_LOAD:
                        currentPlayState = STATE_REPLAY;
                        break;
                    case STATE_PAUSE:
                        currentPlayState = STATE_REPLAY;
                        break;
                    case STATE_PLAY:
                        currentPlayState = STATE_PAUSE;
                        break;
                    case STATE_REPLAY:
                        currentPlayState = STATE_PAUSE;
                        break;
                    default:
                        break;
                }
                setPlayButton();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (audioManager.getMediaPlayer() != null) {
                    if (audioManager.getMediaPlayer().isPlaying()) {
                        int progress = seekBar.getProgress();
                        int msec = progress * audioManager.getMediaPlayer().getDuration() / 100;
                        LogUtil.e("seek to " + msec);
                        audioManager.getMediaPlayer().seekTo(msec);
                        audioManager.getMediaPlayer().start();
                    } else {
                        seekBar.setProgress(0);
                    }
                }
            }
        });
        setPlayButton();
        setSeekBarCanSeekAble();
        audioManager.setPlayingVoicePath(url);
        if (audioManager.getMediaPlayer() != null) {
            setTotalTime(audioManager.getMediaPlayer().getDuration());
        }
    }

    /**
     * 设置播放按钮的状态
     */
    private void setPlayButton() {
        switch (currentPlayState) {
            case STATE_IDLE:
                playButton.setImageResource(R.drawable.media_play);
                audioManager.stopPlaying();
                break;
            case STATE_PLAY:
                playButton.setImageResource(R.drawable.media_stop);
                audioManager.startPlayingVoice(url);
                break;
            case STATE_PAUSE:
                playButton.setImageResource(R.drawable.media_play);
                audioManager.pausePlaying();
                break;
            case STATE_LOAD:
                playButton.setImageResource(R.drawable.media_stop);
                audioManager.pausePlaying();
                break;
            case STATE_REPLAY:
                playButton.setImageResource(R.drawable.media_stop);
                audioManager.resumePlayingAudio(url);
                break;
            default:
                playButton.setImageResource(R.drawable.media_play);
                audioManager.stopPlaying();
                break;
        }
    }

    /**
     * 设置滑动块是否能够滑动
     */
    private void setSeekBarCanSeekAble() {
        switch (currentPlayState) {
            case STATE_IDLE:
            case STATE_LOAD:
            case STATE_PAUSE:
                seekBar.setHorizontalScrollBarEnabled(false);
                break;
            case STATE_PLAY:
                seekBar.setHorizontalScrollBarEnabled(true);
            default:
                break;
        }
    }


    private void setCostTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String string = sdf.format(date);
        textViewCostTime.setText(string);
    }

    private void setTotalTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String string = sdf.format(date);
        textViewTotalTime.setText(string);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Keep
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(AudioEvent event) {
        switch (event.getType()) {
            case AudioEvent.PLAY_COMPLETE:
                currentPlayState = STATE_IDLE;
                handler.sendEmptyMessage(HANDLER_REFRESHBUTTON);
                seekBar.setProgress(100);
                break;

            case AudioEvent.PLAY_FAIL:
                seekBar.setProgress(0);
                currentPlayState = STATE_IDLE;
                handler.sendEmptyMessage(HANDLER_REFRESHBUTTON);
                break;

            case AudioEvent.PLAY_STOP:
                seekBar.setProgress(0);
                currentPlayState = STATE_IDLE;
                handler.sendEmptyMessage(HANDLER_REFRESHBUTTON);
                break;

            case AudioEvent.PLAY_UPDATE:
                int progress = event.getCurrentDuraion() * 100 / event.getTotalDuration();
                int position = event.getCurrentDuraion();
                seekBar.setProgress(progress);
                setCostTime(position);
                setTotalTime(event.getTotalDuration());
                break;

            case AudioEvent.PLAY_ERROR:
                break;
            case AudioEvent.PLAY_START:
                break;
            case AudioEvent.LOAD_COMPLETE:
                setTotalTime(event.getTotalDuration());
                break;
            default:
                break;
        }

    }

}
