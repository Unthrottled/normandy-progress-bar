package io.acari.n7

import com.intellij.util.SVGLoader
import io.acari.n7.theme.NormandyTheme
import org.w3c.dom.Element
import java.util.*

class NormandyColorPatcher(val theOther: SVGLoader.SvgColorPatcher = SVGLoader.SvgColorPatcher {}) : SVGLoader.SvgColorPatcher {

  override fun patchColors(svg: Element) {
    theOther.patchColors(svg)
    patchChildren(svg)
  }

  private fun patchChildren(svg: Element) {
    val themedPrimary = svg.getAttribute("themedPrimary")
    val themedSecondary = svg.getAttribute("themedSecondary")

    if (themedPrimary == "true") {
      svg.setAttribute("fill", NormandyTheme.primaryColorString())
    } else if (themedSecondary == "true") {
      svg.setAttribute("fill", NormandyTheme.secondaryColorString())

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
}

//todo: optimize dis
object SvgLoaderHacker {

  lateinit var otherColorPatcher: SVGLoader.SvgColorPatcher

  fun collectOtherPatcher(): Optional<SVGLoader.SvgColorPatcher> =
      Optional.ofNullable(SVGLoader::class.java.declaredFields
          .firstOrNull { it.name == "ourColorPatcher" })
          .map { ourColorPatcherField ->
            ourColorPatcherField.isAccessible = true
            ourColorPatcherField.get(null)
          }
          .filter { it is SVGLoader.SvgColorPatcher }
          .filter { !(it is NormandyColorPatcher) }
          .map {
            val otherPatcher = it as SVGLoader.SvgColorPatcher
            this.otherColorPatcher = otherPatcher
            otherPatcher
          }
          .map { Optional.of(it) }
          .orElseGet { retriveOtherColorPatcher() }


  fun retriveOtherColorPatcher(): Optional<SVGLoader.SvgColorPatcher> =
      if (this::otherColorPatcher.isInitialized) Optional.of(otherColorPatcher)
      else Optional.empty()

}