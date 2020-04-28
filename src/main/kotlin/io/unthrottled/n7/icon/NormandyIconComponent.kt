package io.unthrottled.n7.icon

import com.intellij.openapi.util.IconLoader

object NormandyIconComponent {
  fun getNormandyIcon() = IconLoader.getIcon("/normandy.svg")
  fun getNormandyToCitadelIcon() = IconLoader.getIcon("/normandyToCitadel.svg")
}