package com.devcomentry.photogallery.domain.model

data class DateSelect(
    val date: String = "",
    val month: String = "",
    val time: Long,
    var type: Int,
    var showFull: Boolean = true,
    var isSelect: Int = 0,
    var idFolder: Long = -1,
    var listIdFolder: MutableList<Long> = mutableListOf(),
) {
    override fun equals(other: Any?): Boolean {
        return if (other is DateSelect)
            this.time == other.time
        else
            super.equals(other)

    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + month.hashCode()
        result = 31 * result + time.hashCode()
        result = 31 * result + type
        result = 31 * result + showFull.hashCode()
        result = 31 * result + isSelect
        result = 31 * result + idFolder.hashCode()
        result = 31 * result + listIdFolder.hashCode()
        return result
    }
}
