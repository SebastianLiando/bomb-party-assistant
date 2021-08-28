package manager

import kotlinx.coroutines.flow.flow
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.Closeable
import java.io.File

class ChromeDriverManager(private val executable: File) : Closeable {
    private val driver by lazy {
        val service = ChromeDriverService.Builder()
            .usingDriverExecutable(executable)
            .usingAnyFreePort()
            .build()

        val options = ChromeOptions().addArguments("headless")

        ChromeDriver(service, options)
    }

    fun inspectRoom(id: String) = flow<String> {
        val wait = WebDriverWait(driver, 10)

        // Navigate to that room, jklm.fun will ask for the username
        val roomUrl = "$JKLM_BASE_URL/$id"
        driver.get(roomUrl)

        // Press the OK button when asked for the username if required
        wait.until {
            val element = it.findElement(By.cssSelector("div.setNickname"))
            element.getAttribute("hidden") != "true"
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

            syllable?.let { emit(it.text) }
        }
    }

    override fun close() {
        driver.quit()
    }

    companion object {
        private const val JKLM_BASE_URL = "https://jklm.fun"
        const val DRIVER_NAME = "chromedriver.exe"
    }
}