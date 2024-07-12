package tasks

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import plugins.TelegramApi
import javax.inject.Inject

abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val sizeStr: Property<String>

    @TaskAction
    fun report() {
        val token = token.get()
        val chatId = chatId.get()
        val sizeStr = sizeStr.get()

        val apkFile = apkDir.get().asFile.listFiles()?.first { it.name.endsWith(".apk") }!!
        val variant = getVariantApkName(apkFile.name)
        val name = "todolist-$variant-${AndroidConsts.VERSION_CODE}.apk"
        runBlocking {
            telegramApi.sendMessage("Build finished", token, chatId).apply {
                println("Status = $status")
            }
        }
        runBlocking {
            telegramApi.sendFile(apkFile, name, token, chatId).apply {
                println("Status = $status")
            }
        }
        if (sizeStr.isNotEmpty()) {
            runBlocking {
                telegramApi.sendMessage(sizeStr, token, chatId)
            }
    }
}

private fun getVariantApkName(name: String): String {
    val withoutExtension = name.removeSuffix(".apk")
    val withoutAppPrefix = withoutExtension.removePrefix("app-")
    return withoutAppPrefix
}
}