package net.swimmi.linya.model

import java.io.Serializable

// 关卡
data class AdvPass(
        var name: String,
        var plots: List<AdvPlot>
): Serializable {

    var serialVersionUID: Long = 1L
}