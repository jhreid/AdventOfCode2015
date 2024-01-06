package day07

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val input = Path("input/day07.txt").readLines()

    var instructions = mutableListOf<Instruction>()

    val wires = mutableMapOf<String, Int>()

    val straight = """\S+ -> \w+""".toRegex()

    input.forEach { line ->
        when {
            straight.matches(line) -> {
                val (wire, signal) = line.split(" -> ")
                instructions.add(Instruction("", signal, wire, "ASSIGN", false))
            }
            line.contains("RSHIFT") -> {
                val (left, dest) = line.split(" -> ")
                val (source, amount) = left.split(" RSHIFT ")
                instructions.add(Instruction(source, dest, amount, "RSHIFT", false))
            }
            line.contains("LSHIFT") -> {
                val (left, dest) = line.split(" -> ")
                val (source, amount) = left.split(" LSHIFT ")
                instructions.add(Instruction(source, dest, amount, "LSHIFT", false))
            }
            line.startsWith("NOT") -> {
                val (source, dest) = line.removePrefix("NOT ").split(" -> ")
                instructions.add(Instruction(source, dest, "", "NOT", false))
            }
            line.contains("AND") -> {
                val (left, dest) = line.split(" -> ")
                val (wire1, wire2) = left.split(" AND ")
                instructions.add(Instruction(wire1, dest, wire2, "AND", false))
            }
            line.contains("OR") -> {
                val (left, dest) = line.split(" -> ")
                val (wire1, wire2) = left.split(" OR ")
                instructions.add(Instruction(wire1, dest, wire2, "OR", false))
            }
        }
    }

    runSimulation(instructions, wires)

    println(wires["a"]!!)

    val wires2 = mutableMapOf<String, Int>()
    val oldInstruction = instructions.filter { it.operation == "ASSIGN" && it.dest == "b" }.first()
    instructions.remove(oldInstruction)
    instructions.add(Instruction("", "b", wires["a"]!!.toString(), "ASSIGN", false))
    instructions.forEach { it.done = false }

    runSimulation(instructions, wires2)

    println(wires2["a"]!!)
}

private fun runSimulation(
    instructions: List<Instruction>,
    wires: MutableMap<String, Int>
) {
    var queue = instructions
    while (queue.isNotEmpty()) {
        queue.forEach { instruction ->
            when {
                instruction.operation == "ASSIGN" -> if (instruction.amount.toIntOrNull() != null) {
                    wires.put(instruction.dest, instruction.amount.toInt())
                    instruction.done = true
                } else if (wires.containsKey(instruction.amount)) {
                    wires.put(instruction.dest, wires[instruction.amount]!!)
                    instruction.done = true
                }

                instruction.operation == "RSHIFT" -> if (wires.containsKey(instruction.source)) {
                    wires.put(instruction.dest, wires[instruction.source]!! shr instruction.amount.toInt())
                    instruction.done = true
                }

                instruction.operation == "LSHIFT" -> if (wires.containsKey(instruction.source)) {
                    wires.put(instruction.dest, wires[instruction.source]!! shl instruction.amount.toInt())
                    instruction.done = true
                }

                instruction.operation == "NOT" -> if (wires.containsKey(instruction.source)) {
                    wires.put(instruction.dest, wires[instruction.source]!!.inv())
                    instruction.done = true
                }

                instruction.operation == "AND" -> if (instruction.source.toIntOrNull() != null && wires.containsKey(
                        instruction.amount
                    )
                ) {
                    wires.put(instruction.dest, instruction.source.toInt() and wires[instruction.amount]!!)
                    instruction.done = true
                } else if (instruction.amount.toIntOrNull() != null && wires.containsKey(instruction.source)) {
                    wires.put(instruction.dest, instruction.amount.toInt() and wires[instruction.source]!!)
                    instruction.done = true
                } else if (wires.containsKey(instruction.source) && wires.containsKey(instruction.amount)) {
                    wires.put(instruction.dest, wires[instruction.amount]!! and wires[instruction.source]!!)
                    instruction.done = true
                }

                instruction.operation == "OR" -> if (instruction.source.toIntOrNull() != null && wires.containsKey(
                        instruction.amount
                    )
                ) {
                    wires.put(instruction.dest, instruction.source.toInt() or wires[instruction.amount]!!)
                    instruction.done = true
                } else if (instruction.amount.toIntOrNull() != null && wires.containsKey(instruction.source)) {
                    wires.put(instruction.dest, instruction.amount.toInt() or wires[instruction.source]!!)
                    instruction.done = true
                } else if (wires.containsKey(instruction.source) && wires.containsKey(instruction.amount)) {
                    wires.put(instruction.dest, wires[instruction.amount]!! or wires[instruction.source]!!)
                    instruction.done = true
                }
            }
        }
        queue = queue.filter { !it.done }.toMutableList()
    }
}

data class Instruction(val source: String, val dest: String, val amount: String, val operation: String, var done: Boolean)