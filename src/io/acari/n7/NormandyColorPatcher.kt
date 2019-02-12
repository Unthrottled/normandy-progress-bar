package io.acari.n7

import com.intellij.util.SVGLoader
import io.acari.n7.theme.NormandyTheme
import org.w3c.dom.Element

class NormandyColorPatcher : SVGLoader.SvgColorPatcher {

  override fun patchColors(svg: Element) {
    val themedPrimary = svg.getAttribute("themedPrimary")
    val themedSecondary = svg.getAttribute("themedSecondary")

    if (themedPrimary == "true") {
      svg.setAttribute("fill", NormandyTheme.primaryColorString())
    } else if (themedSecondary == "true") {
      svg.setAttribute("fill", NormandyTheme.secondaryColorString())

    }
    patchChildren(svg)
  }

  private fun patchChildren(svg: Element) {
    val nodes = svg.childNodes
    val length = nodes.length
    for (i in 0 until length) {
      val item = nodes.item(i)
      if (item is Element) {
        patchColors(item)
      }
    }
  }

}