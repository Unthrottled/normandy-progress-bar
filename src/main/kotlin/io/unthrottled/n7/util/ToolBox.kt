package io.unthrottled.n7.util

fun runSafely(runner: () -> Unit, onError: (Throwable) -> Unit = {}): Unit =
  try {
    runner()
  } catch (e: Throwable) {
    onError(e)
  }

fun <T> runSafelyWithResult(runner: () -> T, onError: (Throwable) -> T): T =
  try {
    runner()
  } catch (e: Throwable) {
    onError(e)
  }
