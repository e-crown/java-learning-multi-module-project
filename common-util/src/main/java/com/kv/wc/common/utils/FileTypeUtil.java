/**
 * 
 */
package com.kv.wc.common.utils;

import com.kv.wc.common.enums.FileType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 判断文件类型
 **/
public final class FileTypeUtil {

    /**
     * 是否是图片类型
     * 
     * @param bytes
     * @return true：图片 false：非图片
     */
    public static boolean checkPicType(byte[] bytes) {
        FileType fileType = getType(bytes);
        if (getPicFileType().stream().anyMatch(p -> p.equals(fileType))) {
            return true;
        }
        return false;
    }

    /**
     * 是否是图片类型
     * 
     * @param bytes
     * @return true：图片 false：非图片
     */
    public static boolean checkFileType(byte[] bytes) {
        FileType fileType = getType(bytes);
        if (getFileType().stream().anyMatch(p -> p.equals(fileType))) {
            return true;
        }
        return false;
    }

    /**
     * 图片类型
     * 
     * @return
     */
    private static List<FileType> getFileType() {
        return Arrays.asList(FileType.JPEG, FileType.PNG, FileType.SVG, FileType.PDF);
    }

    /**
     * wenj类型
     * 
     * @return
     */
    private static List<FileType> getPicFileType() {
        return Arrays.asList(FileType.JPEG, FileType.PNG, FileType.SVG);
    }

    /**
     * 二进制转化为16进制
     */
    private static String bytes2hex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                hex.append("0");
            }
            hex.append(temp.toLowerCase());
        }
        return hex.toString();
    }

    /**
     * 读取文件头
     */
    public static String getFileHeader(InputStream inputstream) throws IOException {
        byte[] b;
        try {
            b = new byte[28];
            inputstream.read(b, 0, 28);
            return bytes2hex(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputstream.close();
        }
        return null;

    }

    public static String getFileHead(byte[] bytes) {
        return bytes2hex(bytes);
    }

    /**
     * 判断文件类型
     */
    public static FileType getType(byte[] bytes) {
        String fileHead = bytes2hex(bytes);
        return getTypeFromFileHead(fileHead);
    }

    /**
     * 判断文件类型
     */
    public static FileType getType(InputStream inputstream) throws IOException {
        String fileHead = getFileHeader(inputstream);
        return getTypeFromFileHead(fileHead);
    }

    public static FileType getType(String filePath) throws IOException {
        InputStream inputstream = new FileInputStream(filePath);
        return getType(inputstream);
    }

    private static FileType getTypeFromFileHead(String fileHead) {
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }
}
