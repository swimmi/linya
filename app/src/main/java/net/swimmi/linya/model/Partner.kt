package net.swimmi.linya.model

import org.litepal.LitePal
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

/**
 * [name]名称
 * [text]描述
 * [ling]五灵
 * [type]类型
 * [aptt]资质
 * [magic]法宝
 */
data class Partner(
        @Column(unique = true)
        var name: String,
        @Column(nullable = true)
        var text: String,
        var gender: Int,
        var ling: Int,
        var type: Int,
        var aptt: Int,
        var magic: Long,
        var karma: String
): LitePalSupport() {

    val id: Long = 0
    @Column(ignore = true)
    var magicItem: Item? = null
        get() = LitePal.find(Item::class.java, magic)

    @Column(ignore = true)
    var karmas: List<Karma>? = null

    @Column(ignore = true)
    var isCollected: Boolean = false
        get() = LitePal.isExist(MyPartner::class.java, "partner = ?", id.toString())
}

