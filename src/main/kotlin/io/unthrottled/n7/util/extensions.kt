package io.unthrottled.n7.util

import com.intellij.ui.ColorUtil
import org.apache.commons.io.IOUtils
import java.awt.Color
import java.io.InputStream
import java.util.Optional

fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)

fun Color.toHexString() = "#${ColorUtil.toHex(this)}"

fun String.toColor() = ColorUtil.fromHex(this)

fun InputStream.readAllTheBytes(): ByteArray = IOUtils.toByteArray(this)
