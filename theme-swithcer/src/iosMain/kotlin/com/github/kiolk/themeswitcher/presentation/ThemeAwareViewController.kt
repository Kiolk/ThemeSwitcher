package com.github.kiolk.themeswitcher.presentation

import platform.UIKit.UITraitCollection
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIViewController

class ThemeAwareViewController : UIViewController(null, null) {
    var onSystemThemeChanged: ((Boolean) -> Unit)? = null

    override fun traitCollectionDidChange(previousTraitCollection: UITraitCollection?) {
        super.traitCollectionDidChange(previousTraitCollection)
        if (traitCollection.userInterfaceStyle != previousTraitCollection?.userInterfaceStyle) {
            val isDarkMode =
                traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark
            onSystemThemeChanged?.invoke(isDarkMode)
        }
    }
}
