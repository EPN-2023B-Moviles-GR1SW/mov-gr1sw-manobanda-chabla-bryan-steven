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
}