package com.stedi.quizapp.other

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.ioMain() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.ioMain() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.ioMain() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.ioMain() = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())