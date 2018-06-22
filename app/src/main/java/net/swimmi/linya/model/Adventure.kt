package net.swimmi.linya.model

import java.io.Serializable

data class Adventure(
        var name: String,
        var text: String,
        var pass: List<AdvPass>
): Serializable {

    var serialVersionUID: Long = 1L
}