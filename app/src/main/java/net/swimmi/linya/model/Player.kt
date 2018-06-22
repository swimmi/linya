package net.swimmi.linya.model

import net.swimmi.linya.ui.utils.UTime
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class Player(
        @Column(unique = true)
        var name: String,
        var force: Long,
        var power: Int,
        var money: Long,
        @Column(nullable = true)
        var createdAt: String = UTime().getNowString()
): LitePalSupport() {

    init {

    }

}