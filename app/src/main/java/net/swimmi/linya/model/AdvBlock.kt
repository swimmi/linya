package net.swimmi.linya.model

import net.swimmi.linya.R

data class AdvBlock(var type: Int, var index: Int, var plot: AdvPlot?, var status: Int = AdvBlock.ON) {
    companion object {
        const val ON: Int = 0
        const val CanIn: Int = 1
        const val In: Int = 2
        const val Off: Int = 3
        val StatusBg = arrayOf(R.drawable.bg_adv_block_on, R.drawable.bg_adv_block_can_in, R.drawable.bg_adv_block_in, R.drawable.bg_adv_block_off)
    }
}