import java.util.Date

fun main(){
    println("Hola Mundo")
    // Tipos de variables
    // INMUTABLES (no se reasignan "=")
    val inmutable:String = "Bryan"
    // inmutable = "Steven"

    // MUTABLES (Re asignar)
    var mutable: String = "Steven"
    mutable = "Bryan"

    // val > var

    //Duck tipyng
    var ejemploVariable = " Bryan Manobanda "
    val edadEjemplo: Int = 12

    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo

    // Variables primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo:Double = 1.2
    val estadoCivil: Char  = 'C'
    val mayorEdad: Boolean = true

    // Clases Java
    val fechaNacimiento: Date = Date()

    // SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val coqueteo = if(estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumaUno = Suma(1, 1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // ARREGLOS
    // Tipos de arreglos

    //Arreglo estatico no pued modificar el arreglo
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    //Arreglo dinamico puede aumentar datos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)

    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.
    forEach{ valorActual: Int ->
        println("Valor Actual: ${valorActual}")
    }

    //it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach{println(it)}

    arregloEstatico.
            forEachIndexed{indice:Int, valorActual:Int ->
                println("Valor ${valorActual} Indice: ${indice}")
            }
    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambiar el arreglo)
    // 1) Enviemos el nuevo valor de la iteracion
    // 2) Nos devuelve es un NUEVO ARREGLO con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map {valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{it + 15}

    //FILTER -> Filtrar el arreglo
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo filtrado

    val respuestaFilter: List <Int> = arregloDinamico
        .filter { valorActual: Int ->
            //Expresion Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter {it <= 5}
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    //OR -> ANY (alguno cumple)
    // AND -> ALL (todos cumplen)

    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll:Boolean = arregloDinamico
        .all{valorActual:Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //REDUCE -> Valor acumulado
    // valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    //[1,2,3,4,5] ->  Sume todos los valores del arregl
    //Valor iteracion 1 = valorEmpeeza +1 = 0 + 1 = 1
    //Valor iteracion 2 = valorIteración1 +2 = 1 + 2 = 3
    val respuestaReduce: Int = arregloDinamico
        .reduce{ // acumulado = 0 -> SIEMPRE EMPIEZA EN 0
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // Logica negocio
        }

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(uno: Int, dos:Int){
        //Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(//constructor PRIMARIO
    //Ejemplo
    //uno:Int, // Parametro sin modificador de acceso
    // private var uno:Int, Propiedad Publica Clase numeros.uno
    // var uno: Int, // Propiedad de la clase (por defecto es Public)
    //public var uno: Int,
    //Propiedad de la clase ´protected numeros.numeroUno
    protected val numeroUno: Int,
    //Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
){
    //var cedula: String = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos //this es opcional
        numeroUno; numeroDos //sin el this es lo mismo
        println("Inicializando")
    }
}

class Suma(//Constructor Primario Suma
    uno:Int, // Parametro
    dos: Int //Parametro
): Numeros(uno,dos){ // constructor del padre
    init { //bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( // Segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ): this( //llamada constructor primario
        if(uno == null) 0 else uno, dos
    ){ // si necesita bloque de codigo se usa
        numeroUno;
    }

    constructor( // tercer constructor
        uno:Int, //parametros
        dos:Int? //parametros
    ): this( //llamada constructor primario
        uno,
        if(dos == null) 0 else uno
    ) // si no necesitamos el bloque de codigo "{}" lo omitimos

    constructor( // cuarto constructor
        uno:Int?, //parametros
        dos:Int? //parametros
    ): this( //llamada constructor primario
        if(uno == null) 0 else uno,
        if(dos == null) 0 else uno
    ) // si no necesitamos el bloque de codigo "{}" lo omitimos


    public fun sumar(): Int { //public por defecto, private o protected
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object { //objeto single para todas las clases
        //Atributo y métodos compartidos
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num:Int): Int{
            return num*num
        }

        val historialSumas = arrayListOf<Int>()

        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}



// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    //template strings
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)

    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa) + bonoEspecial
    }
}}