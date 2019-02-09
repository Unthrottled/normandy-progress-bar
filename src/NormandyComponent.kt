import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.BaseComponent
import com.intellij.util.messages.MessageBusConnection

/**
 * Forged in the flames of battle by alex.
 */


class NormandyComponent : BaseComponent {

    lateinit var messageBus: MessageBusConnection
    override fun initComponent() {
        messageBus = ApplicationManager.getApplication().messageBus.connect()
    }

    override fun disposeComponent() {
        if(this::messageBus.isInitialized){
            messageBus.disconnect()
        }
    }
}