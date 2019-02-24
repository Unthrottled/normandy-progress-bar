package io.acari.n7.icon

import com.intellij.util.SVGLoader
import java.util.*

object SvgLoaderHacker {

  private lateinit var otherColorPatcher: SVGLoader.SvgColorPatcher

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
            otherColorPatcher = otherPatcher
            otherPatcher
          }
          .map { Optional.of(it) }
          .orElseGet { useFallBackPatcher() }


  private fun useFallBackPatcher(): Optional<SVGLoader.SvgColorPatcher> =
      if (this::otherColorPatcher.isInitialized) Optional.of(otherColorPatcher)
      else Optional.empty()

}