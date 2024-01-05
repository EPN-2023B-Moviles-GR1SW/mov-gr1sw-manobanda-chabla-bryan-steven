import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Zoologico(
    val id: Int,
    val nombre: String,
    val ubicacion: String,
    val capacidad: Int,
    val tamanio: Double,
    val descripcion: String,
    var fauna: MutableList<Fauna> = mutableListOf()
) {
    companion object {

        fun cargarZoologicosDesdeArchivo(): List<Zoologico> {

            val file = File("src/main/kotlin/data/Zoologico.txt")
            return if (file.exists()) {
                file.readLines().map { line ->
                    val datos = line.split(",")
                    Zoologico(
                        datos[0].toInt(),
                        datos[1],
                        datos[2],
                        datos[3].toInt(),
                        datos[4].toDouble(),
                        datos[5]
                    )
                }
            } else {
                listOf()
            }
        }

        fun obtenerFaunaDelZoologico(idZoologico: Int): List<Fauna> {
            var fauna = Fauna.cargarFaunaDesdeArchivo()
            return fauna.filter { it.idZoologico == idZoologico }
        }

        fun guardarZoologicosEnArchivo(zoos: List<Zoologico>) {
            val lines = zoos.joinToString("\n") { zoo ->
                val faunaIds = zoo.fauna.joinToString(",") { it.id.toString() }
                "${zoo.id},${zoo.nombre},${zoo.ubicacion},${zoo.capacidad},${zoo.tamanio},${zoo.descripcion},$faunaIds"
            }
            val file = File("src/main/kotlin/data/Zoologico.txt")
            file.writeText(lines)
        }
    }

    fun agregarFauna(fauna: Fauna) {
        this.fauna.add(fauna)
    }

    fun eliminarFauna(fauna: Fauna) {
        this.fauna.remove(fauna)
    }
}
