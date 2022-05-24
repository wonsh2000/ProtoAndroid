package com.wiiv.baseapp.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.wiiv.baseapp.common.util.Disposer
import com.wiiv.baseapp.common.util.disposeOnDestroy
import com.wiiv.baseapp.util.RxBus
import com.wiiv.baseapp.util.RxEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment(private val layoutId: Int) :
    Fragment(), Disposer {

    val lifecycleObservers = mutableListOf<(Lifecycle.Event) -> Unit>()

    protected val compositeDisposableOnPause = CompositeDisposable()
    protected val compositeDisposableOnStop = CompositeDisposable()
    protected val compositeDisposableOnDestroy = CompositeDisposable()

    private var isCreated = false
    private var isFocused = false
    private var isVisibleToUser = true
    private var hasFocus = false

    protected abstract fun setupView(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupProperties(arguments)

        val view = createView(inflater, container)

        setupView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isCreated = true
        notifyLifeCycleUpdate(Lifecycle.Event.ON_CREATE)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        notifyLifeCycleUpdate(Lifecycle.Event.ON_START)
    }

    final override fun onResume() {
        super.onResume()
        runOnResume()
        isFocused = true
    }

    open fun runOnResume() {
        notifyLifeCycleUpdate(Lifecycle.Event.ON_RESUME)
    }

    final override fun onPause() {
        isFocused = false
        runOnPause()
        super.onPause()
    }

    open fun runOnPause() {
        notifyLifeCycleUpdate(Lifecycle.Event.ON_PAUSE)
        compositeDisposableOnPause.clear()
    }

    override fun onStop() {
        notifyLifeCycleUpdate(Lifecycle.Event.ON_STOP)
        compositeDisposableOnStop.clear()
        super.onStop()
    }

    override fun onDestroyView() {
        notifyLifeCycleUpdate(Lifecycle.Event.ON_DESTROY)
        compositeDisposableOnDestroy.clear()
        isCreated = false
        super.onDestroyView()
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

    fun hasFocus() = hasFocus

    protected open fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(layoutId, container, false)
    }

    protected open fun setupProperties(bundle: Bundle?) {
        //do nothing
    }

    private fun notifyLifeCycleUpdate(event: Lifecycle.Event) {
        lifecycleObservers.forEach { it.invoke(event) }
    }

    open fun registerRxBus() {
        RxBus.ViewModelEventBus.listen(RxEvent.VMEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                recieveEvent(it.type, it.data)
            }.disposeOnDestroy(this)
    }

    protected open fun recieveEvent(type: Any, data: Any?) {
        // 각 fragment에서 해당 이벤트 처리
    }
}