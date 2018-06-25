package net.swimmi.linya.model

import android.util.Log
import net.swimmi.linya.ui.utils.UAnimate
import net.swimmi.linya.ui.view.custom.CharacterView


data class Character(
        var type: Int,
        var level: Int,
        var aptt: Int
) {
    var attr: MutableMap<String, Int>

    init {
        attr = genAttr()
    }

    private fun genAttr(): MutableMap<String, Int> {
        val level = this.level
        val ap: Float = this.aptt / 10f
        val attr = mutableMapOf<String, Int>()
        attr["maxHp"] = (level * 300 * ap).toInt()
        attr["hp"] = attr["maxHp"]!!
        attr["atk"] = (level * 30 * ap).toInt()
        attr["def"] = (level * 20 * ap).toInt()
        attr["crt"] = (level * 10 * ap).toInt()
        attr["hit"] = (level * 10 * ap).toInt()
        attr["dge"] = (level * 10 * ap).toInt()
        attr["tna"] = (level * 10 * ap).toInt()
        attr["blk"] = (level * 10 * ap).toInt()
        return attr
    }

    lateinit var mView: CharacterView

    fun goto(target: Character) {
        if (mView.enable) {
            UAnimate.round(mView, target.mView) { target.damage(1000) }
        }
    }

    fun shoot(target: Character) {

    }

    fun damage(value: Int) {
        val hp = attr["hp"]?:0
        if (hp > value) {
            attr["hp"] = hp - value
            mView.invalidate()
        } else {
            attr["hp"] = 0
        }
        mView.riseText("- $value")
    }
}