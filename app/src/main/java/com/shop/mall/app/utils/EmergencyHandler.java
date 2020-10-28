package com.shop.mall.app.utils;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 在程序崩溃时记录崩溃情况
 *
 */
public class EmergencyHandler implements Thread.UncaughtExceptionHandler {

    private static final EmergencyHandler mInstance = new EmergencyHandler();
    private static Thread.UncaughtExceptionHandler mHandler;

    private static String PATH; // 日志记录的路径

    private EmergencyHandler() {
    }

    /**
     * 初始化
     */
    public static void init(String path) {
        PATH = path;
        if (mHandler == null) {
            mHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(mInstance);
        }
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        processException(ex);
        mHandler.uncaughtException(thread, ex);
    }

    /**
     * 捕捉崩溃信息
     */
    private void processException(final Throwable th) {
        try {
            // 获得崩溃信息
            Writer result = new StringWriter();
            PrintWriter printWriter = new PrintWriter(result);
            th.printStackTrace(printWriter);
            String stacktrace = result.toString();
            printWriter.close();
            // 输出崩溃信息
            long timestamp = System.currentTimeMillis();
            String filename = (PATH + "/android_crash_" + timestamp + ".txt");
            File file = new File(filename);
            saveString(stacktrace, file);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void saveString(String stacktrace, File file) {
        FileWriter fw;
        BufferedWriter out = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fw = new FileWriter(file);
            out = new BufferedWriter(fw);
            out.write(stacktrace, 0, stacktrace.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
