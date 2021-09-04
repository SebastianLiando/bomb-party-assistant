package extension

import androidx.compose.ui.res.useResource
import java.io.File

/**
 * Returns the resource file from the given path String.
 */
val String.asResourceFile: File
    get() {
        return useResource(this) { stream ->
            // Create a temporary file
            val file = File.createTempFile(this, "")
            println("Created temp file at ${file.absolutePath}")

            // Mark it for deletion when the application closed.
            file.deleteOnExit()

            // Transfer the content of the resource to the temporary file.
            file.apply {
                file.outputStream().use { outStream ->
                    stream.copyTo(outStream)
                }
            }
        }
    }