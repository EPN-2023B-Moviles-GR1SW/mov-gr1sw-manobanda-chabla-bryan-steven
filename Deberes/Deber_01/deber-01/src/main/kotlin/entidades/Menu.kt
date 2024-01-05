import java.text.SimpleDateFormat

class Menu() {

    fun iniciar_Menu() {
        while (true) {
            println("\nSeleccione una opción:")
            println("1. Operaciones con Zoológicos")
            println("2. Operaciones con Fauna")
            println("3. Salir")
            print("Opción: ")

            when (readLine()?.toIntOrNull()) {
                1 -> menuZoologicos()
                2 -> menuFauna()
                3 -> {
                    println("¡Hasta luego!")
                    return
                }
                else -> println("Opción inválida. Intente de nuevo.")
            }
        }
    }

    fun menuZoologicos() {
        val zoos = Zoologico.cargarZoologicosDesdeArchivo().toMutableList()

        while (true) {
            println("\nOperaciones con Zoológicos:")
            println("1. Crear Zoológico")
            println("2. Mostrar Zoológicos")
            println("3. Actualizar Zoológico")
            println("4. Eliminar Zoológico")
            println("5. Volver al menú principal")
            print("Opción: ")

            when (readLine()?.toIntOrNull()) {
                1 -> {
                    val nuevoZoo = crearZooDesdeConsola()
                    zoos.add(nuevoZoo)
                    Zoologico.guardarZoologicosEnArchivo(zoos)
                    println("Zoológico creado exitosamente!")
                }
                2 -> {
                    if (zoos.isNotEmpty()) {
                        println("\nListado de Zoológicos:")
                        zoos.forEachIndexed { index, zoo ->
                            println("${index + 1}. ${zoo.nombre}")
                        }
                    } else {
                        println("No hay zoológicos registrados.")
                    }
                }
                3 -> {
                    if (zoos.isNotEmpty()) {
                        println("\nListado de Zoológicos:")
                        zoos.forEachIndexed { index, zoo ->
                            println("${index + 1}. ${zoo.nombre}")
                        }
                        print("Seleccione el número del zoológico a actualizar: ")
                        val opcion = readLine()?.toIntOrNull() ?: 0
                        if (opcion in 1..zoos.size) {
                            val zooViejo = zoos[opcion - 1]
                            val zooNuevo = crearZooDesdeConsola()
                            zoos.remove(zooViejo)
                            zoos.add(zooNuevo)
                            Zoologico.guardarZoologicosEnArchivo(zoos)
                            println("Zoológico actualizado exitosamente!")
                        } else {
                            println("Selección inválida.")
                        }
                    } else {
                        println("No hay zoológicos para actualizar.")
                    }
                }
                4 -> {
                    if (zoos.isNotEmpty()) {
                        println("\nListado de Zoológicos:")
                        zoos.forEachIndexed { index, zoo ->
                            println("${index + 1}. ${zoo.nombre}")
                        }
                        print("Seleccione el número del zoológico a eliminar: ")
                        val opcion = readLine()?.toIntOrNull() ?: 0
                        if (opcion in 1..zoos.size) {
                            val zooAEliminar = zoos[opcion - 1]
                            zoos.remove(zooAEliminar)
                            Zoologico.guardarZoologicosEnArchivo(zoos)
                            println("Zoológico eliminado exitosamente!")
                        } else {
                            println("Selección inválida.")
                        }
                    } else {
                        println("No hay zoológicos para eliminar.")
                    }
                }
                5 -> return
                else -> println("Opción inválida. Intente de nuevo.")
            }
        }
    }

