package net.swimmi.linya.model

import net.swimmi.linya.R
import java.io.Serializable

data class AdvBlock(var type: Int, var position: Int? = 0, var plot: AdvPlot? = null, var status: Int = AdvBlock.On): Serializable {

    var serialVersionUID: Long = 1L

    companion object {

        const val tag = "AdvBlock"

        const val On: Int = 0
        const val CanIn: Int = 1
        const val In: Int = 2
        const val Off: Int = 3

        const val PLOT: Int = 0
        const val BATTLE: Int = 1
        const val CHEST: Int = 2
        const val BUFF: Int = 3
        const val DICE: Int = 4
        const val HINDER: Int = 5
        const val EMPTY: Int = 6

        var BlockNames = arrayOf("任务", "战斗", "宝箱", "小莹", "金蟾", "障碍", "")
        val TypeBg = arrayOf(R.color.colorAdvPlot, R.color.colorAdvBattle, R.color.colorAdvChest, R.color.colorAdvBuff, R.color.colorAdvDice, R.color.colorAdvHinder, R.color.colorPopupBackground)
        val StatusBg = arrayOf(R.color.colorAdvBlock, R.color.colorAdvBlockCanIn, R.color.colorPanel, R.color.colorPopupBackground)
    }
}