package net.swimmi.linya.data.helper

import net.swimmi.linya.base.DataHelper
import net.swimmi.linya.model.MyPartner
import net.swimmi.linya.model.Partner
import org.litepal.LitePal

class HPlayer: DataHelper() {

    private val tag = "HPlayer"

    fun getPartners(): List<MyPartner> {
        return LitePal.findAll(MyPartner::class.java)
    }

    private fun hasPartner(partner: Partner): Boolean {
        return LitePal.isExist(MyPartner::class.java, "partner = ?", partner.id.toString())
    }

    fun getPartner(partner: Partner): MyPartner? {
        return if(hasPartner(partner))
            LitePal.where("partner = ?", partner.id.toString()).findFirst(MyPartner::class.java)
        else null
    }

    fun getAllPartner(type: Int): List<MyPartner> {

        if (type == 0)
            return getPartners()

        val list = arrayListOf<MyPartner>()
        val sql = "select * from mypartner a left join partner b on (a.partner = b.id) where b.type = ?"
        val cursor = LitePal.findBySQL(sql, type.toString())
        while (cursor.moveToNext()) {
            val myPartner = MyPartner(cursor.getLong(cursor.getColumnIndex("partner")),
                    cursor.getInt(cursor.getColumnIndex("level")),
                    cursor.getInt(cursor.getColumnIndex("exp")),
                    cursor.getInt(cursor.getColumnIndex("star")))
            list.add(myPartner)
        }

        return list
    }
}