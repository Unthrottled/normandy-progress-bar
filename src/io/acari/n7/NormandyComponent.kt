package io.acari.n7

import com.intellij.ide.ui.LafManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.util.messages.MessageBusConnection
import javax.swing.UIManager

/**
 * Forged in the flames of battle by alex.
 */


class NormandyComponent : BaseComponent {

    lateinit var messageBus: MessageBusConnection
    override fun initComponent() {
        messageBus = ApplicationManager.getApplication().messageBus.connect()
        setNormandyUI()
        LafManager.getInstance().addLafManagerListener { lafManager -> setNormandyUI() }
    }

    private fun setNormandyUI() {
        UIManager.put("ProgressBarUI", NormandyUIFactory::class.java.name)
        UIManager.getDefaults().put(NormandyUIFactory::class.java.name, NormandyUIFactory::class.java)
    }

    override fun disposeComponent() {
        if (this::messageBus.isInitialized) {
            messageBus.disconnect()
        }
    }
}