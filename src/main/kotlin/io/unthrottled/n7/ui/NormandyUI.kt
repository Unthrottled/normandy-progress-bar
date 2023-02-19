package io.unthrottled.n7.ui

import com.intellij.ui.ColorUtil
import com.intellij.ui.scale.JBUIScale.scale
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI.Borders
import com.intellij.util.ui.UIUtil
import io.unthrottled.n7.icon.NormandyIconComponent
import io.unthrottled.n7.theme.NormandyTheme
import io.unthrottled.n7.theme.ThemeConfiguration
import io.unthrottled.n7.util.toColor
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.LinearGradientPaint
import java.awt.geom.RoundRectangle2D
import java.util.Optional
import javax.swing.Icon
import javax.swing.JComponent
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI

@Suppress("ACCIDENTAL_OVERRIDE")
open class NormandyUI : BasicProgressBarUI() {

  private val guidanceSystem = GuidanceSystem()

  companion object {
    val NORMANDY = NormandyIconComponent.getNormandyIcon()
    val NORMANDY_TO_CITADEL = NormandyIconComponent.getNormandyToCitadelIcon()

    @JvmStatic
    @Suppress("UNUSED", "UNUSED_PARAMETER")
    fun createUI(c: JComponent?): ComponentUI {
      c?.border = Borders.empty().asUIResource()
      return NormandyUI()
    }
  }

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength

  override fun getPreferredSize(c: JComponent?): Dimension =
    Dimension(super.getPreferredSize(c).width, scale(25))

  /**
   * Draws the progress bar that has no known completion duration.
   * So the Normandy just flies back and forth between destinations.
   *
   */
  override fun paintIndeterminate(g: Graphics, component: JComponent) {
    drawNormandyProgress(
      g,
      component,
      {
        if (guidanceSystem.isHeadingToCitadel()) {
          NORMANDY_TO_CITADEL
        } else {
          NORMANDY
        }
      },
      guidanceSystem.calculateCurrentLocation()
    )
  }

  /**
   * Draws the progress bar that has a known completion duration.
   * So when the normandy completes the trip from the Citadel to Omega,
   * then the task as been complete.
   *
   */
  override fun paintDeterminate(g: Graphics, component: JComponent) {
    drawNormandyProgress(g, component, { NORMANDY }) { componentWidth, componentHeight, offset ->
      guidanceSystem.reCalibrate() // Fixes the jumping between the two progress bars

      val insets = progressBar.insets
      val barRectWidth = componentWidth - (insets.right + insets.left)
      val barRectHeight = componentHeight - (insets.top + insets.bottom)
      val amountFull = getAmountFull(insets, barRectWidth, barRectHeight)

      val startingX = 2f * offset
      val lengthOfContrail = amountFull - scale(5f)
      val distanceBetweenCitadelAndNormandy = lengthOfContrail.toInt()
      NormandyPositionData(startingX, lengthOfContrail, distanceBetweenCitadelAndNormandy)
    }
  }

  private fun drawNormandyProgress(
    g: Graphics,
    component: JComponent,
    getNormandyIcon: () -> Icon,
    positionDataFunction: (Int, Int, Float) -> NormandyPositionData
  ) {
    getCorrectGraphic(g)
      .ifPresent { dimensionsAndGraphic ->
        val drawableGraphic = dimensionsAndGraphic.third

        val componentWidth = component.width
        val preferredHeight = component.preferredSize.height
        val componentHeight =
          if (component.height - preferredHeight % 2 != 0) {
            preferredHeight + 1
          } else {
            preferredHeight
          }

        if (component.isOpaque) {
          drawableGraphic.fillRect(0, 0, componentWidth, componentHeight)
        }

        val graphicsConfig = GraphicsUtil.setupAAPainting(drawableGraphic)

        // SET Progress bar BACKGROUND (ie Space!)
        val progressBarParent = component.parent
        val backgroundColor =
          if (progressBarParent != null) {
            progressBarParent.background
          } else {
            UIUtil.getPanelBackground()
          }
        val tintedBackgroundColor =
          when {
            ThemeConfiguration.isTransparentBackground -> backgroundColor
            ThemeConfiguration.isCustomBackground -> ThemeConfiguration.backgroundColor.toColor()
            ColorUtil.isDark(backgroundColor) -> ColorUtil.brighter(backgroundColor, 5)
            else -> ColorUtil.darker(backgroundColor, 2)
          }
        drawableGraphic.color = tintedBackgroundColor

        val borderRadius = scale(8f)
        val offset = scale(1f)
        drawableGraphic.fill(
          RoundRectangle2D.Float(
            offset,
            offset,
            componentWidth.toFloat() - 2f * offset - offset,
            componentHeight.toFloat() - 2f * offset - offset,
            borderRadius,
            borderRadius
          )
        )

        // Draw Contrail Background
        drawableGraphic.paint = LinearGradientPaint(
          0f,
          scale(0f),
          0f,
          componentHeight.toFloat(),
          NormandyTheme.contrailScales,
          NormandyTheme.contrailColors
            .map { contrailColorFunction -> contrailColorFunction(tintedBackgroundColor) } // Allows transparency
            .toTypedArray()
        )

        val (startingX, lengthOfContrail, distanceBetweenCitadelAndNormandy) =
          positionDataFunction(componentWidth, componentHeight, offset)

        val contrailRadius =
          if (ThemeConfiguration.isTransparentBackground) {
            scale(10f)
          } else {
            borderRadius
          }

        drawableGraphic.fill(
          RoundRectangle2D.Float(
            startingX,
            2f * offset,
            lengthOfContrail,
            componentHeight - scale(5f),
            contrailRadius,
            contrailRadius
          )
        )

        // Draw the Normandy!
        getNormandyIcon()
          .paintIcon(
            progressBar,
            drawableGraphic,
            distanceBetweenCitadelAndNormandy,
            scale(0)
          )

        graphicsConfig.restore()
      }
  }

  private fun getCorrectGraphic(g: Graphics): Optional<Triple<Int, Int, Graphics2D>> =
    Optional.of(g)
      .filter { it is Graphics2D }
      .map { it as Graphics2D }
      .map {
        val insets = progressBar.insets
        Triple(
          progressBar.width - (insets.right + insets.left),
          progressBar.height - (insets.top + insets.bottom),
          it
        )
      }
      .filter { it.first > 0 || it.second > 0 }
}
