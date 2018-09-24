package com.adshow.player.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adshow.player.activitys.fullscreen.ADMaterial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class FileUtils {

    private static final String TAG = "ADPlayerBackendService";

    public static String getFileName(String url) {
        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(url);//条件匹配
        String fileName = "";
        while (mc.find()) {
            fileName = mc.group();//截取文件名后缀名
            Log.e("fileName:", fileName);
        }
        return fileName;
    }

    public static void unzip(String FilePath, String UnzipPath) {
        File dir = new File(UnzipPath);
        if (!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(FilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                File newFile = new File(UnzipPath + File.separator + fileName.replace("\\","/"));
                Log.e(TAG, "Unzipping to " + newFile.getAbsolutePath());
                File directory = new File(newFile.getParent());
                if(!directory.exists()){
                    directory.mkdirs();
                }
                if (!fileName.contains(".")) {
                    newFile.mkdirs();
                } else {
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zis.closeEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.toString());
        }
    }



    /**
     * Helper function to load file from assets
     */
    public static List<ADMaterial> readAdvertisingConfig(String filePath) throws JSONException {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = new FileInputStream(filePath); // "/sdcard/Advertising/json/" + fileName
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line;
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null) isr.close();
                if (fIn != null) fIn.close();
                if (input != null) input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }

        List<ADMaterial> jsonListObject = new Gson().fromJson(returnString.toString(), new TypeToken<List<ADMaterial>>(){}.getType());
        return jsonListObject;
    }

    public static File getParentFile(@NonNull Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }

}
