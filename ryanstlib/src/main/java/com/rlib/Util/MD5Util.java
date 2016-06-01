package com.rlib.util;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by kevin on 16/3/14.
 */
public class MD5Util {
    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (digest != null) {
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } else {
            return null;
        }
    }

    /**
     * 获取单个字符串的MD5值
     *
     * @param String
     * @return
     */
    public static String getStringMD5(String text) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(text.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().toLowerCase();
    }

    /**
     * @param file
     * @param md5
     * @return
     * @Description:检查文件的MD5与传入的MD5值是否一致
     * @author DingLei
     * @date 2014-4-23
     */
    public static boolean checkFileMD5(File file, String md5) {
        String targetMD5 = getFileMD5(file);
        if (!TextUtils.isEmpty(targetMD5) && !TextUtils.isEmpty(md5) && targetMD5.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }
}
