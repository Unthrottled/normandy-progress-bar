package icons

import com.intellij.ui.IconManager
import javax.swing.Icon

object NormandyIcons {

  @JvmStatic
  val PLUGIN_LOGO = load("/alliance.svg")

  private fun load(path: String): Icon =
    IconManager.getInstance().getIcon(path, javaClass)
}
