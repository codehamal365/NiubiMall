package com.gl.utlils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 生成二维码
 */
class QrCodeUtil{
    // 二维码的宽度
    static final int WIDTH = 300;
    // 二维码的高度
    static final int HEIGHT = 300;
    // 二维码的格式
    static final String FORMAT = "png";
    // 二维码的内容
    static final String TEXT = "Hello！二维码！！！";

    public void generate() {
       /*
           定义二维码的参数
        */
        HashMap hashMap = new HashMap();
        // 设置二维码字符编码
        hashMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 设置二维码纠错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置二维码边距
        hashMap.put(EncodeHintType.MARGIN, 2);

        try {
            // 开始生成二维码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(TEXT, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hashMap);
            // 导出到指定目录
            MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, new File("D://erweima.png").toPath());
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
