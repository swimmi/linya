package net.swimmi.linya.data.helper

import android.util.Log
import net.swimmi.linya.base.DataHelper
import net.swimmi.linya.model.Partner
import org.litepal.LitePal

class HPartner: DataHelper() {

    private val tag = "HPartner"
    fun findAll(): List<Partner> {
        return LitePal.findAll(Partner::class.java)
    }

    fun findAll(type: Int, ling: Int): List<Partner> {

        var str = ""
        val cond = mutableListOf<String>()


        if (type != 0) {
            str += "type = ? and "
            cond.add(type.toString())
        }
        if (ling != 0) {
            str += "ling = ? and "
            cond.add(ling.toString())
        }

        str += "1 > 0"
        cond.add(0, str)

        return LitePal.where(*cond.toTypedArray()).find(Partner::class.java)
    }
}