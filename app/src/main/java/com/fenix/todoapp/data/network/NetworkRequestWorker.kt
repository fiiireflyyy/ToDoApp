package com.fenix.todoapp.data.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fenix.todoapp.data.repository.TodoItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkRequestWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @Inject lateinit var repository: TodoItemsRepository

    override suspend fun doWork(): Result {
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        repository.getList()
        Log.d("NetworkRequestWorker", "Сетевой запрос выполнен в $currentTime")
        return Result.success()
    }
}

object WorkScheduler {
    fun schedulerWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<NetworkRequestWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NetworkRequestWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}