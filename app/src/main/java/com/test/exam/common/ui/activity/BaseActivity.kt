package com.test.exam.common.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.exam.common.util.Disposer
import com.test.exam.common.util.disposeOnDestroy
import com.test.exam.util.RxBus
import com.test.exam.util.RxEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity(private val layoutId: Int) : AppCompatActivity(), Disposer {

    protected val compositeDisposableOnPause = CompositeDisposable()
    protected val compositeDisposableOnStop = CompositeDisposable()
    protected val compositeDisposableOnDestroy = CompositeDisposable()

    protected abstract fun setupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupProperties(intent?.extras)
        setContentView()
        setupView()
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

    protected open fun setContentView() {
        setContentView(layoutId)
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