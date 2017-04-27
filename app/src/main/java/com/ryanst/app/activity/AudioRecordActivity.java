package com.ryanst.app.activity;


import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ryanst.app.R;
import com.ryanst.app.widget.ShowSizeUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioRecordActivity extends Activity {
    private ImageButton btnStartRecord;
    private ImageButton btnEndRecord;
    private ImageButton btnPlay;
    private ImageButton btnDelete;
    private Button myButton;
    private ListView myListView1;
    private File currentAudioFile;
    /**
     * 录音保存路径
     **/
    private File myRecAudioDir;
    private File myPlayFile;
    private MediaRecorder mediaRecorder;
    /**
     * 存放音频文件列表
     **/
    private ArrayList<String> recordFiles;
    private ArrayAdapter<String> adapter;
    private TextView myTextView1;
    /**
     * 文件存在
     **/
    private boolean sdcardExit;
    /**
     * 是否停止录音
     **/
    private boolean isStopRecord;
    /**
     * 按钮背景图片的标志位
     **/
    private boolean sigle = false;
    private String length1 = null;

    private final String SUFFIX = ".amr";


    /**
     * 暂停按钮
     **/
    Button btnPause;


    /**
     * 记录需要合成的几段amr语音文件
     **/
    private ArrayList<String> mRecordList;


    int second = 0;

    int minute = 0;

    /**
     * 计时器
     **/
    Timer timer;


    /**
     * 是否暂停标志位
     **/
    private boolean hasPause;

    /**
     * 在暂停状态中
     **/
    private boolean inPause;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_audio);

        //暂停标志位 为false
        hasPause = false;
        //暂停状态标志位
        inPause = false;

        //初始化list
        mRecordList = new ArrayList<String>();

        //四个按钮
        btnStartRecord = (ImageButton) findViewById(R.id.iv_stop);
        btnEndRecord = (ImageButton) findViewById(R.id.ImageButton02);
        btnPlay = (ImageButton) findViewById(R.id.ImageButton03);
        btnDelete = (ImageButton) findViewById(R.id.ImageButton04);
        myButton = (Button) findViewById(R.id.btn_see_storage);
        btnPause = (Button) findViewById(R.id.btn_pause);
        myListView1 = (ListView) findViewById(R.id.ListView01);
        myTextView1 = (TextView) findViewById(R.id.TextView01);
        btnEndRecord.setEnabled(false);
        btnPlay.setEnabled(false);
        btnDelete.setEnabled(false);

        myPlayFile = null;

        // 判断sd Card是否插入
        sdcardExit = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        // 取得sd card路径作为录音文件的位置
        if (sdcardExit) {
            String pathStr = Environment.getExternalStorageDirectory().getPath() + "/YYT";
            myRecAudioDir = new File(pathStr);
            if (!myRecAudioDir.exists()) {
                myRecAudioDir.mkdirs();
                Log.v("录音", "创建录音文件！" + myRecAudioDir.exists());
            }
//			Environment.getExternalStorageDirectory().getPath() + "/" + PREFIX + "/";
        }
        // 取得sd card 目录里的.arm文件
        getRecordFiles();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, recordFiles);
        // 将ArrayAdater添加ListView对象中
        myListView1.setAdapter(adapter);
        // 录音

        btnStartRecord.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                second = 0;
                minute = 0;

                mRecordList.clear();
