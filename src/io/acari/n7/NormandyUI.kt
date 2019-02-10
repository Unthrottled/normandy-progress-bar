package io.acari.n7

import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.geom.Area
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D
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

  override fun paintIndeterminate(g: Graphics, c: JComponent) {
    getCorrectGraphic(g)
        .ifPresent { dimensionsAndGraphic ->
          val graphic = dimensionsAndGraphic.third

          graphic.color = JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50))
          val componentWidth = c.width
          var componentHeight = c.preferredSize.height
          if ((c.height - componentHeight) % 2 != 0) componentHeight++

          val baseRainbowPaint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
              arrayOf(Color.RED, Color.ORANGE)
          )

          graphic.paint = baseRainbowPaint

          val heightDifference = (c.height - componentHeight) / 2
          if (c.isOpaque) {
            graphic.fillRect(0, heightDifference, componentWidth, componentHeight)
          }
          graphic.color = JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50))
          val config = GraphicsUtil.setupAAPainting(graphic)
          graphic.translate(0, heightDifference)
          val x = 0

          val old = graphic.paint
          graphic.paint = baseRainbowPaint

          val R = JBUI.scale(8f)
          val R2 = JBUI.scale(9f)
          val containingRoundRect = Area(RoundRectangle2D.Float(1f, 1f, componentWidth - 2f, componentHeight - 2f, R, R))
          graphic.fill(containingRoundRect)
          graphic.paint = old

          val area = Area(Rectangle2D.Float(0f, 0f, componentWidth.toFloat(), componentHeight.toFloat()))
          area.subtract(Area(RoundRectangle2D.Float(1f, 1f, componentWidth - 2f, componentHeight - 2f, R, R)))
          graphic.paint = Gray._128
          if (c.isOpaque) {
            graphic.fill(area)
          }

          area.subtract(Area(RoundRectangle2D.Float(0f, 0f, componentWidth.toFloat(), componentHeight.toFloat(), R2, R2)))

          val parent = c.parent
          val background = if (parent != null) parent.background else UIUtil.getPanelBackground()
          graphic.paint = background
          if (c.isOpaque) {
            graphic.fill(area)
          }


          graphic.draw(RoundRectangle2D.Float(1f, 1f, componentWidth.toFloat() - 2f - 1f, componentHeight.toFloat() - 2f - 1f, R, R))
          graphic.translate(0, -(c.height - componentHeight) / 2)

          config.restore()

        }
  }

  //todo: vertical progress bars
  override fun paintDeterminate(g: Graphics, component: JComponent) {
    getCorrectGraphic(g)
        .ifPresent {dimensionsAndGraphic->
          val insets = progressBar.insets
          val graphic = dimensionsAndGraphic.third
          val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)
          val componentWidth = component.width
          val preferredHeight = component.preferredSize.height
          val componentHeight = if (component.height - preferredHeight % 2 != 0) preferredHeight + 1 else preferredHeight
          val barRectWidth = componentWidth - (insets.right + insets.left)
          val barRectHeight = componentHeight - (insets.top + insets.bottom)


          val amountFull = getAmountFull(insets, barRectWidth, barRectHeight)
          val parent = component.parent
          val backgroundColor = if (parent != null) parent.background else UIUtil.getPanelBackground()

          graphic.color = backgroundColor
          if (component.isOpaque) {
            graphic.fillRect(0, 0, componentWidth, componentHeight)
          }


          val R = JBUI.scale(8f)
          val R2 = JBUI.scale(9f)
          val off = JBUI.scale(1f)

          graphic.translate(0, (component.height - componentHeight) / 2)
          graphic.color = progressBar.foreground
          graphic.fill(RoundRectangle2D.Float(0f, 0f, componentWidth - off, componentHeight - off, R2, R2))

          graphic.color = backgroundColor
          graphic.fill(RoundRectangle2D.Float(off, off, componentWidth.toFloat() - 2f * off - off, componentHeight.toFloat() - 2f * off - off, R, R))
          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
              arrayOf(Color.RED, Color.ORANGE)
          )

          graphic.fill(RoundRectangle2D.Float(2f * off, 2f * off, amountFull - JBUI.scale(5f), componentHeight - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)))
          graphic.translate(0, -(component.height - componentHeight) / 2)

          graphicsConfig.restore()

        }

//    if (progressBar.orientation != SwingConstants.HORIZONTAL || !component.componentOrientation.isLeftToRight) {
//      super.paintDeterminate(graphic, component)
//      return
//    }
  }

  private fun getCorrectGraphic(g: Graphics): Optional<Triple<Int, Int, Graphics2D>> = Optional.of(g)
      .filter { it is Graphics2D }
      .map { it as Graphics2D }
      .map {
        val insets = progressBar.insets
        Triple(progressBar.width - (insets.right + insets.left),
            progressBar.height - (insets.top + insets.bottom), it)
      }
      .filter { it.first > 0 || it.second > 0 }

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength


}