    fun menuFauna() {
        val zoos = Zoologico.cargarZoologicosDesdeArchivo().toMutableList()
        val fauna = Fauna.cargarFaunaDesdeArchivo().toMutableList()

        while (true) {
            println("\nOperaciones con Fauna:")
            println("1. Agregar animal al zoológico")
            println("2. Eliminar animal del zoológico")
            println("3. Actualizar animal del zoológico")
            println("4. Volver al menú principal")
            print("Opción: ")
            when (readLine()?.toIntOrNull()) {
                1 -> {
                    if (zoos.isNotEmpty()) {
                        println("\nListado de Zoológicos:")
                        zoos.forEachIndexed { index, zoo ->
                            println("${index + 1}. ${zoo.nombre}")
                        }
                        print("Seleccione el número del zoológico para agregar el animal: ")
                        val opcion = readLine()?.toIntOrNull() ?: 0
                        if (opcion in 1..zoos.size) {
                            val zooSeleccionado = zoos[opcion - 1]
                            val nuevoAnimal = crearAnimalDesdeConsola(zooSeleccionado.id)
                            fauna.add(nuevoAnimal)
                            zooSeleccionado.agregarFauna(nuevoAnimal)
                            Zoologico.guardarZoologicosEnArchivo(zoos)
                            Fauna.guardarFaunaEnArchivo(fauna)
                            println("Animal agregado al zoológico exitosamente!")
                        } else {
                            println("Selección inválida.")
                        }
                    } else {
                        println("No hay zoológicos para agregar animales.")
                    }
                }
                2 -> {
                    if (zoos.isNotEmpty()) {
                        println("\nListado de Zoológicos:")
                        zoos.forEachIndexed { index, zoo ->
                            println("${index + 1}. ${zoo.nombre}")
                        }
                        print("Seleccione el número del zoológico para eliminar el animal: ")
                        val opcion = readLine()?.toIntOrNull() ?: 0
                        if (opcion in 1..zoos.size) {
                            val zooSeleccionado = zoos[opcion - 1]
                            var faunaZoo = Zoologico.obtenerFaunaDelZoologico(zooSeleccionado.id)
                            if (faunaZoo.isNotEmpty()) {
                                println("Lista de Fauna en el Zoológico:")
                                faunaZoo.forEachIndexed { index, fauna ->
                                    println("${index + 1}. ${fauna.nombre}")
                                }
                                print("Seleccione el número del animal a eliminar: ")
                                val opcionAnimal = readLine()?.toIntOrNull() ?: 0
                                if (opcionAnimal in 1..faunaZoo.size) {
                                    val animalAEliminar = faunaZoo[opcionAnimal - 1]
                                    fauna.remove(animalAEliminar)
                                    zooSeleccionado.eliminarFauna(animalAEliminar)
                                    Zoologico.guardarZoologicosEnArchivo(zoos)
                                    Fauna.guardarFaunaEnArchivo(fauna)
                                    println("Animal eliminado del zoológico exitosamente!")
                                } else {
                                    println("Selección inválida.")
                                }
                            } else {
                                println("No hay fauna para eliminar.")
                            }
                        } else {
                            println("Selección inválida.")
                        }
                    } else {
                        println("No hay zoológicos para eliminar animales.")
                    }
                }

                3 -> {
                    println("\nListado de Fauna:")
                    fauna.forEachIndexed { index, animal ->
                        println("${index + 1}. ${animal.nombre}")
                    }
                    print("Seleccione el número del animal a actualizar: ")
                    val opcion = readLine()?.toIntOrNull() ?: 0
                    if (opcion in 1..fauna.size) {
                        val animalSeleccionado = fauna[opcion - 1]
                        val animalActualizado = crearAnimalDesdeConsola(animalSeleccionado.idZoologico)
                        animalActualizado.id = animalSeleccionado.id
                        Fauna.actualizarFaunaEnArchivo(animalActualizado)
                        println("Animal actualizado exitosamente!")
                    } else {
                        println("Selección inválida.")
                    }
                }

                4 -> return
                else -> println("Opción inválida. Intente de nuevo.")
            }
        }
    }

    fun crearZooDesdeConsola(): Zoologico {
        println("Ingrese los datos del zoológico:")
        print("Id: ")
        val id = readLine()?.toIntOrNull() ?: 0
        print("Nombre: ")
        val nombre = readLine() ?: ""
        print("Ubicación: ")
        val ubicacion = readLine() ?: ""
        print("Capacidad: ")
        val capacidad = readLine()?.toIntOrNull() ?: 0
        print("Tamaño: ")
        val tamanio = readLine()?.toDoubleOrNull() ?: 0.0
        print("Descripción: ")
        val descripcion = readLine() ?: ""

        return Zoologico(id, nombre, ubicacion, capacidad, tamanio, descripcion)
    }

    fun crearAnimalDesdeConsola(zoo_id: Int): Fauna {
        println("Ingrese los datos del animal:")
        print("Id: ")
        val id = readLine()?.toIntOrNull() ?: 0
        print("Nombre: ")
        val nombre = readLine() ?: ""
        print("Fecha de Nacimiento (dd-MM-yyyy): ")
        val fechaNacimiento = readLine() ?: ""
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val fecha = dateFormat.parse(fechaNacimiento)
        print("Cantidad: ")
        val cantidad = readLine()?.toIntOrNull() ?: 0
        print("¿Es depredador? (true/false): ")
        val esDepredador = readLine()?.toBoolean() ?: false
        print("Tipo: ")
        val tipo = readLine() ?: ""

        return Fauna(id, zoo_id, nombre, fecha, cantidad, esDepredador, tipo)
    }
}