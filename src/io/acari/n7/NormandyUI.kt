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
    //boxRect = getBox(boxRect);
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

    // Deal with possible text painting
    if (progressBar.isStringPainted) {
      if (progressBar.orientation == SwingConstants.HORIZONTAL) {
        paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.x, boxRect.width)
      } else {
        paintString(g, b.left, b.top, barRectWidth, barRectHeight, boxRect.y, boxRect.height)
      }
    }
    config.restore()
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

  override fun paintDeterminate(g: Graphics?, c: JComponent) {

    if (g !is Graphics2D) {
      return
    }

    if (progressBar.orientation != SwingConstants.HORIZONTAL || !c.componentOrientation.isLeftToRight) {
      super.paintDeterminate(g, c)
      return
    }
    val config = GraphicsUtil.setupAAPainting(g)
    val b = progressBar.insets // area for border
    val w = progressBar.width
    var h = progressBar.preferredSize.height
    if ((c.height - h) % 2 != 0) h++

    val barRectWidth = w - (b.right + b.left)
    val barRectHeight = h - (b.top + b.bottom)

    if (barRectWidth <= 0 || barRectHeight <= 0) {
      return
    }

    val amountFull = getAmountFull(b, barRectWidth, barRectHeight)

    val parent = c.parent
    val background = if (parent != null) parent.background else UIUtil.getPanelBackground()

    g.setColor(background)
    if (c.isOpaque) {
      g.fillRect(0, 0, w, h)
    }

    val R = JBUI.scale(8f)
    val R2 = JBUI.scale(9f)
    val off = JBUI.scale(1f)

    g.translate(0, (c.height - h) / 2)
    g.color = progressBar.foreground
    g.fill(RoundRectangle2D.Float(0f, 0f, w - off, h - off, R2, R2))
    g.color = background
    g.fill(RoundRectangle2D.Float(off, off, w.toFloat() - 2f * off - off, h.toFloat() - 2f * off - off, R, R))
    g.paint = LinearGradientPaint(0f,
        scale(2f),
        0f,
        h - scale(6f),
        floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
        arrayOf(Color.RED, Color.ORANGE)
    )

    g.fill(RoundRectangle2D.Float(2f * off, 2f * off, amountFull - JBUI.scale(5f), h - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)))
    g.translate(0, -(c.height - h) / 2)

    // Deal with possible text painting
    if (progressBar.isStringPainted) {
      paintString(g, b.left, b.top,
          barRectWidth, barRectHeight,
          amountFull, b)
    }
    config.restore()
  }

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength


}