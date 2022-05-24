package com.wiiv.baseapp.util

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

sealed class RxBus(private val publisher: Subject<RxEvent>) {

    fun publish(event: RxEvent) {
        publisher.onNext(event)
    }

    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
        .subscribeOn(Schedulers.computation())
        .observeOn(Schedulers.computation())

    object ViewModelEventBus: RxBus(PublishSubject.create())
}

sealed class RxEvent {

    data class VMEvent(val type: Any, val data: Any?): RxEvent()
}