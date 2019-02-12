package io.acari.n7

import com.intellij.util.ui.JBUI


const val DEFAULT_VELOCITY_FROM_CITADEL = 2f
const val DEFAULT_DISTANCE_FROM_CITADEL = 0f

object GuidanceSystem {
  private var distanceFromCitadel: Float = 120f
  private var velocityFromCitadel: Float = -1f

  fun reCalibrate() { //aka Garrus
    velocityFromCitadel = DEFAULT_VELOCITY_FROM_CITADEL
    distanceFromCitadel = DEFAULT_DISTANCE_FROM_CITADEL
  }

  fun isHeadingToCitadel() = velocityFromCitadel < 0

  fun calculateCurrentLocation():(Int, Int, Float) -> NormandyPositionData =
      { componentWidth, _, offset ->
        distanceFromCitadel =
            if (distanceFromCitadel < 2) {
              velocityFromCitadel = DEFAULT_VELOCITY_FROM_CITADEL
              2f
            } else if (distanceFromCitadel >= componentWidth - JBUI.scale(15)) {
              velocityFromCitadel = -DEFAULT_VELOCITY_FROM_CITADEL
              componentWidth.toFloat() - JBUI.scale(15)
            } else {
              distanceFromCitadel
            }

        distanceFromCitadel += velocityFromCitadel

        val distanceBetweenCitadelAndNormandy = distanceFromCitadel - JBUI.scale(5f)
        val headingToCitadel = isHeadingToCitadel()
        val startingX = if (headingToCitadel) distanceBetweenCitadelAndNormandy else 2f * offset
        val distanceBetweenNormandyAndOmega = componentWidth - distanceBetweenCitadelAndNormandy
        val lengthOfJetWash = if (headingToCitadel) distanceBetweenNormandyAndOmega else distanceBetweenCitadelAndNormandy
        val positionOfNormandy = if (headingToCitadel) distanceBetweenCitadelAndNormandy - NormandyUI.NORMANDY_TO_CITADEL.iconWidth else distanceBetweenCitadelAndNormandy
        NormandyPositionData(startingX, lengthOfJetWash, positionOfNormandy.toInt())
      }
}