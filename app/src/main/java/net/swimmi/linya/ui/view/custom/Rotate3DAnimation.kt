package net.swimmi.linya.ui.view.custom

import android.graphics.Camera
import android.view.animation.Animation
import android.view.animation.Transformation

class Rotate3DAnimation(centerX: Float, centerY: Float) : Animation() {

    var mCenterX = centerX
    var mCenterY = centerY

    var mCamera = Camera()

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        val camera = mCamera
        val matrix = t!!.matrix
        camera.save()
        camera.rotateY(180f)

        camera.getMatrix(matrix)

        camera.restore()
        matrix.preTranslate(-mCenterX, -mCenterY)
        matrix.postTranslate(mCenterX, mCenterY)
    }
}