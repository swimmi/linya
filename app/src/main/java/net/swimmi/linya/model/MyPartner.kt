package net.swimmi.linya.model

import org.litepal.LitePal
import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class MyPartner
(
        var partner: Long,
        var level: Int,
        var exp: Int,
        var star: Int

): LitePalSupport() {
    val id: Long = 0
    @Column(ignore = true)
    var partnerItem: Partner? = null
        get() = LitePal.find(Partner::class.java, partner)
}