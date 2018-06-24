package net.swimmi.linya.model

import net.swimmi.linya.ui.utils.UAnimate
import net.swimmi.linya.ui.view.combine.CharacterView


data class Character(
        var type: Int,
        var level: Int,
        var aptt: Int
) {
    var attr: Attr? = null
        get() = Attr.genAttr(this)
    lateinit var view: CharacterView

    fun goto(target: Character) {
        if (view.enable)
            UAnimate.round(view, target.view)
    }
}