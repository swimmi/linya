package net.swimmi.linya.data.helper

import net.swimmi.linya.base.DataHelper
import net.swimmi.linya.model.Item
import net.swimmi.linya.model.MyItem
import org.litepal.LitePal

class HItem: DataHelper() {

    private val tag = "HItem"

    fun getItems(): List<MyItem> {
        return LitePal.findAll(MyItem::class.java)
    }

    private fun hasItem(item: Item): Boolean {
        return LitePal.isExist(MyItem::class.java, "item = ?", item.id.toString())
    }

    fun getItem(item: Item): MyItem? {
        return if(hasItem(item))
            LitePal.where("item = ?", item.id.toString()).findFirst(MyItem::class.java)
        else null
    }

    private fun findAll(): List<MyItem> {
        return LitePal.findAll(MyItem::class.java)
    }

    fun findAll(type: Int): List<MyItem> {

        if (type == 0)
            return findAll()

        val list = arrayListOf<MyItem>()
        val sql = "select * from myitem a left join item b on (a.item = b.id) where b.type = ?"
        val cursor = LitePal.findBySQL(sql, type.toString())
        while (cursor.moveToNext()) {
            val item = MyItem(cursor.getLong(cursor.getColumnIndex("item")), cursor.getInt(cursor.getColumnIndex("count")))
            list.add(item)
        }

        return list
    }
}