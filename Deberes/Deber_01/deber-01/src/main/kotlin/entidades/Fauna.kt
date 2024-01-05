import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Fauna(
    var id: Int,
    val idZoologico: Int,
    val nombre: String,
    val fechaNacimiento: Date,
    val cantidad: Int,
    val esDepredador: Boolean,
    val tipo: String
) {
    companion object {
        private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        fun cargarFaunaDesdeArchivo(): List<Fauna> {
            val file = File("src/main/kotlin/data/Fauna.txt")
            return if (file.exists()) {
                file.readLines().map { line ->
                    val datos = line.split(",")
                    val fechaNacimiento = dateFormat.parse(datos[3])
                    Fauna(
                        datos[0].toInt(),
                        datos[1].toInt(),
                        datos[2],
                        fechaNacimiento,
                        datos[4].toInt(),
                        datos[5].toBoolean(),
                        datos[6]
                    )
                }
            } else {
                listOf()
            }
        }

        fun guardarFaunaEnArchivo(fauna: List<Fauna>) {
            val lines = fauna.joinToString("\n") { animal ->
                val fechaNacimientoStr = dateFormat.format(animal.fechaNacimiento)
                "${animal.id},${animal.idZoologico},${animal.nombre},$fechaNacimientoStr,${animal.cantidad},${animal.esDepredador},${animal.tipo}"
            }
            val file = File("src/main/kotlin/data/Fauna.txt")
            file.writeText(lines)
        }

        fun actualizarFaunaEnArchivo(faunaActualizada: Fauna) {
            val fauna = cargarFaunaDesdeArchivo().toMutableList()
            val index = fauna.indexOfFirst { it.id == faunaActualizada.id }
            if (index != -1) {
                fauna[index] = faunaActualizada
                guardarFaunaEnArchivo(fauna)
            } else {
                println("Animal no encontrado.")
            }
        }

    }
}
