package com.z

import java.io.File
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val time = measureTimeMillis {
        val k = args[0].toInt()
        val array = fileToStream(args[1])
        measureTimeMillis { array.sort() }.apply { println("Sort Completed in $this ms") }
        solve(array = array,k = k)
    }
    println("Exec Completed in $time ms")
}

private fun fileToStream(path:String): IntArray {
    var array = IntArray(0)
    val time = measureTimeMillis {
        array = File(path)
                .toByteArray()
                .toIntStream()
                .toArray()
    }
    println("File read Completed in $time ms")
    return array

}

private fun solve(array:IntArray,k:Int){
    val time = measureTimeMillis {
        val pairs = mutableSetOf<Pair<Int, Int>>()
        var top = 0
        var bottom = array.size - 1
        var sum: Int
        while (top < bottom) {
            sum = array[top] + array[bottom]
            when {
                sum == k -> {
                    pairs.add(array[top] to array[bottom])
                    top++
                    bottom--
                }
                sum < k -> top++
                else -> bottom--
            }
        }
        println("stream size: ${array.size}")
        println(pairs)
        println("pairs: ${pairs.size}")
    }
    println("Solve Completed in $time ms")
}

