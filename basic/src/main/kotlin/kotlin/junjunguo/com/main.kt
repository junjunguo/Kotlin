import java.util.*

fun main(args: Array<String>) {
    myPrint(sum(1, 2))
    println("random " + random())

    val app = Apple(9, "red")
    println(app.string())

    app.add(99)
    println(app.string())

    app.subtract(100)
    app.color = "Green"
    println(app.string())

    println("Number " + app.getNumber())
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun myPrint(n: Int) {
    println("my print: $n")
}

fun random(): Double {
    val random = Random()
    var r = random.nextInt()
    r++
    return Math.PI * r
}

class Apple {

    private var number: Int

    var color: String

    constructor(number: Int, color: String) {
        this.number = number
        this.color = color
    }

    constructor() {
        this.number = 0
        this.color = ""
    }

    fun add(number: Int): Int {
        this.number += number
        return this.number
    }

    fun subtract(number: Int): Int {
        this.number -= number
        return this.number
    }

    fun string(): String {
        return "number: ${this.number}; color: ${this.color}"
    }

    fun getNumber(): Int = this.number
}