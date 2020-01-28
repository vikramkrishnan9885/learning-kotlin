package hello

import nl.komponents.kovenant.functional.bind
import nl.komponents.kovenant.functional.map
import nl.komponents.kovenant.task
import org.funktionale.option.Option
import org.funktionale.option.optionSequential
import org.funktionale.option.toOption

// SEE PACKAGES

fun main(args:Array<String>) {
    // LAMBDA
    var greetVar0 : (String) -> String = {name:String -> "Hello "+ name}
    println(greetVar0("Krishnan"))
    // Aliter
    var greetVar1 = {name: String -> "Hello "+name}
    println(greetVar1("Vikram"))

    // TYPE HEIRARCHY
    println("====================================================")
    val str0 = "Hello"
    println(str0 is String)
    println(str0 is Any)

    // NULLABLE TYPE
    println("==================================================")
    var num: Int? = null
    println(num)

    var str1: String? = "Hello"
    // You can either use a safe call with ?., or ignore the danger with !!
    println(str1?.length)
    println(str1!!.length)

    str1 = null
    println(str1?.length)
    //println(str1!!.length)

    // Another useful thing when working with nullable types is the elvis operator written as ?:
    // <expression_1> ?: <expression_2>
    // This result is determined by the <expression_1>. If it’s not null,
    // this non-null value is returned, otherwise the <expression_2> is returned.
    str1 = "Hello"
    println(str1 ?: "Hi")

    str1 = null
    println(str1 ?: "Hi")

    // COLLECTIONS
    println("==================================================")
    val lst0 = listOf<Int>(1,2,3)
    println(lst0)

    val arr = arrayOf(1,2,3)
    println(arr)
    println(arr[0]) // Note brackets are similar to Java and not Scala
    println(arr.get(0)) // Note the brackets

    val lst1 = mutableListOf<Int>(1,2,3)
    lst1.add(4)
    println(lst1)

    println("==================================================")
    val person0 = Person("Joe") // NO NEW KEYWORD
    println(person0)
    println(person0.name)
    println(person0.sayHello())
    println(Person.random100())

    // OBJECTS
    println("==================================================")
    println(RandomUtils.random100())
    // FOR STATIC EQ. COMPANION OBJECT IS USED SEE BELOW

    // GENERICS
    println("==================================================")
    val cell0 = Cell<Int>(2)
    println(cell0.contents)

    val cell1 = Cell(3)
    println(cell1.contents)

    fun <T> concat(a: T, b: T): String = a.toString() + b.toString()
    println(concat(1, 2))

    // EXTENSION FUNCTIONS
    println("==================================================")
    fun String.onlySpaces(): Boolean = this.trim().length == 0
    println("   ".onlySpaces())
    println(" 3".onlySpaces())

    // CONDITIONALS
    println("==================================================")
    // Unlike Java with if statements, in Kotlin if expressions always result in a value.
    // In this respect they are similar to Java’s ternary operator ?:
    val isEven0 = { num: Int -> if (num % 2 == 0) true else false }
    println(isEven0)

    // But what if branch types are different, for example Int and Double?
    // In this case the nearest common supertype will be chosen.
    // For Int and Double this supertype will be Any, so Any will be the type of the whole expression
    val isEven1 = { num: Int -> if (num % 2 == 0) 1 else 1.2 }
    println(isEven1)

    // LOOPS
    println("==================================================")
    for (i in 5 downTo 1) println(i) // Includes 1
    println("---------------------------------------------------")
    for (i in 1 until 5) println(i) // Excludes 5

    // STRING INTERPOLATION
    println("==================================================")
    val name = "Joe"
    val greeting = "Hello $name"
    println(greeting)

    println("Random number is ${Math.random()}")

    val s0 = """First line\nStill first line"""
    println(s0)

    val s1 = """
        First line
        Second line
    """.trimIndent()
    println(s1)

    // INTERFACES
    println("==================================================")
    class C: A, B { override fun b(): Unit = println("b") }
    // If we don’t implement b, we will have to make the C class abstract or get an error.
    val c = C() // you need the parantheses
    c.a()
    c.b()

    // MAP, FILTER, FOREACH, FLATMAP
    println("==================================================")
    val list = listOf(1, 2, 3, 4)
    list.map { el -> el * el }.forEach { i -> println(i) }
    list.filter { el -> el % 2 == 0 }.forEach { i -> println(i) }

    val list2 = listOf("a", "b")
    list.map { el1 ->
        list2.map { el2 ->
            "$el1$el2"
        }
    }.forEach { i -> println(i) }

    list.flatMap { el1 ->
        list2.map { el2 ->
            "$el1$el2"
        }
    }.forEach { i -> println(i) }

    // FOLDING
    println("==================================================")
    println(list.fold(0,{acc,next-> acc + next }))
    // One interesting feature of Kotlin is that whenever a lambda happens to be the
    // last arguments of the function, it can be moved out of parentheses:
    println(list.fold(0){acc,next-> acc + next })

    // DATA CLASSES
    println("==================================================")
    data class Person(val firstName: String, val lastName: String,val birthYear: Int)
    val person = Person("Joe", "Black", 1990)
    println(person)
    val p1= person.copy(lastName = "Smith")
    println(p1)
    println(person)
    //The componentN methods allow to access object properties without referring to their names
    println(person.component1())
    val (first, last, year) = person
    println(last)

    // LAZINESS
    println("==================================================")
    fun logEagerly(enabled:Boolean,message:String):Unit {
        if(enabled) {
            println(message)
        }
    }
    val isEnabled = true
    val userId: () -> String = {"1"}
    logEagerly(isEnabled, "User ${userId()} logged out from the system")

    fun logLazily(enabled:Boolean,message:() -> String):Unit {
        if(enabled) {
            println(message())
        }
    }
    // NOTE THE BRACES ON THE SECOND ARG. IT IS A FUNCTION NOT A STRING
    logLazily(isEnabled, {"User ${userId()} logged out from the system" })

    // OPTION
    // There is no Option in the Kotlin standard library,
    // but it can be found in a library called “funKTionale”18 written by Mario Arias.
    fun generateNumber(): Int? {
        val num = Math.round(Math.random()*100)
        return if (num <- 90) num.toInt() else null
    }

    val maybeNum1 =  generateNumber().toOption()
    val maybeNum2 = generateNumber().toOption()

    val maybeSum  = maybeNum1.flatMap { num1 ->
        maybeNum2.map {
            num2 -> num1 + num2
        }
    }
    println(maybeSum)

    val maybeNum3 = generateNumber().toOption()
    val seq = listOf<Option<Int>>(maybeNum1, maybeNum2, maybeNum3)
    val maybeListSum = seq.optionSequential().map { list -> list.fold(0){acc, next -> acc+next} }
    println(maybeListSum)

    // PROMISES
    fun queryNextNumber(): Long {
        Thread.sleep(2000)
        val source =  Math.round(Math.random()*100)
        if (source <= 60) return source
        else throw Exception("Generated number is too big")
    }

    val num1P = task {queryNextNumber()}
    val num2P = task {queryNextNumber()}
    val sumP0 = num1P.success { num1 ->
        num2P.success { num2 ->
            println(num1 + num2)
        }
    }
    println(sumP0)

    val sumP1 =  num1P.bind {num1 ->
        num2P.map { num2 -> num1 + num2 }
    }
    println(sumP1)
}

// OBJECTS
// Objects cannot be declared in a function (i.e. local)
// Eq. to static like Scala
object RandomUtils {
    fun random100() =  Math.round(Math.random()*100)
}

// CLASSES
class Person(val name: String) {
    override fun toString(): String {
        return "hello.Person("+this.name+")"
    }
    fun sayHello():String = "Hi! I'm " + name

    // IF YOU ADD C.O. THEN YOU HAVE TO MOVE IT OUT OF THE FUNCTION
    // NO LOCAL OBJECTS
    companion object {
        fun random100() = Math.round(Math.random() * 100)
    }
}

// GENERICS
class Cell<T>(val contents:T) {
    fun get():T = contents
}

// INTERFACES
interface A {fun a():Unit=println("a")}
interface B {fun b():Unit}