//			Calendar c=Calendar.getInstance();
//			int	mMinute1=c.get(Calendar.MINUTE);

                sigle = true;
                // TODO Auto-generated method stub

                startRecord();

                if (sigle = false) {
                    btnStartRecord.setBackgroundResource(R.drawable.record_hover1);
                } else {
                    btnStartRecord.setBackgroundResource(R.drawable.record_dis1);
                    btnEndRecord.setBackgroundResource(R.drawable.stop_hover2);
                    btnPlay.setBackgroundResource(R.drawable.play_hover1);
                    btnDelete.setBackgroundResource(R.drawable.delete_hover);
                }


            }

        });
        // 停止
        btnEndRecord.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigle = true;
                timer.cancel();

                //这里写暂停处理的 文件！加上list里面 语音合成起来
                if (hasPause) {
                    //在暂停状态按下结束键,处理list就可以了
                    if (inPause) {
                        getInputCollection(mRecordList, false);
                    }
                    //在正在录音时，处理list里面的和正在录音的语音
                    else {
                        mRecordList.add(currentAudioFile.getPath());
                        recordStop();
                        getInputCollection(mRecordList, true);
                    }
                    //还原标志位
                    hasPause = false;
                    inPause = false;
                    btnPause.setText("暂停录音");

                    //	adapter.add(currentAudioFile.getName());
                }

                //若录音没有经过任何暂停
                else {
                    if (currentAudioFile != null) {
                        // 停止录音
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                        // 将录音频文件给Adapter
                        adapter.add(currentAudioFile.getName());
                        DecimalFormat df = new DecimalFormat("#.000");
                        if (currentAudioFile.length() <= 1024 * 1024) {
                            //length1 = (currentAudioFile.length() / 1024.0)+"";

                            length1 = df.format(currentAudioFile.length() / 1024.0) + "K";
                        } else {
                            //length1 = (currentAudioFile.length() / 1024.0 / 1024)+"";
                            //DecimalFormat df = new DecimalFormat("#.000");
                            length1 = df.format(currentAudioFile.length() / 1024.0 / 1024) + "M";
                        }
                        System.out.println(length1);

                        myTextView1.setText("停  止" + currentAudioFile.getName()
                                + "文件大小为：" + length1);
                        btnEndRecord.setEnabled(false);

                    }

                }

                if (sigle = false) {
                    btnEndRecord.setBackgroundResource(R.drawable.stop_hover2);
                } else {
                    btnStartRecord.setBackgroundResource(R.drawable.record_hover1);
                    btnEndRecord.setBackgroundResource(R.drawable.stop1);
                    btnPlay.setBackgroundResource(R.drawable.play_hover1);
                    btnDelete.setBackgroundResource(R.drawable.delete_hover);
                }

                //停止录音了
                isStopRecord = true;
            }

        });

        // 播放
        btnPlay.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                sigle = true;
                // TODO Auto-generated method stub
                if (myPlayFile != null && myPlayFile.exists()) {
                    // 打开播放程序
                    startPlay(myPlayFile);
                } else {
                    Toast.makeText(AudioRecordActivity.this, "你选的是一个空文件", Toast.LENGTH_LONG)
                            .show();
                    Log.d("没有选择文件", "没有选择文件");
                }
                if (sigle = false) {
                    btnPlay.setBackgroundResource(R.drawable.play_hover1);
                } else {
                    btnStartRecord.setBackgroundResource(R.drawable.record_hover1);
                    btnEndRecord.setBackgroundResource(R.drawable.stop_hover2);
                    btnPlay.setBackgroundResource(R.drawable.play1);
                    btnDelete.setBackgroundResource(R.drawable.delete_hover);
                }
            }

        });

        // 删除
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sigle = true;
                // TODO Auto-generated method stub

                if (myPlayFile != null) {
                    // 先将Adapter删除文件名
                    adapter.remove(myPlayFile.getName());
                    // 删除文件
                    if (myPlayFile.exists())
                        myPlayFile.delete();
                    myTextView1.setText("完成删除！");

                }
                if (sigle = false) {
                    btnDelete.setBackgroundResource(R.drawable.delete_hover);
                } else {
                    btnStartRecord.setBackgroundResource(R.drawable.record_hover1);
                    btnEndRecord.setBackgroundResource(R.drawable.stop_hover2);
                    btnPlay.setBackgroundResource(R.drawable.play_hover1);
                    btnDelete.setBackgroundResource(R.drawable.delete_dis);
                }
            }
        });

        /**
         * 暂停按钮,记录之前保存的语音文件
         */
        btnPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hasPause = true;

                //已经暂停过了，再次点击按钮 开始录音，录音状态在录音中
                if (inPause) {
                    btnPause.setText("暂停录音");
                    startRecord();
                    inPause = false;
                }
                //正在录音，点击暂停,现在录音状态为暂停
                else {
                    //当前正在录音的文件名，全程
                    mRecordList.add(currentAudioFile.getPath());
                    inPause = true;
                    recordStop();
                    //startRecord();
                    btnPause.setText("继续录音");

                    //计时停止
                    timer.cancel();
                }
            }
        });


        myListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg, View arg1,
                                    int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 当有单点击文件名时将删除按钮及播放按钮Enable
                btnPlay.setEnabled(true);
                btnDelete.setEnabled(true);
                myPlayFile = new File(myRecAudioDir.getAbsolutePath()
                        + File.separator
                        + ((TextView) arg1).getText().toString());

                DecimalFormat df = new DecimalFormat("#.000");
                if (myPlayFile.length() <= 1024 * 1024) {
                    length1 = df.format(myPlayFile.length() / 1024.0) + "K";
                } else {
                    length1 = df.format(myPlayFile.length() / 1024.0 / 1024) + "M";
                }
                myTextView1.setText("你选的是"
                        + ((TextView) arg1).getText().toString()
                        + "文件大小为：" + length1);
                Toast.makeText(AudioRecordActivity.this, "你选的是" + (((TextView) arg1).getText()) + "文件大小为：" + length1,
                        Toast.LENGTH_LONG).show();

            }

        });

        myButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowSizeUtil show = new ShowSizeUtil();
                String text = show.showsize();
                Toast.makeText(AudioRecordActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }


    protected void recordStop() {
        if (mediaRecorder != null && !isStopRecord) {
            // 停止录音
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }

        timer.cancel();
    }


    /**
     * activity的生命周期，stop时关闭录音资源
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        if (mediaRecorder != null && !isStopRecord) {
            // 停止录音
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        super.onStop();
    }


    /**
     * 获取目录下的所有音频文件
     */
    private void getRecordFiles() {
        // TODO Auto-generated method stub
        recordFiles = new ArrayList<String>();
        if (sdcardExit) {
            File files[] = myRecAudioDir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().indexOf(".") >= 0) { // 只取.amr 文件
                        String fileS = files[i].getName().substring(
                                files[i].getName().indexOf("."));
                        if (fileS.toLowerCase().equals(".mp3")
                                || fileS.toLowerCase().equals(".amr")
                                || fileS.toLowerCase().equals(".mp4"))
                            recordFiles.add(files[i].getName());

                    }
                }
            }
        }

    }

    // 打开录音播放程序
    private void startPlay(File f) {
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(android.content.Intent.ACTION_VIEW);
//        String type = getMIMEType(f);
//        intent.setDataAndType(Uri.fromFile(f), type);
//        startActivity(intent);

        Uri uri = Uri.fromFile(f);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, uri);
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    private String getMIMEType(File f) {

        String end = f.getName().substring(f.getName().lastIndexOf(".") + 1,
                f.getName().length()).toLowerCase();
        String type = "";
        if (end.equals("mp3") || end.equals("aac") || end.equals("amr")
                || end.equals("mpeg") || end.equals("mp4")) {
            type = "audio";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg")) {
            type = "image";
        } else {
            type = "*";
        }
        type += "/";
        return type;
    }

    private void startRecord() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                second++;
                if (second >= 60) {
                    second = 0;
                    minute++;
                }
                handler.sendEmptyMessage(1);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 1000, 1000);

        try {
            if (!sdcardExit) {
                Toast.makeText(AudioRecordActivity.this, "请插入SD card",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String mMinute1 = getTime();
            Toast.makeText(AudioRecordActivity.this, "当前时间是:" + mMinute1, Toast.LENGTH_LONG).show();
            // 创建音频文件
//			currentAudioFile = File.createTempFile(mMinute1, ".amr",
//					myRecAudioDir);

            currentAudioFile = new File(myRecAudioDir, mMinute1 + SUFFIX);
            mediaRecorder = new MediaRecorder();
            // 设置录音为麦克风
            mediaRecorder
                    .setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder
                    .setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            mediaRecorder
                    .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            //录音文件保存这里
            mediaRecorder.setOutputFile(currentAudioFile
                    .getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();

//			mediaRecorder.getMaxAmplitude();
//			mediaRecorder.getAudioSourceMax();
            mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    // TODO Auto-generated method stub
                    int a = mr.getMaxAmplitude();
                    Toast.makeText(AudioRecordActivity.this, a, Toast.LENGTH_LONG).show();
                }
            });

            myTextView1.setText("录音中......");
            btnEndRecord.setEnabled(true);
            btnPlay.setEnabled(false);
            btnDelete.setEnabled(false);
            isStopRecord = false;
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH：mm：ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);
        System.out.println("当前时间");
        return time;
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            myTextView1.setText("录音时间：" + minute + ":" + second);
        }

    };

    /**
     * @param isAddLastRecord 是否需要添加list之外的最新录音，一起合并
     * @return 将合并的流用字符保存
     */
    public void getInputCollection(List recordList, boolean isAddLastRecord) {
        Toast.makeText(AudioRecordActivity.this, "当前时间是:" + getTime(), Toast.LENGTH_LONG).show();

        // 创建音频文件,合并的文件放这里
        File file1 = new File(myRecAudioDir, getTime() + SUFFIX);
        FileOutputStream fileOutputStream = null;

        if (!file1.exists()) {
            try {
                file1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream = new FileOutputStream(file1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //list里面为暂停录音 所产生的 几段录音文件的名字，中间几段文件的减去前面的6个字节头文件


        for (int i = 0; i < recordList.size(); i++) {
            File file = new File((String) recordList.get(i));
            Log.d("list的长度", recordList.size() + "");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;

                //头文件
                if (i == 0) {
                    while (fileInputStream.read(myByte) != -1) {
                        fileOutputStream.write(myByte, 0, length);
                    }
                }

                //之后的文件，去掉头文件就可以了
                else {
                    while (fileInputStream.read(myByte) != -1) {

                        fileOutputStream.write(myByte, 6, length - 6);
                    }
                }

                fileOutputStream.flush();
                fileInputStream.close();
                System.out.println("合成文件长度：" + file1.length());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        //结束后关闭流
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //加上当前正在录音的这一段
//			if(isAddLastRecord){
//
//
//				//刚刚录音的
//				try {
//					FileInputStream fileInputStream=new FileInputStream(currentAudioFile);
//					byte  []myByte=new byte[fileInputStream.available()];
//					System.out.println(fileInputStream.available()+"");
//					while(fileInputStream.read(myByte)!=-1){
//						//outputStream.
//						fileOutputStream.write(myByte, 6, (fileInputStream.available()-6));
//					}
//
//					fileOutputStream.flush();
//					fileInputStream.close();
//					fileOutputStream.close();
//					System.out.println("合成文件长度："+file1.length());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}


        //合成一个文件后，删除之前暂停录音所保存的零碎合成文件
        deleteListRecord(isAddLastRecord);

        adapter.add(file1.getName());

    }

    private void deleteListRecord(boolean isAddLastRecord) {
        for (int i = 0; i < mRecordList.size(); i++) {
            File file = new File((String) mRecordList.get(i));
            if (file.exists()) {
                file.delete();
            }
        }
        //正在暂停后，继续录音的这一段音频文件
        if (isAddLastRecord) {
            currentAudioFile.delete();
        }
    }
}