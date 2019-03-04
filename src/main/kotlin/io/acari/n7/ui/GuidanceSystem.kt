package io.acari.n7.ui

import com.intellij.util.ui.JBUI.*

const val DEFAULT_VELOCITY_FROM_CITADEL = 2f
const val DEFAULT_DISTANCE_FROM_CITADEL = 0f
val iconWidth = NormandyUI.NORMANDY_TO_CITADEL.iconWidth

class GuidanceSystem {
  private var distanceFromCitadel: Float = DEFAULT_DISTANCE_FROM_CITADEL
  private var velocityFromCitadel: Float = DEFAULT_VELOCITY_FROM_CITADEL

  fun reCalibrate() { //aka Garrus
    velocityFromCitadel = DEFAULT_VELOCITY_FROM_CITADEL
    distanceFromCitadel = DEFAULT_DISTANCE_FROM_CITADEL
  }

  fun isHeadingToCitadel() = velocityFromCitadel < 0

  fun calculateCurrentLocation():(Int, Int, Float) -> NormandyPositionData =
      { componentWidth, _, offset ->
        distanceFromCitadel =
            when {
              isAtCitadel() -> {
                velocityFromCitadel = DEFAULT_VELOCITY_FROM_CITADEL //towards Omega away from the Citadel
                2f
              }
              isAtOmega(componentWidth) -> {
                velocityFromCitadel = -DEFAULT_VELOCITY_FROM_CITADEL //towards the Citadel and away from Omega
                componentWidth.toFloat()
              }
              else -> distanceFromCitadel
            }

        distanceFromCitadel += velocityFromCitadel

        val distanceBetweenCitadelAndNormandy = distanceFromCitadel
        val headingToCitadel = isHeadingToCitadel()
        val whiteSpaceOffset = scale(2f)//the reversed normandy has a bit of whitespace in the front so when it is flipped the nozzles do not match the contrails
        val startingX = if (headingToCitadel) { distanceBetweenCitadelAndNormandy + iconWidth - whiteSpaceOffset
        } else 2f * offset
        val distanceBetweenNormandyAndOmega = componentWidth - distanceBetweenCitadelAndNormandy
        val lengthOfContrail = if (headingToCitadel) distanceBetweenNormandyAndOmega else distanceBetweenCitadelAndNormandy - iconWidth
        val positionOfNormandy = if (headingToCitadel) distanceBetweenCitadelAndNormandy + whiteSpaceOffset else distanceBetweenCitadelAndNormandy - iconWidth
        NormandyPositionData(startingX, lengthOfContrail, positionOfNormandy.toInt())
      }

  private fun isAtOmega(componentWidth: Int) = distanceFromCitadel >= componentWidth + iconWidth

  private fun isAtCitadel() = distanceFromCitadel < -iconWidth
}