package io.acari.n7

import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JComponent
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI

/**
 * Forged in the flames of battle by alex.
 */

open class NormandyUI : BasicProgressBarUI() {

    companion object {
        fun createUi(jComponent: JComponent): ComponentUI {
            jComponent.border = JBUI.Borders.empty().asUIResource()
            return NormandyUI()
        }
    }


    override fun getPreferredSize(c: JComponent?): Dimension =
            Dimension(super.getPreferredSize(c).width, scale(20))

    override fun paintIndeterminate(g: Graphics?, c: JComponent?) {

    }

    override fun paintDeterminate(g: Graphics?, c: JComponent?) {

    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength



}