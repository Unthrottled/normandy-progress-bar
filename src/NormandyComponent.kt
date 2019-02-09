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

        LafManager.getInstance().addLafManagerListener { lafManager ->
            UIManager.put("ProgressBarUI", NormandyUI::javaClass.name)
            UIManager.getDefaults().put(NormandyUI::javaClass.name, NormandyUI::javaClass)

        }
    }

    override fun disposeComponent() {
        if (this::messageBus.isInitialized) {
            messageBus.disconnect()
        }
    }
}