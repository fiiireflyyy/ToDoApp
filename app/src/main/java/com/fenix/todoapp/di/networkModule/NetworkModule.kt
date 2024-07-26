package com.fenix.todoapp.di.networkModule

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.fenix.todoapp.data.dp.TodoDao
import com.fenix.todoapp.data.dp.TodoDatabase
import com.fenix.todoapp.data.network.NetworkConnection
import com.fenix.todoapp.data.preferences.PreferencesManager
import com.fenix.todoapp.data.preferences.dataStore
import com.fenix.todoapp.di.app.AppScope
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Module
object NetworkModule {

    @Provides
    @AppScope
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens("Turgon", "Turgon")
                    }
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KTOR_REQUEST", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(HttpRequestRetry) {
                maxRetries = 3
                retryIf { request, response ->
                    !response.status.isSuccess()
                }
                delayMillis { retry ->
                    retry * 30L
                }
            }
        }
    }


    @AppScope
    @Provides
    fun provideDataBase(context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "todo.db"
        )
            .build()
    }

    @Provides
    fun provideToDoDao(db: TodoDatabase): TodoDao {
        return db.todoDao()
    }

    @Provides
    @AppScope
    fun provideNetworkConnection(context: Context): NetworkConnection {
        return NetworkConnection(context)
    }
    @Provides
    @AppScope
    fun providePreferencesManager(context: Context): PreferencesManager {
        return PreferencesManager(context.dataStore)
    }
}
