package net.swimmi.linya.model

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class Item
(
        @Column(unique = true)
        var name: String,
        var text: String,
        var type: Int
): LitePalSupport() {

    val id: Long = 0
}