package com.z

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File
import java.io.OutputStream
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val time =  measureTimeMillis {
         val top = 100000000
         val input = Input(mutableListOf(), 1000)
         for (i in 1..top) {
             input.array.add((0..1000).random())
             if(i%100==0) println("%.4f".format(i*100.0/top)+"%")
         }
         jacksonObjectMapper().writeValue(File("Z:\\Documents\\randomInput.json"), input)
     }
    println("Completed in $time ms")
}
data class Input(val array:MutableList<Int>, val sum:Int)
fun IntRange.random() = Random.nextInt(start, endInclusive + 1)

