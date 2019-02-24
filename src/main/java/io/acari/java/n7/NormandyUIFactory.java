package io.acari.java.n7;


import io.acari.n7.NormandyUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

/**
 * Needs to be isolated from the kotlin code because it does not
 * get properly created when it hangs out with the kotlin code for
 * some raisin...
 */
public class NormandyUIFactory extends NormandyUI {

  @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
  public static ComponentUI createUI(final JComponent c) {
    return NormandyUI.Companion.createUi(c);// Because Kotlin Cannot Shadow Static Methods and this is needed to create the progress bar UI...so yeah it is used and important ._.
  }

}
