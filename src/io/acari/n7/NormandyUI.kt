package io.acari.n7

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import java.awt.*
import java.util.*
import javax.swing.JComponent
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI

/**
 * Forged in the flames of battle by alex.
 */

val SCALING_FACTOR = 1.0f / 2.0f

open class NormandyUI : BasicProgressBarUI() {

    companion object {
        fun createUi(jComponent: JComponent): ComponentUI {
            jComponent.border = JBUI.Borders.empty().asUIResource()
            return NormandyUI()
        }
    }


    override fun getPreferredSize(c: JComponent?): Dimension =
            Dimension(super.getPreferredSize(c).width, scale(20))

    override fun paintIndeterminate(graphics: Graphics?, component: JComponent) {
        Optional.ofNullable(graphics)
                .filter { graphic -> graphic is Graphics2D }
                .map { graphic -> graphic as Graphics2D }
                .map {
                    val insets = progressBar.insets
                    Triple(progressBar.width - (insets.right + insets.left),
                            progressBar.height - (insets.top + insets.bottom), it)
                }
                .filter { it.first <= 0 || it.second <= 0 }
                .ifPresent { dimensionsAndGraphic ->
                    val graphic = dimensionsAndGraphic.third
                    graphic.color = JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50))
                    val componentWidth = component.width
                    val preferredHeight = component.preferredSize.height
                    val commponentHeight = if (component.height - preferredHeight % 2 == 0) preferredHeight + 1 else preferredHeight
                    graphic.paint = LinearGradientPaint(0f,
                            scale(2f),
                            0f,
                            commponentHeight - scale(6f),
                            floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
                            arrayOf(Color.RED, Color.ORANGE)
                            )
                    if(component.isOpaque){
                        graphic.fillRect(0,(component.height - commponentHeight)/2, componentWidth, commponentHeight)
                    }

                }

    }

    override fun paintDeterminate(g: Graphics?, c: JComponent?) {

    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength


}