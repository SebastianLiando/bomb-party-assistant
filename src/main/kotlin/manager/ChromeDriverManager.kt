package manager

import extension.isHidden
import kotlinx.coroutines.flow.flow
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.Closeable
import java.io.File

/**
 * This class manages interaction with the web driver.
 *
 * @property executable The web driver file.
 */
class ChromeDriverManager(private val executable: File) : Closeable {
    /** Whether the driver has been lazily loaded. */
    var isDriverLoaded = false

    /** The web driver. This is lazily loaded since it takes a few seconds to start.
     * To check if the driver is loaded, check the property [isDriverLoaded].
     * */
    private val driver by lazy {
        // Indicate that the driver has been loaded.
        isDriverLoaded = true

        // Tell the driver to use any port
        val service = ChromeDriverService.Builder()
            .usingDriverExecutable(executable)
            .usingAnyFreePort()
            .build()

        // Headless argument makes the browser to run without user interface.
        val options = ChromeOptions().addArguments("headless")

        ChromeDriver(service, options)
    }

    /**
     * Observe the syllable in the room with the given [id].
     *
     * @param id The room id to inspect.
     */
    fun inspectRoom(id: String) = flow<String> {
        val wait = WebDriverWait(driver, 10)

        // Navigate to the room, jklm.fun will ask for the username
        val roomUrl = "$JKLM_BASE_URL/$id"
        driver.get(roomUrl)

        // Press the OK button when asked for the username if required
        wait.until {
            val element = it.findElement(By.cssSelector("div.setNickname"))
            !element.isHidden
        }
        val okButton = driver.findElements(By.cssSelector("button.styled")).first {
            it.text == "OK"
        }
        okButton.click()

        // Wait until <iframe> is available
        // If error, then exception will be thrown
        val iframe = wait.until {
            it.findElement(By.tagName("iframe"))
        }
        driver.switchTo().frame(iframe)

        // Regularly check syllable
        while (true) {
            val syllable = driver.findElements(By.cssSelector("div.syllable")).firstOrNull()

            // If the syllable is displayed, emit it
            syllable?.let { emit(it.text) }
        }
    }

    override fun close() {
        driver.quit()
    }

    companion object {
        /** The base URL of JKLM.fun website. */
        private const val JKLM_BASE_URL = "https://jklm.fun"
    }
}