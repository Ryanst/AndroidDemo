package com.ryanst.app.widget;

import java.io.File;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShowSizeUtil {

    public String text;

    public String showsize() {

        //�жϴ洢���Ƿ����
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//		ȡ��SD card�ļ�·��һ����/sdcard*/
            File path = Environment.getExternalStorageDirectory();
            //statFs ���ļ�ϵͳ�ռ�ʹ�����
            StatFs statFs = new StatFs(path.getPath());
            //Block ��size
            long blockSize = statFs.getBlockSize();
            //��Block����
            long totalBlocks = statFs.getAvailableBlocks();
            //��ʹ�õ�BLOCK����
            long availableBlocks = statFs.getAvailableBlocks();
            String[] total = fileSize(totalBlocks * blockSize);
            String[] available = fileSize(availableBlocks * blockSize);
            text = "�ܹ�" + total[0] + total[1] + "\t";
            text += "����" + available[0] + available[1];

        } else if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
            text = "SD CARD ��ɾ��";
        }
        return text;
    }

    private String[] fileSize(long size) {
        String str = "";
        if (size >= 1024) {
            str = "KB";
            size /= 1024;
            if (size >= 1024) {
                str = "MB";
                size /= 1024;
            }
        }
        DecimalFormat formatter = new DecimalFormat();
        //ÿ3�������ã�����
        formatter.setGroupingSize(3);
        String result[] = new String[2];
        result[0] = formatter.format(size);
        result[1] = str;

        return result;
    }
}
