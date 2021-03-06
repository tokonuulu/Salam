package com.example.chatter.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatter.model.User
import com.example.chatter.repository.AuthRepository
import com.example.chatter.repository.DatabaseRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel
@Inject constructor(
    private val authRepository: AuthRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {


    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        val uid = authRepository.getCurrentUid()
        if ( uid != null )
            loadUserProfile(uid)
    }

    fun getCurrentUid() : String? {
        return authRepository.getCurrentUid()
    }

    private fun loadUserProfile(uid: String) {
        databaseRepository.getUserInfo(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<User> {
                override fun onSubscribe(d: Disposable?) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(user: User?) {
                    if (user != null)
                        onUserInfoChange(user)
                }
                override fun onError(e: Throwable?) {
                }
            })
    }

    private fun onUserInfoChange(user: User) {
        _user.value = user
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}