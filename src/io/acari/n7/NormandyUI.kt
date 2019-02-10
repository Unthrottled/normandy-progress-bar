package io.acari.n7

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import com.intellij.util.ui.UIUtil
import sun.swing.SwingUtilities2
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.geom.Area
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
import java.util.*
import javax.swing.JComponent
import javax.swing.SwingConstants
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
        val insets = progressBar.insets
        Optional.ofNullable(graphics)
                .filter { graphic -> graphic is Graphics2D }
                .map { graphic -> graphic as Graphics2D }
                .map {
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
                    val componentHeightDifference = component.height - commponentHeight
                    if (component.isOpaque) {
                        graphic.fillRect(0, componentHeightDifference / 2, componentWidth, commponentHeight)
                    }

                    graphic.color = JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50))
                    val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)
                    graphic.translate(0, componentHeightDifference / 2)
                    graphic.paint = LinearGradientPaint(0f,
                            scale(2f),
                            0f,
                            commponentHeight - scale(6f),
                            floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
                            arrayOf(Color.RED, Color.ORANGE)
                    )

                    val R = JBUI.scale(8f)
                    val R2 = JBUI.scale(9f)
                    val containingRoundRect = Area(RoundRectangle2D.Float(1f, 1f, componentWidth - 2f, commponentHeight - 2f, R, R))
                    graphic.fill(containingRoundRect)

                    val area = Area(Rectangle2D.Float(0f, 0f, componentWidth.toFloat(), commponentHeight.toFloat()))
                    area.subtract(Area(RoundRectangle2D.Float(1f, 1f, componentWidth - 2f, commponentHeight - 2f, R, R)))
                    graphic.paint = Gray._128
                    if (component.isOpaque) {
                        graphic.fill(area)
                    }


                    area.subtract(Area(RoundRectangle2D.Float(0f, 0f, componentWidth.toFloat(), commponentHeight.toFloat(), R2, R2)))

                    val parent = component.parent
                    val background = if (parent != null) parent.background else UIUtil.getPanelBackground()
                    graphic.paint = background
                    if (component.isOpaque) {
                        graphic.fill(area)
                    }

                    graphic.draw(RoundRectangle2D.Float(1f, 1f, componentWidth.toFloat() - 2f - 1f, commponentHeight.toFloat() - 2f - 1f, R, R))
                    graphic.translate(0, -componentHeightDifference / 2)

                    // Deal with possible text painting
                    if (progressBar.isStringPainted) {
                        if (progressBar.orientation == SwingConstants.HORIZONTAL) {
                            paintString(graphic, insets.left, insets.top, dimensionsAndGraphic.first, dimensionsAndGraphic.second, boxRect.x, boxRect.width)
                        } else {
                            paintString(graphic, insets.left, insets.top, dimensionsAndGraphic.first, dimensionsAndGraphic.second, boxRect.y, boxRect.height)
                        }
                    }
                    graphicsConfig.restore()

                }

    }

    private fun paintString(g: Graphics, x: Int, y: Int, w: Int, h: Int, fillStart: Int, amountFull: Int) {
        if (g !is Graphics2D) {
            return
        }

        val progressString = progressBar.string
        g.font = progressBar.font
        var renderLocation = getStringPlacement(g, progressString,
                x, y, w, h)
        val oldClip = g.clipBounds

        if (progressBar.orientation == SwingConstants.HORIZONTAL) {
            g.color = selectionBackground
            SwingUtilities2.drawString(progressBar, g, progressString,
                    renderLocation.x, renderLocation.y)
            g.color = selectionForeground
            g.clipRect(fillStart, y, amountFull, h)
            SwingUtilities2.drawString(progressBar, g, progressString,
                    renderLocation.x, renderLocation.y)
        } else { // VERTICAL
            g.color = selectionBackground
            val rotate = AffineTransform.getRotateInstance(Math.PI / 2)
            g.font = progressBar.font.deriveFont(rotate)
            renderLocation = getStringPlacement(g, progressString,
                    x, y, w, h)
            SwingUtilities2.drawString(progressBar, g, progressString,
                    renderLocation.x, renderLocation.y)
            g.color = selectionForeground
            g.clipRect(x, fillStart, w, amountFull)
            SwingUtilities2.drawString(progressBar, g, progressString,
                    renderLocation.x, renderLocation.y)
        }
        g.clip = oldClip
    }

    override fun paintDeterminate(g: Graphics?, c: JComponent?) {

    }

    override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength


}