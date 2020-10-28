package com.shop.mall.app.base

import android.content.Intent
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.jess.arms.base.BaseActivity
import com.jess.arms.mvp.IPresenter
import com.jess.arms.mvp.IView
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.kaopiz.kprogresshud.KProgressHUD
import com.lxj.xpopup.XPopup
import com.shop.mall.R
import com.shop.mall.app.aop.SingleClick
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeCompat
import java.util.concurrent.TimeUnit

/**
 * @author fang.xc@outlook.com
 * @date 2019/06/03
 */
abstract class BaseArmActivity<P : IPresenter> : BaseActivity<P>(), IView, View.OnClickListener {
    /**
     * 仿ios加载框
     */
    var mProgressDialog: KProgressHUD? = null

    /**
     * 是否需要沉浸式状态栏
     */
    var needTransStatusBar = true

    var colorRes: Int = 0

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private val MIN_CLICK_DELAY_TIME = 1000L

    private var mDisposable: Disposable? = null

    lateinit var mXPopupBuilder: XPopup.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        //初始化Xpopup.builder
        mXPopupBuilder = XPopup.Builder(this)
        super.onCreate(savedInstanceState)
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        initTitleBar()
    }


    private fun initTitleBar() {
        val titleBar = findViewById<CommonTitleBar>(R.id.titlebar)
        if (titleBar != null) {
            if (titleBar.centerTextView != null) {
                titleBar.centerTextView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            }
        }

    }


    /**
     * 显示信息
     *
     * @param message 消息内容, 不能为 `null`
     */
    override fun showMessage(message: String) {
        checkNotNull(message)
        ToastUtils.showShort(message)
    }


    open fun showErrorMessage(msg: String) {
        mXPopupBuilder
            .asConfirm("错误", msg) {
                //confirm 确认操作
                doInConfirmError()
            }
            .show()
    }

    /**
     * 根据传入的msg显示对应的loading控件
     */
    open fun showLoading(msg: String?) {
        mDisposable?.dispose()
        if (mProgressDialog != null && !TextUtils.isEmpty(msg) && !mProgressDialog!!.isShowing) {
            AutoSize.autoConvertDensity(this, 375F, true)
            mProgressDialog!!
                .show()
        }
    }


    /**
     * 隐藏loading控件
     */
    override fun hideLoading() {
        hideLoading(500)
    }

    open fun hideLoading(mills: Long = 500) {
        if (mills <= 0) {
            if (mProgressDialog != null) {
                mProgressDialog!!.dismiss()
            }
            mDisposable?.dispose()
        } else {
            //延迟mills ms结束,防止用户看见loading框闪一下没了
            mDisposable =
                Single
                    .just("")
                    .delay(mills, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally {
                        mProgressDialog?.dismiss()
                    }
                    .compose(RxLifecycleUtils.bindToLifecycle(this as IView))
                    .subscribe()
        }

    }

    /**
     * 启动页面
     */
    override fun launchActivity(intent: Intent) {
        checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun onDestroy() {
        if (mProgressDialog != null) {
            this.mProgressDialog!!.dismiss()
            this.mProgressDialog = null
        }
        mDisposable?.dispose()
        super.onDestroy()
    }

    /**
     * 结束
     */
    override fun killMyself() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
        mDisposable?.dispose()
        finish()
    }

    /**
     * 是否需要沉浸式状态栏
     * @param need -> true 需要  反之 不需要
     */
    fun needTransStatusBar(need: Boolean, colorRes: Int?) {
        this.needTransStatusBar = need
        if (colorRes != null) {
            this.colorRes = colorRes
        } else {
            this.colorRes = ContextCompat.getColor(this, R.color.white)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (this.needTransStatusBar) {
            BarUtils.setStatusBarColor(
                this,
                if (this.colorRes != 0) this.colorRes else ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        }
    }

    /**
     * 配置点击事件
     */
    fun configClickListener(vararg views: View?) {
        for (view in views) {
            if (view != null) {
                view.setOnClickListener(this@BaseArmActivity)
            }
        }
    }

    /**
     * 错误确认的操作
     */
    fun doInConfirmError() {
        // do nothing
    }


    @SingleClick
    override fun onClick(v: View?) {
        when (v?.id) {
            else -> {
            }
        }
    }

    override fun getResources(): Resources {
        //解决某些情况下AutoSize会失效的问题
        runOnUiThread {
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()))
        }
        return super.getResources()
    }
}