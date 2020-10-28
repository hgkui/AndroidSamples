package com.shop.mall.app

import android.os.Environment
import com.jess.arms.base.BaseApplication
import com.lxj.xpopup.XPopup
import com.shop.mall.BuildConfig
import com.shop.mall.R
import com.shop.mall.app.helper.ObjectBox
import com.shop.mall.app.utils.EmergencyHandler
import io.objectbox.android.AndroidObjectBrowser
import me.jessyan.autosize.AutoSizeConfig
import java.lang.reflect.Field
import java.lang.reflect.Method

class MyApplication() : BaseApplication() {
    companion object {
        /**
         * Application实例
         */
        var instance: MyApplication? = null

    }

    override fun onCreate() {
        super.onCreate()
        EmergencyHandler.init(Environment.getExternalStorageDirectory().path + "/01error")
        initAutoSize()
        initPopup()
        setMaxTimeOut()
    }

    /**
     * 初始化AutoSize
     */
    private fun initAutoSize() {
        //禁止app字体大小随系统字体大小而变化
        AutoSizeConfig.getInstance().setExcludeFontScale(true)
    }

    /**
     * 初始化 xPopup
     */
    private fun initPopup() {
        XPopup.setPrimaryColor(resources.getColor(R.color.colorPrimaryDark))
    }

    /**
     * 适配Android 28 对使用非 SDK API 弹窗处理
     */
    private fun closeAndroidPDialog() {
        try {
            val aClass = Class.forName("android.content.pm.PackageParser\$Package")
            val declaredConstructor = aClass.getDeclaredConstructor(String::class.java)
            declaredConstructor.setAccessible(true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        try {
            val cls = Class.forName("android.app.ActivityThread")
            val declaredMethod: Method = cls.getDeclaredMethod("currentActivityThread")
            declaredMethod.setAccessible(true)
            val activityThread: Any = declaredMethod.invoke(null)
            val mHiddenApiWarningShown: Field = cls.getDeclaredField("mHiddenApiWarningShown")
            mHiddenApiWarningShown.setAccessible(true)
            mHiddenApiWarningShown.setBoolean(activityThread, true)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 解决
     * TimeoutException android.content.res.AssetManager.finalize() timed out after 120 s
     */
    private fun setMaxTimeOut() {
        try {
            val clazz = Class.forName("java.lang.Daemons")
            val field = clazz.getDeclaredField("MAX_FINALIZE_NANOS")
            field.isAccessible = true
            field.set(null, Int.MAX_VALUE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val clazz = Class.forName("java.lang.Daemons\$FinalizerWatchdogDaemon")

            val method = clazz.superclass!!.getDeclaredMethod("stop")
            method.isAccessible = true

            val field = clazz.getDeclaredField("INSTANCE")
            field.isAccessible = true

            method.invoke(field.get(null))

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}