package com.fenix.todoapp.ui

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.fenix.todoapp.app.App
import com.fenix.todoapp.data.network.NetworkConnection
import com.fenix.todoapp.data.network.WorkScheduler
import com.fenix.todoapp.data.repository.TodoItemsRepository
import com.fenix.todoapp.navigation.Navigation
import com.fenix.todoapp.ui.design.theme.ToDoTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as App
        app.appComponent.inject(this)
        WorkScheduler.schedulerWork(this)
        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        val networkConnection = NetworkConnection()
        connectivityManager.requestNetwork(networkConnection.networkRequest, networkConnection.networkCallback)
        setContent {
            ToDoTheme {
                Navigation()
            }
        }
    }

}