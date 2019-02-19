package io.acari.n7

import com.intellij.ui.ColorUtil
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import com.intellij.util.ui.UIUtil
import io.acari.n7.GuidanceSystem.isHeadingToCitadel
import io.acari.n7.theme.NormandyTheme
import java.awt.*
import java.awt.geom.RoundRectangle2D
import java.util.*
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI

open class NormandyUI : BasicProgressBarUI() {

  companion object {

    val NORMANDY = NormandyIconComponent.getNormandyIcon()
    val NORMANDY_TO_CITADEL = NormandyIconComponent.getNormandyToCitadelIcon()

    fun createUi(jComponent: JComponent): ComponentUI {
      jComponent.border = JBUI.Borders.empty().asUIResource()
      return NormandyUI()
    }
  }

  private val borderColor: Color = NormandyTheme.borderColor()

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength

  override fun getPreferredSize(c: JComponent?): Dimension =
      Dimension(super.getPreferredSize(c).width, scale(25))

  override fun paintIndeterminate(g: Graphics, component: JComponent) {
    drawNormandyProgress(g, component, { if (isHeadingToCitadel()) NORMANDY_TO_CITADEL else NORMANDY },
        GuidanceSystem.calculateCurrentLocation())
  }

  override fun paintDeterminate(g: Graphics, component: JComponent) {
    drawNormandyProgress(g, component, { NORMANDY }) { componentWidth, componentHeight, offset ->
      GuidanceSystem.reCalibrate()// Fixes the jumping between the two progress bars

      val insets = progressBar.insets
      val barRectWidth = componentWidth - (insets.right + insets.left)
      val barRectHeight = componentHeight - (insets.top + insets.bottom)
      val amountFull = getAmountFull(insets, barRectWidth, barRectHeight)

      val startingX = 2f * offset
      val lengthOfJetwash = amountFull - JBUI.scale(5f)
      val distanceBetweenCitadelAndNormandy = amountFull - scale(5)
      NormandyPositionData(startingX, lengthOfJetwash, distanceBetweenCitadelAndNormandy)
    }
  }

  private fun drawNormandyProgress(g: Graphics, component: JComponent, getNormandyIcon: () -> Icon,
                                   positionDataFunction: (Int, Int, Float) -> NormandyPositionData) {
    getCorrectGraphic(g)
        .ifPresent { dimensionsAndGraphic ->
          val graphic = dimensionsAndGraphic.third

          val componentWidth = component.width
          val preferredHeight = component.preferredSize.height
          val componentHeight = if (component.height - preferredHeight % 2 != 0) preferredHeight + 1 else preferredHeight

          if (component.isOpaque) {
            graphic.fillRect(0, 0, componentWidth, componentHeight)
          }

          val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)

          //SET BACKGROUND
          val parent = component.parent
          val backgroundColor = if (parent != null) parent.background else UIUtil.getPanelBackground()
          val tintedBackgroundColor =
              if (ColorUtil.isDark(backgroundColor)) ColorUtil.brighter(backgroundColor, 3)
              else ColorUtil.darker(backgroundColor, 3)
          graphic.color = tintedBackgroundColor
          val R = JBUI.scale(8f)
          val off = JBUI.scale(1f)
          graphic.fill(RoundRectangle2D.Float(off, off, componentWidth.toFloat() - 2f * off - off, componentHeight.toFloat() - 2f * off - off, R, R))


          //Draw Jetwash Background
          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              NormandyTheme.jetWashScales,
              NormandyTheme.colors
                  .map { jetWashColorFunction -> jetWashColorFunction(backgroundColor) } // Allows transparency
                  .toTypedArray()
          )

          val (startingX, lengthOfJetWash, distanceBetweenCitadelAndNormandy) =
              positionDataFunction(componentWidth, componentHeight, off)

          graphic.fill(RoundRectangle2D.Float(startingX, 2f * off,
              lengthOfJetWash, componentHeight - JBUI.scale(5f),
              JBUI.scale(7f), JBUI.scale(7f)))

          //Draw Normandy
          getNormandyIcon().paintIcon(progressBar, graphic, distanceBetweenCitadelAndNormandy, -scale(2))

          graphicsConfig.restore()
        }
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