package com.fenix.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fenix.todoapp.app.App
import com.fenix.todoapp.data.network.NetworkMonitor
import com.fenix.todoapp.data.network.WorkScheduler
import com.fenix.todoapp.navigation.Navigation
import com.fenix.todoapp.ui.design.theme.ToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        app.appComponent.inject(this)
        WorkScheduler.schedulerWork(this)
        var networkMonitor = NetworkMonitor(this)
        setContent {
            ToDoTheme {
                Navigation()
            }
        }
    }
}