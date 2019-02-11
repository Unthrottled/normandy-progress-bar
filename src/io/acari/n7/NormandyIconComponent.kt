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
        private val colorPatcher = NormandyColorPatcher()

        init {
            setColorPatcher()
        }

        fun getNormandyIcon() = IconLoader.getIcon("/normandy.svg")

        private fun setColorPatcher() {
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
        patchChildren(svg)
    }

    private fun patchChildren(svg: Element) {
        val nodes = svg.childNodes
        val length = nodes.length
        for (i in 0 until length) {
            val item = nodes.item(i)
            if (item is Element) {
                patchColors(item)
            }
        }
    }

}