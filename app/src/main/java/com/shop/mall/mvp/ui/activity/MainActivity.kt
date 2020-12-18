package com.shop.mall.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.di.component.AppComponent
import com.shop.mall.R
import com.shop.mall.app.base.BaseArmActivity
import com.shop.mall.di.component.DaggerMainComponent
import com.shop.mall.di.module.MainModule
import com.shop.mall.mvp.contract.MainContract
import com.shop.mall.mvp.presenter.MainPresenter

class MainActivity : BaseArmActivity<MainPresenter>(), MainContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder()
            .appComponent(appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }
    
    override fun initData(savedInstanceState: Bundle?) {

    }
}