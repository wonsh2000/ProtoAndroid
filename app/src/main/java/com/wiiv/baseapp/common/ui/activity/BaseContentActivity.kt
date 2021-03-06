package com.wiiv.baseapp.common.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wiiv.baseapp.common.util.Disposer
import com.wiiv.baseapp.common.util.disposeOnDestroy
import com.wiiv.baseapp.util.RxBus
import com.wiiv.baseapp.util.RxEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseComposableActivity() : AppCompatActivity(), Disposer {

    protected val compositeDisposableOnPause = CompositeDisposable()
    protected val compositeDisposableOnStop = CompositeDisposable()
    protected val compositeDisposableOnDestroy = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProperties(intent?.extras)
        setContent {
            BindingView()
        }

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        compositeDisposableOnPause.clear()
        super.onPause()
    }

    override fun onStop() {
        compositeDisposableOnStop.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposableOnDestroy.clear()
        super.onDestroy()
    }

    override fun disposeOnPause(disposable: Disposable) {
        compositeDisposableOnPause.add(disposable)
    }

    override fun disposeOnStop(disposable: Disposable) {
        compositeDisposableOnStop.add(disposable)
    }

    override fun disposeOnDestroy(disposable: Disposable) {
        compositeDisposableOnDestroy.add(disposable)
    }

    protected open fun setupProperties(bundle: Bundle?) {
        //do nothing
    }

    @Preview(showBackground = true)
    @Composable
    protected open fun BindingView() {

    }

    open fun registerRxBus() {
        RxBus.ViewModelEventBus.listen(RxEvent.VMEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recieveEvent(it.type, it.data)
            }.disposeOnDestroy(this)
    }

    protected open fun recieveEvent(type: Any, data: Any?) {

    }
}