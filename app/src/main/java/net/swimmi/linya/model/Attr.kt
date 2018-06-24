package net.swimmi.linya.model

/**
 *  hp: Int
 *  mp: Int
    atk: Int
    def: Int
    crt: Int
    hit: Int
    dge: Int
    tna: Int
    blk: Int
 */
class Attr {
    
    var hp = 100
    var mp = 0
    var atk = 0
    var def = 0
    var crt = 0
    var hit = 0
    var dge = 0
    var tna = 0
    var blk = 0

    init {
        
    }
    
    companion object {
        
        fun genAttr(character: Character): Attr {
            val level = character.level
            val ap: Float = character.aptt / 10f
            val attr = Attr()
            attr.hp += (level * 300 * ap).toInt()
            attr.atk += (level * 30 * ap).toInt()
            attr.def += (level * 20 * ap).toInt()
            attr.crt += (level * 10 * ap).toInt()
            attr.hit += (level * 10 * ap).toInt()
            attr.dge += (level * 10 * ap).toInt()
            attr.tna += (level * 10 * ap).toInt()
            attr.blk += (level * 10 * ap).toInt()
            return attr
        }
    }

    override fun toString(): String {
        return "hp: $hp"
    }
}