package com.wiiv.baseapp.common.ui.viewmodel

import androidx.lifecycle.*
import com.wiiv.baseapp.common.ui.fragment.BaseFragment
import com.wiiv.baseapp.common.util.Disposer
import com.wiiv.baseapp.util.Event
import com.wiiv.baseapp.util.RxBus
import com.wiiv.baseapp.util.RxEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), Disposer, LifecycleObserver {


    data class eventSet(
        val eventType: Any,
        val eventData: Any?
    )

    val _event = MutableLiveData<Event<eventSet>>()
    val event: LiveData<Event<eventSet>> = _event

    protected val compositeDisposableOnPause = CompositeDisposable()
    protected val compositeDisposableOnStop = CompositeDisposable()
    protected val compositeDisposableOnDestroy = CompositeDisposable()

    override fun disposeOnPause(disposable: Disposable) {
        compositeDisposableOnPause.add(disposable)
    }

    override fun disposeOnStop(disposable: Disposable) {
        compositeDisposableOnStop.add(disposable)
    }

    override fun disposeOnDestroy(disposable: Disposable) {
        compositeDisposableOnDestroy.add(disposable)
    }

    override fun onCleared() {
        compositeDisposableOnPause.clear()
        compositeDisposableOnStop.clear()
        compositeDisposableOnDestroy.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreated() {
        //do nothing
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        //do nothing
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        //do nothing
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        compositeDisposableOnPause.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        compositeDisposableOnStop.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        compositeDisposableOnDestroy.clear()
    }

    fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
    }

    fun sendEvent(type: Any, data: Any? = null){
        _event.postValue(Event(eventSet(type, data)))
    }

    /*
    *   viewmodel?????? ????????? ?????? ????????? rx??? ??????
    *   @PARAM type : ????????? ?????? ( ??? viewmodel??? ???????????? ?????? )
    *   @PARAM data : ????????? ?????????
    * */
    fun sendEventRx(type: Any, data: Any? = null) {
        RxBus.ViewModelEventBus.publish(RxEvent.VMEvent(type, data))
    }

    fun unBindLifecycle(owner: BaseFragment) {
        owner.lifecycleObservers.clear()
    }

    fun bindLifecycle(owner: BaseFragment) {
        owner.lifecycleObservers.clear()
        owner.lifecycleObservers.add {
            when (it) {
                Lifecycle.Event.ON_CREATE -> onCreated()
                Lifecycle.Event.ON_START -> onStart()
                Lifecycle.Event.ON_RESUME -> onResume()
                Lifecycle.Event.ON_PAUSE -> onPause()
                Lifecycle.Event.ON_STOP -> onStop()
                Lifecycle.Event.ON_DESTROY -> onDestroy()
                else -> {
                }
            }
        }
    }
    
}