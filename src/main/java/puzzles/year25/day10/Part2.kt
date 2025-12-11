package puzzles.year25.day10

import org.chocosolver.solver.Model
import org.chocosolver.solver.Solver
import util.Puzzle

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val input = inputString.trim().lines().map { it.split(" ") }.map { parse(it) }
        var result = 0
        for (i in input) {
            val matrix = calculateMatrix(i)
            result += linearSolver(matrix, i.second.size, i.first.max(), i.first.min(), (i.first.sum() / 1.5).toInt())
        }

        return result
    }

    fun linearSolver(input: Pair<Array<Array<Int>>, Array<Int>>, buttons: Int, upperBound: Int, solutionLowerBound: Int, solutionUpperBound: Int): Int {
        val model = Model("Model")

        val vars = model.intVarArray("x", buttons, 0, upperBound)

        for (i in input.first.indices) {
            val coefficients = input.first[i].toIntArray()
            val rhs = input.second[i]

            model.scalar(vars, coefficients, "=", rhs).post()
        }

        val objectiveSum = model.intVar("objectiveSum", solutionLowerBound, solutionUpperBound)
        model.sum(vars, "=", objectiveSum).post()

        val solver: Solver = model.solver

        val solution = solver.findOptimalSolution(objectiveSum, false)

        if (solution != null) {
            println(vars.contentToString())

            return solution.getIntVal(objectiveSum)
        } else {
            println("No integer solution found.")
            return 0
        }
    }

    fun parse(input: List<String>): Pair<List<Int>, List<List<Int>>> {
        var jolts: List<Int> = ArrayList()
        val buttons: MutableList<List<Int>> = ArrayList()

        for (s in input) {
            if (s.startsWith("{")) {
                jolts = s.substring(1, s.length - 1).split(",").map { it.toInt() }
            } else if (s.startsWith("(")) {
                buttons.add(s.substring(1, s.length - 1).split(",").map { it.toInt() })
            }
        }

        return Pair(jolts, buttons)
    }

    fun calculateMatrix(input: Pair<List<Int>, List<List<Int>>>): Pair<Array<Array<Int>>, Array<Int>> {
        val buttons = input.second
        val jolts = input.first
        val result = Array(jolts.size) { Array(buttons.size) { 0 } }
        val constants = jolts.toTypedArray()

        // Simple jolt equations
        for (j in jolts.indices) {
            for (b in buttons.indices) {
                if (buttons[b].contains(j)) {
                    result[j][b] = 1
                }
            }
        }

        return Pair(result, constants)
    }
}

// 18231 -- too low