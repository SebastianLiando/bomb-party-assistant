package extension

import org.openqa.selenium.WebElement

/**
 * Returns `true` if this element's hidden CSS attribute is `true`.
 */
val WebElement.isHidden: Boolean
    get() = getAttribute("hidden") == "true"