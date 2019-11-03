package io.acari.n7.icon

import com.intellij.util.SVGLoader
import java.util.*

object SvgLoaderHacker {

  private lateinit var otherColorPatcher: SVGLoader.SvgElementColorPatcherProvider

  fun collectOtherPatcher(): Optional<SVGLoader.SvgElementColorPatcherProvider> =
      Optional.ofNullable(SVGLoader::class.java.declaredFields
          .firstOrNull { it.name == "ourColorPatcher" })
          .map { ourColorPatcherField ->
            ourColorPatcherField.isAccessible = true
            ourColorPatcherField.get(null)
          }
          .filter { it is SVGLoader.SvgElementColorPatcherProvider }
          .filter { it !is NormandyColorPatcher }
          .map {
            val otherPatcher = it as SVGLoader.SvgElementColorPatcherProvider
            otherColorPatcher = otherPatcher
            otherPatcher
          }
          .map { Optional.of(it) }
          .orElseGet { useFallBackPatcher() }


  private fun useFallBackPatcher(): Optional<SVGLoader.SvgElementColorPatcherProvider> =
      if (this::otherColorPatcher.isInitialized) Optional.of(otherColorPatcher)
      else Optional.empty()

}