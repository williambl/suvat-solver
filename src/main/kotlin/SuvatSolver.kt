import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.exitProcess

val equations: Map<Pair<SUVATVariable, SUVATVariable>, (Double, Double, Double) -> Double> = mapOf(
    Pair(Pair(SUVATVariable.S, SUVATVariable.U), {v, a, t -> v*t -(1/2)*a*(t.pow(2))}),
    Pair(Pair(SUVATVariable.S, SUVATVariable.V), {u, a, t -> u*t + (1/2)*a*(t.pow(2))}),
    Pair(Pair(SUVATVariable.S, SUVATVariable.A), {u, v, t -> ((u+v)/2)*t}),
    Pair(Pair(SUVATVariable.S, SUVATVariable.T), {u, v, a -> (v.pow(2)-u.pow(2))/(2*a)}),

    Pair(Pair(SUVATVariable.U, SUVATVariable.S), {v, a, t -> -1*(a*t-v)}),
    Pair(Pair(SUVATVariable.U, SUVATVariable.V), {s, a, t -> (-1/2)*a*t+(s/t)}),
    Pair(Pair(SUVATVariable.U, SUVATVariable.A), {s, v, t -> ((2*s)/t) -v}),
    Pair(Pair(SUVATVariable.U, SUVATVariable.T), {s, v, a -> sqrt(v.pow(2)-2*a*s)}),

    Pair(Pair(SUVATVariable.V, SUVATVariable.S), {u, a, t -> u + a*t}),
    Pair(Pair(SUVATVariable.V, SUVATVariable.U), {s, a, t -> (s/t)-(1/2)*a*t}),
    Pair(Pair(SUVATVariable.V, SUVATVariable.A), {s, u, t -> ((2*s)/t)-u}),
    Pair(Pair(SUVATVariable.V, SUVATVariable.T), {s, u, a -> sqrt(u.pow(2) + 2*a*s)}),

    Pair(Pair(SUVATVariable.A, SUVATVariable.S), {u, v, t -> (v-u)/t}),
    Pair(Pair(SUVATVariable.A, SUVATVariable.U), {s, v, t -> (s-v*t)/(-0.5*t.pow(2))}),
    Pair(Pair(SUVATVariable.A, SUVATVariable.V), {s, u, t -> (s-u*t)/(0.5*t.pow(2))}),
    Pair(Pair(SUVATVariable.A, SUVATVariable.T), {s, u, v -> (v.pow(2)-u.pow(2))/(2*s)}),

    Pair(Pair(SUVATVariable.T, SUVATVariable.S), {u, v, a -> (v-u)/a}),
    Pair(Pair(SUVATVariable.T, SUVATVariable.U), {s, v, a -> (v-sqrt(v.pow(2)-2*a*s))/a}),
    Pair(Pair(SUVATVariable.T, SUVATVariable.V), {s, u, a -> (u+sqrt(u.pow(2)+2*a*s))/a}),
    Pair(Pair(SUVATVariable.T, SUVATVariable.A), {s, u, v -> s/((u+v)/2)})
)


fun main(args: Array<String>) {

    val suvat: MutableList<Double?> = MutableList(5) { null }
    lateinit var missingVariable: SUVATVariable
    lateinit var variableToFind: SUVATVariable

    if (args.size < 5) {
        println("Wrong number of arguments!")
        exitProcess(1)
    }

    for (i in 0 until 5) {
        suvat[i] = args[i].toDoubleOrNull()
    }

    if (suvat.count { it == null } > 2) {
        println("Wrong number of missing variables!")
        exitProcess(1)
    }
    val suvatNotNull = suvat.filterNotNull()

    for (i in 0 until 5) {
        if (args[i] == "m") {
            missingVariable = SUVATVariable.values()[i]
        }
        if (args[i] == "f") {
            variableToFind = SUVATVariable.values()[i]
        }
    }

    val result = equations[Pair(variableToFind, missingVariable)]?.invoke(suvatNotNull[0], suvatNotNull[1], suvatNotNull[2])
    println("${variableToFind.name}: $result")
}

enum class SUVATVariable {
    S,
    U,
    V,
    A,
    T;
}