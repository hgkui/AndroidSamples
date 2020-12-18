package com.shop.mall.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.shop.mall.di.module.LoginModule

import com.jess.arms.di.scope.ActivityScope
import com.shop.mall.mvp.ui.activity.LoginActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/18/2020 09:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(LoginModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}
