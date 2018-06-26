package net.swimmi.linya.model

import android.util.Log
import net.swimmi.linya.ui.utils.UAnimate
import net.swimmi.linya.ui.utils.URandom
import net.swimmi.linya.ui.utils.hide
import net.swimmi.linya.ui.view.custom.BattleField
import net.swimmi.linya.ui.view.custom.CharacterView


data class Character(
        var type: Int,
        var level: Int,
        var aptt: Int
) {
    private val tag = "Character"
    var attr: MutableMap<String, Int>

    var isNpc = false
    var isAlive = true

    private val baseDge = 20
    private val baseCrt = 60
    private val baseCrtValue = 1.5f
    private val baseBlk = 40

    init {
        attr = genAttr()
    }

    private fun genAttr(): MutableMap<String, Int> {
        val level = this.level
        val ap: Float = this.aptt / 10f
        val attr = mutableMapOf<String, Int>()
        attr["maxHp"] = (level * 300 * ap).toInt()
        attr["hp"] = attr["maxHp"]!!
        attr["mp"] = 0
        attr["atk"] = (level * 40 * ap).toInt()
        attr["def"] = (level * 20 * ap).toInt()
        attr["crt"] = (level * 10 * ap).toInt()
        attr["hit"] = (level * 10 * ap).toInt()
        attr["dge"] = (level * 10 * ap).toInt()
        attr["tna"] = (level * 10 * ap).toInt()
        attr["blk"] = (level * 10 * ap).toInt()
        attr["brk"] = (level * 10 * ap).toInt()
        return attr
    }

    lateinit var mView: CharacterView

    fun hit(target: Character) {
        if (mView.enable) {
            UAnimate.round(mView, target.mView) {
                val value = calc(target)
                target.damage(value)
            }
        }
    }

    fun shoot(field: BattleField, target: Character) {
        if (mView.enable) {
            field.shoot(mView, target.mView) {
                val value = calc(target)
                target.damage(value)
            }
        }
    }

    private fun calc(target: Character): Damage {
        var effect = ""
        var value = 0
        val a1 = attr
        val a2 = target.attr
        // 判断是否闪避
        val dDge = baseDge
        if (URandom.dice(dDge)) {
            effect = "闪避"
        } else {
            // 计算伤害
            val dAtk = a1["atk"]!! - a2["def"]!!
            value += dAtk
            // 判断是否暴击
            val dCrt = baseCrt
            if (URandom.dice(dCrt)) {
                value = (value * baseCrtValue).toInt()
                effect = "暴击"
            } else {
                // 判断是否格挡
                val dBlk = baseBlk
                if (URandom.dice(dBlk)) {
                    value -= dBlk
                    effect = "格挡"
                }
            }
        }
        return Damage(value, effect)
    }

    private fun damage(damage: Damage) {
        val hp = attr["hp"]!!
        val direction = if(isNpc) UAnimate.UP else UAnimate.DOWN
        UAnimate.shock(mView, direction)

        if (hp > damage.value) {
            attr["hp"] = hp - damage.value
        } else {
            attr["hp"] = 0
            isAlive = false
            mView.hide()
        }
        if (damage.effect == "闪避")
            mView.riseText(damage.effect)
        else
            mView.riseText("- ${damage.value} ${damage.effect}")
    }
}