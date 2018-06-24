package net.swimmi.linya.model

data class Team(var members: List<Character>, var arrayType: Int ) {
    companion object {

        private const val AtkArray = 1
        private const val DefArray = -1
        private const val BalArray = 0

        fun getArray(type: Int): Array<Int> {
            return when (type) {
                AtkArray -> arrayOf(0, 1, 0, 2, 0, 3, 4, 0, 5)
                DefArray -> arrayOf(1, 0, 2, 3, 0, 4, 0, 5, 0)
                BalArray -> arrayOf(1, 0, 2, 0, 3, 0, 4, 0, 5)
                else -> arrayOf(0)
            }
        }
    }
}