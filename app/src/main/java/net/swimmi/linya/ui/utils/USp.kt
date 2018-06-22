package net.swimmi.linya.ui.utils

import android.content.Context
import android.util.Base64
import java.io.*

class USp {
    companion object {

        fun contains(context: Context, keyName: String): Boolean {
            val sp = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            return sp.contains(keyName)
        }

        fun <T> saveObject(context: Context, obj: T, keyName: String) {
            val sp = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val bos: ByteArrayOutputStream
            var oos: ObjectOutputStream? = null
            try {
                bos = ByteArrayOutputStream()
                oos = ObjectOutputStream(bos)
                oos.writeObject(obj)
                val bytes = bos.toByteArray()
                val objStr = Base64.encodeToString(bytes, Base64.DEFAULT)
                val spe = sp.edit()
                spe.putString(keyName, objStr)
                spe.apply()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (oos != null) {
                    try {
                        oos.flush()
                        oos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        fun <T> loadObject(context: Context, keyName: String): T? {
            val sp = context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val bytes = Base64.decode(sp.getString(keyName, ""), Base64.DEFAULT)
            val bis: ByteArrayInputStream
            var ois: ObjectInputStream? = null
            var obj: T? = null
            try {
                bis = ByteArrayInputStream(bytes)
                ois = ObjectInputStream(bis)
                obj = ois.readObject() as T
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (ois != null) {
                    try {
                        ois.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            return obj
        }
    }
}