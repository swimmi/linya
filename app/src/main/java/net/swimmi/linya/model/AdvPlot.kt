package net.swimmi.linya.model

import java.io.Serializable

// 情节
data class AdvPlot(var id: Int, var npc: Int, var text: String, var type: Int, var pre: Int, var exit: Boolean): Serializable {

    var serialVersionUID: Long = 1L
}