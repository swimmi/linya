package net.swimmi.linya.ui.utils

import java.util.*

class URandom {

    private val ran = Random()

    fun dice(rate: Int): Boolean {

        return ran.nextInt(100) <= rate
    }

    fun dice(rateArray: Array<Int>): Int {
        val value = ran.nextInt(100)
        var sum = 0
        for ( (index, rate) in rateArray.withIndex() ) {
            sum += rate
            if(value <= sum) {
                return index
            }
        }
        return 0
    }
}