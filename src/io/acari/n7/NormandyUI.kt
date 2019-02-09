package io.acari.n7

import com.intellij.util.ui.JBUI.scale
import java.awt.Dimension
import java.awt.Graphics
import java.lang.RuntimeException
import javax.swing.JComponent
import javax.swing.plaf.basic.BasicProgressBarUI

/**
 * Forged in the flames of battle by alex.
 */

class NormandyUI : BasicProgressBarUI() {


    override fun getPreferredSize(c: JComponent?): Dimension =
            Dimension(super.getPreferredSize(c).width, scale(20))

    override fun paintIndeterminate(g: Graphics?, c: JComponent?) {
        throw RuntimeException("aaoeuaoeuaoeu")
    }

    override fun paintDeterminate(g: Graphics?, c: JComponent?) {

    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength



}