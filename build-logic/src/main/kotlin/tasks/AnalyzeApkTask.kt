package tasks

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import plugins.TelegramApi
import java.io.File
import java.util.zip.ZipFile
import javax.inject.Inject

abstract class AnalyzeApkTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val projectDir: DirectoryProperty

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() = runBlocking {
        val token = token.get()
        val chatId = chatId.get()
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                val reportFile = File("${apkFile.parent}/apk_analytic_report.txt")
                val report = analyzeApk(apkFile)
                reportFile.writeText(report)
                telegramApi.upload(reportFile, token, chatId)
            }

    }

    private fun analyzeApk(apkFile: File): String {
        val zipFile = ZipFile(apkFile)
        val report = StringBuilder()

        zipFile.entries().asSequence().forEach { entry ->
            if (!entry.isDirectory) {
                val sizeMb = entry.size / (1024.0 * 1024.0)
                report.append("- ${entry.name} ${"%.2f".format(sizeMb)} Mb\n")
            }
        }
        return report.toString()
    }

}