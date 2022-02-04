package io.unthrottled.n7.icon

import com.intellij.util.SVGLoader
import io.unthrottled.n7.config.ConfigurationPersistence
import io.unthrottled.n7.theme.NormandyTheme
import io.unthrottled.n7.theme.ThemeConfiguration
import io.unthrottled.n7.util.runSafely
import io.unthrottled.n7.util.runSafelyWithResult
import org.w3c.dom.Element
import java.net.URL

class NormandyColorPatcher(
  private val otherColorPatcherProvider: SVGLoader.SvgElementColorPatcherProvider
) : SVGLoader.SvgElementColorPatcherProvider {

  override fun forPath(path: String?): SVGLoader.SvgElementColorPatcher =
    buildHackedPatcher(
      runSafelyWithResult({
        otherColorPatcherProvider.forPath(path)
      }) {
        null
      }
    )

  override fun forURL(url: URL?): SVGLoader.SvgElementColorPatcher =
    buildHackedPatcher(
      runSafelyWithResult({
        otherColorPatcherProvider.forURL(url)
      }) {
        null
      }
    )

  private fun buildHackedPatcher(
    otherPatcher: SVGLoader.SvgElementColorPatcher?
  ): SVGLoader.SvgElementColorPatcher {
    val self = this
    val digestGuy = ConfigurationPersistence.instance
      .map {
        config ->
        config.contrailColor +
          config.externalContrailColor +
          config.primaryThemeColor +
          config.secondaryThemeColor +
          config.externalSecondaryColor +
          config.isRainbowMode
      }.map {
        it.toByteArray(Charsets.UTF_8)
      }.orElseGet {
        "我覺得你大腿又厚又性感".toByteArray(Charsets.UTF_8)
      }
    return object : SVGLoader.SvgElementColorPatcher {
      override fun patchColors(svg: Element) {
        self.patchColors(svg, otherPatcher)
      }

      override fun digest(): ByteArray? {
        return digestGuy
      }
    }
  }

  fun patchColors(
    svg: Element,
    otherPatcher: SVGLoader.SvgElementColorPatcher?
  ) {
    runSafely({
      otherPatcher?.patchColors(svg)
    })
    patchChildren(svg, otherPatcher)
  }

  private fun patchChildren(svg: Element, otherPatcher: SVGLoader.SvgElementColorPatcher?) {
    when (svg.getAttribute("themedPrimary")) {
      "fill" -> svg.setAttribute("fill", NormandyTheme.primaryColorString())
      "stroke" -> svg.setAttribute("stroke", NormandyTheme.primaryColorString())
      "both" -> {
        svg.setAttribute("stroke", NormandyTheme.primaryColorString())
        svg.setAttribute("fill", NormandyTheme.primaryColorString())
      }
    }

    val themedSecondaryAttribute = svg.getAttribute("themedSecondary")
    if (themedSecondaryAttribute.isNotBlank() && ThemeConfiguration.isRainbowMode) {
      when (themedSecondaryAttribute) {
        "fill" -> svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        "stroke" -> svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
        "first" -> paintRGB(svg, NormandyTheme.RED_COLOR_STRING)
        "second" -> paintRGB(svg, NormandyTheme.ORANGE_COLOR_STRING)
        "third" -> paintRGB(svg, NormandyTheme.YELLOW_COLOR_STRING)
        "fourth" -> paintRGB(svg, NormandyTheme.GREEN_COLOR_STRING)
        "fifth" -> paintRGB(svg, NormandyTheme.INDIGO_COLOR_STRING)
        "sixth" -> paintRGB(svg, NormandyTheme.VIOLET_COLOR_STRING)
        "both" -> {
          svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
          svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        }
      }
    } else {
      when (themedSecondaryAttribute) {
        "fill" -> svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        "stroke" -> svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
        "both", "first", "second", "third", "fourth", "fifth", "sixth" -> {
          svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
          svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        }
      }
    }

    val nodes = svg.childNodes
    val length = nodes.length
    for (i in 0 until length) {
      val item = nodes.item(i)
      if (item is Element) {
        patchColors(item, otherPatcher)
      }
    }
  }

  private fun paintRGB(svg: Element, colorString: String) {
    svg.setAttribute("stroke", "#$colorString")
    svg.setAttribute("fill", "#$colorString")
  }
}
