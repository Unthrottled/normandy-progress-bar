package io.acari.n7.icon

import com.intellij.util.SVGLoader
import io.acari.n7.theme.NormandyTheme
import io.acari.n7.theme.ThemeConfiguration
import org.w3c.dom.Element
import java.net.URL

class NormandyColorPatcher(private val otherColorPatcher: (Element) ->Unit = {} ) : SVGLoader.SvgElementColorPatcherProvider {
  override fun forURL(url: URL?): SVGLoader.SvgElementColorPatcher {
    val self = this;
    return object : SVGLoader.SvgElementColorPatcher {
      override fun patchColors(svg: Element) {
        self.patchColors(svg)
      }

      override fun digest(): ByteArray? {
        return null
      }
    }
  }

  fun patchColors(svg: Element) {
    otherColorPatcher(svg)
    patchChildren(svg)
  }

  private fun patchChildren(svg: Element) {
    val themedPrimaryAttribute = svg.getAttribute("themedPrimary")

    when(themedPrimaryAttribute){
      "fill"-> svg.setAttribute("fill", NormandyTheme.primaryColorString())
      "stroke"-> svg.setAttribute("stroke", NormandyTheme.primaryColorString())
      "both"-> {
        svg.setAttribute("stroke", NormandyTheme.primaryColorString())
        svg.setAttribute("fill", NormandyTheme.primaryColorString())
      }
    }

    val themedSecondaryAttribute = svg.getAttribute("themedSecondary")
    if(themedSecondaryAttribute.isNotBlank() && ThemeConfiguration.isRainbowMode){
      when(themedSecondaryAttribute){
        "fill"-> svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        "stroke"-> svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
        "first" -> paintRGB(svg, NormandyTheme.RED_COLOR_STRING)
        "second" -> paintRGB(svg, NormandyTheme.ORANGE_COLOR_STRING)
        "third" -> paintRGB(svg, NormandyTheme.YELLOW_COLOR_STRING)
        "fourth" -> paintRGB(svg, NormandyTheme.GREEN_COLOR_STRING)
        "fifth" -> paintRGB(svg, NormandyTheme.INDIGO_COLOR_STRING)
        "sixth" -> paintRGB(svg, NormandyTheme.VIOLET_COLOR_STRING)
        "both"-> {
          svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
          svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        }
      }
    } else {
      when(themedSecondaryAttribute){
        "fill"-> svg.setAttribute("fill", NormandyTheme.secondaryColorString())
        "stroke"-> svg.setAttribute("stroke", NormandyTheme.secondaryColorString())
        "both", "first","second","third","fourth","fifth","sixth"-> {
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
        patchColors(item)
      }
    }
  }

  private fun paintRGB(svg: Element, colorString: String) {
    svg.setAttribute("stroke", "#$colorString")
    svg.setAttribute("fill", "#$colorString")
  }
}