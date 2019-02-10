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

  override fun paintIndeterminate(g2d: Graphics?, c: JComponent) {
    if (g2d !is Graphics2D) {
      return
    }
    val g = g2d


    val b = progressBar.insets // area for border
    val barRectWidth = progressBar.width - (b.right + b.left)
    val barRectHeight = progressBar.height - (b.top + b.bottom)

    if (barRectWidth <= 0 || barRectHeight <= 0) {
      return
    }
    g.color = JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50))
    val w = c.width
    var h = c.preferredSize.height
    if ((c.height - h) % 2 != 0) h++

    val baseRainbowPaint = LinearGradientPaint(0f,
        scale(2f),
        0f,
        h - scale(6f),
        floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
        arrayOf(Color.RED, Color.ORANGE)
    )

    g.paint = baseRainbowPaint

    if (c.isOpaque) {
      g.fillRect(0, (c.height - h) / 2, w, h)
    }
    g.color = JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50))
    val config = GraphicsUtil.setupAAPainting(g)
    g.translate(0, (c.height - h) / 2)
    val x = 0

    val old = g.paint
    g.paint = baseRainbowPaint

    val R = JBUI.scale(8f)
    val R2 = JBUI.scale(9f)
    val containingRoundRect = Area(RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R))
    g.fill(containingRoundRect)
    g.paint = old

    val area = Area(Rectangle2D.Float(0f, 0f, w.toFloat(), h.toFloat()))
    area.subtract(Area(RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R)))
    g.paint = Gray._128
    if (c.isOpaque) {
      g.fill(area)
    }

    area.subtract(Area(RoundRectangle2D.Float(0f, 0f, w.toFloat(), h.toFloat(), R2, R2)))

    val parent = c.parent
    val background = if (parent != null) parent.background else UIUtil.getPanelBackground()
    g.paint = background
    if (c.isOpaque) {
      g.fill(area)
    }


    g.draw(RoundRectangle2D.Float(1f, 1f, w.toFloat() - 2f - 1f, h.toFloat() - 2f - 1f, R, R))
    g.translate(0, -(c.height - h) / 2)

    config.restore()
  }

  //todo: vertical progress bars
  override fun paintDeterminate(g: Graphics, component: JComponent) {
    val insets = progressBar.insets
    Optional.of(g)
        .filter { it is Graphics2D}
        .map { it as Graphics2D }
        .map {
          Triple(progressBar.width - (insets.right + insets.left),
              progressBar.height - (insets.top + insets.bottom), it)
        }
        .filter { it.first > 0 || it.second > 0 }
        .ifPresent {dimensionsAndGraphic->
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

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength


}