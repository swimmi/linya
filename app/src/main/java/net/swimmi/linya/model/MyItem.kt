package net.swimmi.linya.model

import org.litepal.LitePal
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class MyItem
(
        var item: Long,
        var count: Int

): LitePalSupport() {
    val id: Long = 0
    @Column(ignore = true)
    var itemItem: Item? = null
        get() = LitePal.find(Item::class.java, item)
}