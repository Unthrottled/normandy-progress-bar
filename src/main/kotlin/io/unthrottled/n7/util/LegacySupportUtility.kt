package io.unthrottled.n7.util

object LegacySupportUtility {

    fun invokeClassSafely(clasName: String, runSafely: () -> Unit) {
        try {
            Class.forName(clasName)
            runSafely() // :|
        } catch (ignored: Throwable) {
        }
    }

    fun orRunLegacy(
      clasName: String,
      runCurrent: () -> Unit,
      runLegacy: () -> Unit
    ) {
        try {
            Class.forName(clasName)
            runCurrent() // :|
        } catch (ignored: Throwable) {
            runLegacy()
        }
    }

    fun <T> orGetLegacy(
      clazz: String,
      runSafely: () -> T,
      orElseGet: () -> T
    ) =
            try {
                Class.forName(clazz)
                runSafely()
            } catch (ignored: Throwable) {
                orElseGet()
            }

    fun <C, T> invokeMethodSafely(
      clazz: Class<C>,
      method: String,
      runSafely: () -> T,
      orElseGet: () -> T,
      vararg paratemers: Class<*>
    ) =
            try {
                clazz.getDeclaredMethod(method, *paratemers)
                runSafely()
            } catch (ignored: Throwable) {
                orElseGet()
            }

    fun <C> invokeVoidMethodSafely(
      clazz: Class<C>,
      method: String,
      runSafely: () -> Unit,
      orElseGet: () -> Unit,
      vararg parameters: Class<*>
    ) =
            try {
                clazz.getDeclaredMethod(method, *parameters)
                runSafely()
            } catch (ignored: Throwable) {
                orElseGet()
            }

    fun <C, T> useFieldSafely(
      clazz: Class<C>,
      method: String,
      runSafely: () -> T,
      orElseGet: () -> T
    ) =
            try {
                clazz.getDeclaredField(method)
                runSafely()
            } catch (ignored: Throwable) {
                orElseGet()
            }
}
