package io.acari.n7.util

import com.intellij.ui.ColorUtil
import java.awt.Color
import java.util.*

fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)

fun Color.toHexString() = "#${ColorUtil.toHex(this)}"

fun String.toColor() = ColorUtil.fromHex(this)