package io.acari.n7;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

public class NormandyUIFactory extends NormandyUI {

  @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
  public static ComponentUI createUI(final JComponent c) {
    return NormandyUI.Companion.createUi(c);// Because Kotlin Cannot Shadow Static Methods ._.
  }

}
