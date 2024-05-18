package com.example.testtaskheartrate.ui.scan_heartbeat.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class HeartBeatDetector {
    private val redValues = mutableListOf<Int>()
    private val averageHeartBeat = mutableListOf<Int>()
    private val sampleSize = 30
    private val windowSize = 10

    @Synchronized
    fun addFrame(frame: ByteBuffer, width: Int, height: Int) {
        val byteArray = ByteArray(frame.remaining())
        frame.get(byteArray)
        Log.e("addframe", frame.toString())
        Log.e("addframe", "I WOOORK")

        val yuvImage = YuvImage(byteArray, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)

        val redAverage = calculateRedAverage(out.toByteArray())

        redValues.add(redAverage)
        Log.e("addframe", redValues.toString())
        Log.e("addframe", "REDDDDDDDDDDD")
        if (redValues.size > sampleSize) {
            redValues.removeAt(0)
        }
    }

    private fun calculateRedAverage(jpegData: ByteArray): Int {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeByteArray(jpegData, 0, jpegData.size, options)

        var redSum = 0
        val totalPixels = bitmap.width * bitmap.height

        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val pixel = bitmap.getPixel(x, y)
                val red = Color.red(pixel)
                redSum += red
            }
        }

        bitmap.recycle()

        return redSum / totalPixels
    }

    @Synchronized
    fun getHeartRate(): Int {

        var heartRate = 0
        val signal = redValues.map { it.toDouble() }
        if (signal.all { it < 30.0 }) {
            return -1
        }

        val peaks = findPeaks(signal, sampleSize / 4, sampleSize / 4)

        if (peaks.isEmpty()) {
            return -1
        }

        val intervals = peaks.zipWithNext { a, b -> b - a }
        val avgInterval = intervals.average()


        if (avgInterval > 0) {
            heartRate = (60.0 / (avgInterval / windowSize)).toInt()
            averageHeartBeat.add((60.0 / (avgInterval / windowSize)).toInt())
        } else {
            heartRate = -1
        }

        return heartRate
    }

    private fun findPeaks(signal: List<Double>, minDistance: Int, minHeight: Int): List<Int> {
        val peaks = mutableListOf<Int>()
        var currentPeak = -1

        try {
            for (i in minDistance until signal.size - minDistance) {
                val leftBase = signal.slice(i - minDistance until i).minOrNull() ?: continue
                val rightBase = signal.slice(i + 1..i + minDistance).minOrNull() ?: continue

                if (signal[i] > leftBase + minHeight && signal[i] > rightBase + minHeight) {
                    if (currentPeak == -1 || i - currentPeak >= minDistance) {
                        currentPeak = i
                        peaks.add(i)
                    }
                }
            }
        } catch (e: Exception) {

            e.printStackTrace()
        }

        return peaks
    }


}

