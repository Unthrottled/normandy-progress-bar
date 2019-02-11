package io.acari.n7

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.util.IconLoader
import com.intellij.util.SVGLoader
import com.intellij.util.messages.MessageBusConnection
import org.w3c.dom.Element

/**
 * Forged in the flames of battle by alex.
 */
class NormandyIconComponent : BaseComponent {

    companion object {
        val colorPatcher = NormandyColorPatcher()

        init {
            setColorPatecher()
        }

        fun getNormandyIcon() = IconLoader.getIcon("/normandy.svg")

        private fun setColorPatecher() {
            SVGLoader.setColorPatcher(colorPatcher)
        }
    }

    lateinit var messageBus: MessageBusConnection

    override fun initComponent() {

        messageBus = ApplicationManager.getApplication().messageBus.connect()
    }

    override fun disposeComponent() {
        if (this::messageBus.isInitialized) {
            messageBus.disconnect()
        }
    }
}

class NormandyColorPatcher : SVGLoader.SvgColorPatcher {

    override fun patchColors(svg: Element) {
        val themedPrimary = svg.getAttribute("themedPrimary")
        val themedSecondary = svg.getAttribute("themedSecondary")

        if (themedPrimary == "true") {

        } else if (themedSecondary == "true") {
            svg.setAttribute("fill", "#447A3A")

        }
    }

}