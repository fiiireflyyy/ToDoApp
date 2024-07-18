package com.fenix.todoapp.data.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fenix.todoapp.data.repository.TodoItemsRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject
/**
 * [NetworkRequestWorker] responsible for scheduler request
 */
class NetworkRequestWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @Inject lateinit var repository: TodoItemsRepository

    override suspend fun doWork(): Result {
        repository.getTodoItems()
        return Result.success()
    }
}

object WorkScheduler {
    fun schedulerWork(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<NetworkRequestWorker>(8, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "NetworkRequestWork",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}