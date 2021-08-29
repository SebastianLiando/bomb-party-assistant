package extension

import androidx.compose.ui.res.useResource
import java.io.File

val String.asResourceFile: File
    get() {
        return useResource(this) { stream ->
            val file = File.createTempFile(this, "")
            println("Created temp file at ${file.absolutePath}")
            file.deleteOnExit()

            file.apply {
                file.outputStream().use { outStream ->
                    stream.copyTo(outStream)
                }
            }
        }
    }