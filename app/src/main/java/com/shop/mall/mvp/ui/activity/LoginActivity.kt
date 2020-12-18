package com.shop.mall.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.di.component.AppComponent
import com.shop.mall.R
import com.shop.mall.app.base.BaseArmActivity
import com.shop.mall.di.component.DaggerLoginComponent
import com.shop.mall.di.module.LoginModule
import com.shop.mall.mvp.contract.LoginContract
import com.shop.mall.mvp.presenter.LoginPresenter


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
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class LoginActivity : BaseArmActivity<LoginPresenter>(), LoginContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .loginModule(LoginModule(this))
            .build()
            .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {

    }


}
