package com.shop.mall.app.aop

import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.shop.mall.app.utils.isFastDoubleClick
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

/**
 * 对brvah的onItemClick和onChildItemClick事件进行重复点击过滤
 *
 * @author fang.xc@outlook.com
 * @date 2019-12-11
 */
@Aspect
class ItemClickAspect {

    @Around("execution(* com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener.onItemClick(..))")
    @Throws(Throwable::class)
    fun itemClickFilterHook(joinPoint: ProceedingJoinPoint) {

        // 取出方法的参数
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) {
                view = arg
                break
            }
        }
        if (view == null) {
            return
        }

        if (!isFastDoubleClick(view, 300)) {
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        } else {
            LogUtils.d("itemClickFilterHook -> 重复点击,已过滤")
        }
    }

    @Around("execution(* com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener.onItemChildClick(..))")
    @Throws(Throwable::class)
    fun childClickFilterHook(joinPoint: ProceedingJoinPoint) {
        // 取出方法的参数
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) {
                view = arg
                break
            }
        }
        if (view == null) {
            return
        }
        if (!isFastDoubleClick(view, 300)) {
            // 不是快速点击，执行原方法
            joinPoint.proceed()
        } else {
            LogUtils.d("childClickFilterHook -> 重复点击,已过滤")
        }
    }

}