package tasks

import AndroidConsts
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
    private val tgApi: TelegramApi,
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
    fun execute(): Unit = runBlocking {
        val token = token.get()
        val chatId = chatId.get()
        val sizeStr = sizeStr.get()

        val apkFile = apkDir.get().asFile.listFiles()?.first { it.name.endsWith(".apk") }!!

        val variant = getVariantFromApkName(apkFile.name)
        val name = "todolist-$variant-${AndroidConsts.VERSION_CODE}.apk"

        tgApi.sendMessage(message = "Build finished", token = token, chatId = chatId)
        tgApi.sendFile(file = apkFile, filename = name, token = token, chatId = chatId)
        if (sizeStr.isNotEmpty()) {
            tgApi.sendMessage(message = sizeStr, token = token, chatId = chatId)
        }

    }

    private fun getVariantFromApkName(name: String): String {
        val withoutExtension = name.removeSuffix(".apk")
        val withoutPrefix = withoutExtension.removePrefix("app-")
        return withoutPrefix
    }
}

