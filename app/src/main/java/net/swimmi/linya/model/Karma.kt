package net.swimmi.linya.model

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class Karma
(
        @Column(unique = true)
        var name: String,
        var members: String
): LitePalSupport()