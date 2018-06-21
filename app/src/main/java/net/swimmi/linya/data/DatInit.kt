package net.swimmi.linya.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.swimmi.linya.model.*
import net.swimmi.linya.ui.utils.UFile
import org.litepal.LitePal

class DatGame {

    private lateinit var context: Context

    private var gson: Gson = Gson()

    fun addData(context: Context) {
        this.context = context
        addItem()
        addPartner()
        loadPlayer()
        loadMyPartner()
        loadMyItem()
    }

    private fun loadPlayer() {
        val player = Player("草未眠", 1000, 100, 300000)
        player.save()
    }

    private fun addItem() {
        val jsonStr = UFile().readText(context, "item.json")
        val list: MutableList<Item> = gson.fromJson(jsonStr, object:TypeToken<List<Item>>(){}.type)
        LitePal.saveAll(list)
    }

    private fun addPartner() {
        val jsonStr = UFile().readText(context, "partner.json")
        val list: MutableList<Partner> = gson.fromJson(jsonStr, object:TypeToken<List<Partner>>(){}.type)
        LitePal.saveAll(list)
    }

    private fun loadMyPartner() {
        val jsonStr = UFile().readText(context, "my_partner.json")
        val list: MutableList<MyPartner> = gson.fromJson(jsonStr, object:TypeToken<List<MyPartner>>(){}.type)
        LitePal.saveAll(list)
    }

    private fun loadMyItem() {
        val jsonStr = UFile().readText(context, "my_item.json")
        val list: MutableList<MyItem> = gson.fromJson(jsonStr, object:TypeToken<List<MyItem>>(){}.type)
        LitePal.saveAll(list)
    }
}