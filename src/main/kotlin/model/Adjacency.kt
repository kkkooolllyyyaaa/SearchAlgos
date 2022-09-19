package model

data class Adjacency(
    val vertex: String,
    val distance: Long,
) {
    override fun equals(other: Any?) = vertex == (other)
    override fun hashCode() = distance.hashCode()
}
