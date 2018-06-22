package net.swimmi.linya.data.helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import net.swimmi.linya.model.Adventure
import net.swimmi.linya.ui.utils.UFile

class HAdventure {


    fun loadAdventure(context: Context): List<Adventure> {
        val gson = Gson()
        val jsonStr = UFile().readText(context, "adventure.json")
        return gson.fromJson(jsonStr, object: TypeToken<List<Adventure>>(){}.type)
    }
}