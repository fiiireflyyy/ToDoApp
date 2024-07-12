package plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create
import tasks.AnalyzeApkTask
import tasks.TelegramReporterTask
import tasks.ValidateApkSizeTask

class TelegramReporterPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android not found")

        val extension = project.extensions.create("tgReporter", TelegramExtension::class)
        val telegramApi = TelegramApi(HttpClient(OkHttp))

        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            val name = variant.name.capitalize()

            val validateTaskProvider = project.tasks.register(
                "validateApkSizeFor$name",
                ValidateApkSizeTask::class.java,
                telegramApi,
            )
            validateTaskProvider.configure {
                token.set(extension.token)
                chatId.set(extension.chatId)
                apkDir.set(artifacts)
                maxApkSize.set(extension.maxApkSize)
            }

            project.tasks.register(
                "analyzeApkFor$name",
                AnalyzeApkTask::class.java,
                telegramApi,
            ).configure {
                dependsOn("createDebugApkListingFileRedirect")
                token.set(extension.token)
                chatId.set(extension.chatId)
                apkDir.set(artifacts)
                projectDir.set(project.projectDir)
            }

            project.tasks.register(
                "reportTelegramApkFor${variant.name.capitalize()}",
                TelegramReporterTask::class.java,
                telegramApi
            ).configure {
                val sizeStr: Property<String> = project.objects.property(String::class.java)
                sizeStr.set("")
                if (extension.validationEnabled.get() == true) {
                    dependsOn(validateTaskProvider)
                    sizeStr.set(validateTaskProvider.flatMap { it.size })
                }
                this.sizeStr.set(sizeStr)
                apkDir.set(artifacts)
                token.set(extension.token)
                chatId.set(extension.chatId)

                if (extension.analysisEnabled.get() == true) {
                    finalizedBy("analyzeApkFor$name")
                }
            }
        }
    }
}

interface TelegramExtension {
    val chatId: Property<String>
    val token: Property<String>
    val maxApkSize: Property<Int>
    val validationEnabled: Property<Boolean>
    val analysisEnabled: Property<Boolean>
}