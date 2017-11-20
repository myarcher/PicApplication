package com.select.untils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Administrator on 2017/3/23.
 */

public class FileHelper {
    private Context context;
    /** SD卡是否存在**/
    private boolean hasSD = false;
    /** SD卡的路径**/
    private String SDPATH;
    /** 当前程序包的路径**/
    private String FILESPATH;
    public FileHelper(Context context) {
        this.context = context;
        hasSD = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILESPATH = this.context.getFilesDir().getPath();
    }
    /**
     * 在SD卡上创建文件
     */
    public File createSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + "//" + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
    /**
     * 删除SD卡上的文件
     *
     * @param fileName
     */
    public boolean deleteSDFile(String fileName) {
        File file = new File(SDPATH + "//" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }
    /**
     * 写入内容到SD卡中的txt文本中
     * str为内容
     */
    public void writeSDFile(String fileName,String pnum,String svalue,String endvalue,String evalues,String eva)
    {
        File f = new File(SDPATH + "//" + fileName);//如果文件存在，则追加内容；如果文件不存在，则创建文件
        if (f.exists()) {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
                //out.write(str); 
                out.write(" " + pnum + "                " + svalue + "                "  + endvalue+ "                " + evalues+ "             " + eva+"\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if(out != null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true)));
                out.write("***************************************"+"\n");

                out.write("" + "图片编号" + "           " + "原始亮度" + "          "  + "改变亮度"+ "          " + "外界亮度"+ "          " + "满意度"  +"\n");
                out.write(" " + pnum + "                " + svalue + "                "  + endvalue+ "                " + evalues+ "             " + eva+"\n");  
            } catch (Exception e) {
            } finally {
                try {
                    if(out != null){
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public  String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 读取SD卡中文本文件
     *
     * @param fileName
     * @return
     */
    public String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            int c;
            while ((c = fis.read()) != -1) {
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public String getFILESPATH() {
        return FILESPATH;
    }
    public String getSDPATH() {
        return SDPATH;
    }
    public boolean hasSD() {
        return hasSD;
    }
}
