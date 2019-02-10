package io.acari.n7

import com.intellij.openapi.util.IconLoader
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
    val NORMANDY = IconLoader.getIcon("/normandy.svg")

    fun createUi(jComponent: JComponent): ComponentUI {
      jComponent.border = JBUI.Borders.empty().asUIResource()
      return NormandyUI()
    }
  }

  private var distanceFromCitadel = 0
  private var velocityFromCitadel = 1

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength

  override fun getPreferredSize(c: JComponent?): Dimension =
      Dimension(super.getPreferredSize(c).width, scale(25))

  override fun paintIndeterminate(g: Graphics, component: JComponent) {
    getCorrectGraphic(g)
        .ifPresent { dimensionsAndGraphic ->
          val graphic = dimensionsAndGraphic.third

          graphic.color = JBColor(Gray._240.withAlpha(50), Gray._128.withAlpha(50))

          val componentWidth = component.width
          val preferredHeight = component.preferredSize.height
          val componentHeight = if (component.height - preferredHeight % 2 != 0) preferredHeight + 1 else preferredHeight


          val heightDifference = (component.height - componentHeight) / 2

          if (component.isOpaque) {
            graphic.fillRect(0, 0, componentWidth, componentHeight)
          }

          graphic.color = JBColor(Gray._165.withAlpha(50), Gray._88.withAlpha(50))

          val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)

          //SET BACKGROUND
          graphic.translate(0, heightDifference)
          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
              arrayOf(Color.RED, Color.ORANGE)
          )

          //PAINT BACKGROUND COMPONENT
          val R = JBUI.scale(8f)
          val containingRoundRect = Area(RoundRectangle2D.Float(1f, 1f, componentWidth - 2f, componentHeight - 2f, R, R))
          graphic.fill(containingRoundRect)

          graphic.paint = Gray._200

          distanceFromCitadel =
              if (distanceFromCitadel < 2) {
                velocityFromCitadel = 1
                2
              } else if (distanceFromCitadel >= componentWidth - scale(15)) {
                velocityFromCitadel = -1
                componentWidth - scale(15)
              } else {
                distanceFromCitadel
              }
          distanceFromCitadel += velocityFromCitadel

          NORMANDY.paintIcon(progressBar, graphic, distanceFromCitadel - scale(5), -scale(2))
          graphic.draw(RoundRectangle2D.Float(1f, 1f, componentWidth.toFloat() - 2f - 1f, componentHeight - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)))
          graphic.translate(0, -(component.height - componentHeight) / 2)

          graphicsConfig.restore()

        }
  }

  //todo: vertical progress bars
  override fun paintDeterminate(g: Graphics, component: JComponent) {
    getCorrectGraphic(g)
        .ifPresent { dimensionsAndGraphic ->
          val graphic = dimensionsAndGraphic.third

          val componentWidth = component.width
          val preferredHeight = component.preferredSize.height
          val componentHeight = if (component.height - preferredHeight % 2 != 0) preferredHeight + 1 else preferredHeight

          if (component.isOpaque) {
            graphic.fillRect(0, 0, componentWidth, componentHeight)
          }

          graphic.color = progressBar.foreground

          val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)

          graphic.translate(0, (component.height - componentHeight) / 2)
          val R = JBUI.scale(8f)
          val R2 = JBUI.scale(9f)
          val off = JBUI.scale(1f)
          graphic.fill(RoundRectangle2D.Float(0f, 0f, componentWidth - off, componentHeight - off, R2, R2))

          val parent = component.parent
          val backgroundColor = if (parent != null) parent.background else UIUtil.getPanelBackground()
          graphic.color = backgroundColor

          graphic.fill(RoundRectangle2D.Float(off, off, componentWidth.toFloat() - 2f * off - off, componentHeight.toFloat() - 2f * off - off, R, R))
          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              floatArrayOf(SCALING_FACTOR * 1, SCALING_FACTOR * 2),
              arrayOf(Color.RED, Color.ORANGE)
          )

          val insets = progressBar.insets
          val barRectWidth = componentWidth - (insets.right + insets.left)
          val barRectHeight = componentHeight - (insets.top + insets.bottom)
          val amountFull = getAmountFull(insets, barRectWidth, barRectHeight)

          NORMANDY.paintIcon(progressBar, graphic, amountFull - scale(5), -scale(2))
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


}