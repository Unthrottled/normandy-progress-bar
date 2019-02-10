package io.acari.n7

import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ColorUtil
import com.intellij.util.ui.GraphicsUtil
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.JBUI.scale
import com.intellij.util.ui.UIUtil
import java.awt.*
import java.awt.geom.RoundRectangle2D
import java.util.*
import javax.swing.JComponent
import javax.swing.plaf.ComponentUI
import javax.swing.plaf.basic.BasicProgressBarUI

val jetWashColor = { _: Color -> ColorUtil.fromHex("#d6f5f8") }
val outerJetWashColor = { _: Color -> ColorUtil.fromHex("#a38dbe") }
val backgroundColorFunction = { backgroundColor: Color -> backgroundColor }

val colors = arrayOf(outerJetWashColor, jetWashColor,
    outerJetWashColor, jetWashColor,
    outerJetWashColor, backgroundColorFunction, backgroundColorFunction, outerJetWashColor,
    jetWashColor, outerJetWashColor,
    jetWashColor, outerJetWashColor)
val SCALING_FACTOR = 1.0f / colors.size
val jetWashScales = colors.mapIndexed { index, _ -> SCALING_FACTOR * index }
    .toFloatArray()

open class NormandyUI : BasicProgressBarUI() {

  companion object {
    val NORMANDY = IconLoader.getIcon("/normandy.svg")

    fun createUi(jComponent: JComponent): ComponentUI {
      jComponent.border = JBUI.Borders.empty().asUIResource()
      return NormandyUI()
    }
  }

  private var distanceFromCitadel = 20
  private var velocityFromCitadel = -1

  override fun getBoxLength(availableLength: Int, otherDimension: Int): Int = availableLength

  override fun getPreferredSize(c: JComponent?): Dimension =
      Dimension(super.getPreferredSize(c).width, scale(25))

  override fun paintIndeterminate(g: Graphics, component: JComponent) {
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
          val R2 = JBUI.scale(9f)
          val off = JBUI.scale(1f)
          graphic.color = progressBar.foreground
          graphic.fill(RoundRectangle2D.Float(0f, 0f, componentWidth - off, componentHeight - off, R2, R2))

          //Draw Border
          val parent = component.parent
          val backgroundColor = if (parent != null) parent.background else UIUtil.getPanelBackground()
          graphic.color = backgroundColor
          val R = JBUI.scale(8f)
          graphic.fill(RoundRectangle2D.Float(off, off, componentWidth.toFloat() - 2f * off - off, componentHeight.toFloat() - 2f * off - off, R, R))


          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              jetWashScales,
              colors.map { jetWashColorFunction -> jetWashColorFunction(backgroundColor) }.toTypedArray()
          )

          distanceFromCitadel =
              if (distanceFromCitadel < 2) {
                velocityFromCitadel = 1
                2
              } else if (distanceFromCitadel >= componentWidth - scale(15)) {
                velocityFromCitadel = -1
                componentWidth - scale(15)
              } else {
                distanceFromCitadel + velocityFromCitadel
              }

          val distanceBetweenCitadelAndNormandy = distanceFromCitadel - JBUI.scale(5f)
          val headingToCitadel = velocityFromCitadel < 1
          val startingX = if (headingToCitadel) distanceBetweenCitadelAndNormandy else 2f * off
          val distanceBetweenNormandyAndOmega = componentWidth - distanceBetweenCitadelAndNormandy
          val lengthOfJetWash = if(headingToCitadel) distanceBetweenNormandyAndOmega else distanceBetweenCitadelAndNormandy

            graphic.fill(RoundRectangle2D.Float(startingX, 2f * off, lengthOfJetWash,
                componentHeight - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)))

          graphic.translate(0, -(component.height - componentHeight) / 2)


          graphic.translate(0, -(component.height - componentHeight) / 2)

          NORMANDY.paintIcon(progressBar, graphic, distanceBetweenCitadelAndNormandy.toInt(), -scale(2))

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

          val graphicsConfig = GraphicsUtil.setupAAPainting(graphic)

          //SET BACKGROUND
          val R2 = JBUI.scale(9f)
          val off = JBUI.scale(1f)
          graphic.color = progressBar.foreground
          graphic.fill(RoundRectangle2D.Float(0f, 0f, componentWidth - off, componentHeight - off, R2, R2))

          //Draw Border
          val parent = component.parent
          val backgroundColor = if (parent != null) parent.background else UIUtil.getPanelBackground()
          graphic.color = backgroundColor
          val R = JBUI.scale(8f)
          graphic.fill(RoundRectangle2D.Float(off, off, componentWidth.toFloat() - 2f * off - off, componentHeight.toFloat() - 2f * off - off, R, R))

          val insets = progressBar.insets
          val barRectWidth = componentWidth - (insets.right + insets.left)
          val barRectHeight = componentHeight - (insets.top + insets.bottom)
          val amountFull = getAmountFull(insets, barRectWidth, barRectHeight)

          graphic.paint = LinearGradientPaint(0f,
              scale(2f),
              0f,
              componentHeight - scale(6f),
              jetWashScales,
              colors.map { jetWashColorFunction -> jetWashColorFunction(backgroundColor) }.toTypedArray()
          )

          graphic.fill(RoundRectangle2D.Float(2f * off, 2f * off, amountFull - JBUI.scale(5f), componentHeight - JBUI.scale(5f), JBUI.scale(7f), JBUI.scale(7f)))
          graphic.translate(0, -(component.height - componentHeight) / 2)

          NORMANDY.paintIcon(progressBar, graphic, amountFull - scale(5), -scale(2))

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