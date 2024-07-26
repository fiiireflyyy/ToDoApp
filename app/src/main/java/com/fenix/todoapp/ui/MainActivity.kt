package com.fenix.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.fenix.todoapp.R
import com.fenix.todoapp.app.App
import com.fenix.todoapp.data.network.WorkScheduler
import com.fenix.todoapp.di.activity.MainActivityComponent
import com.fenix.todoapp.di.activity.MainActivityModule
import com.fenix.todoapp.ui.aboutAppScreen.AboutAppFragment
import com.fenix.todoapp.ui.aboutAppScreen.AssetsReader

class MainActivity : FragmentActivity() {

    lateinit var mainActivityComponent: MainActivityComponent
        private set

    val assetReader = AssetsReader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityComponent = (applicationContext as App).appComponent.mainActivityComponent(
            MainActivityModule(this)
        )
        mainActivityComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WorkScheduler.schedulerWork(this)

        if(savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragment_container, AboutAppFragment())
            }
        }
    }
}