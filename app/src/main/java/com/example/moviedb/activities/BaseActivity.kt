package com.example.moviedb.activities

import androidx.appcompat.app.AppCompatActivity
import com.example.moviedb.repositories.MovieRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    private var disposables: CompositeDisposable = CompositeDisposable()

    protected val movieRepository: MovieRepository by inject()

    override fun onPause() {
        disposables.dispose()
        super.onPause()
    }

    protected fun addDisposable(disposable: Disposable) {
        if (disposables.isDisposed) {
            disposables = CompositeDisposable()
        }
        disposables.add(disposable)
    }
}