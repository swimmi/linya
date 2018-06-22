package net.swimmi.linya.model

data class Adventure(
        var name: String,
        var text: String,
        var pass: List<AdvPass>
)