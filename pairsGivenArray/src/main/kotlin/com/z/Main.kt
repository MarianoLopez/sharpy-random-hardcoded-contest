package com.z

import java.io.File
import java.nio.file.Files
import java.util.*
import java.util.stream.Collectors
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    val time = measureTimeMillis {
        val k = args[0].toInt()
        val pairs = mutableSetOf<Pair<Int,Int>>()
        //val array = Files.readAllBytes(File(args[1]).toPath()).toIntStream().sorted().toArray()
        val array = File(args[1])
                .toByteArray()
                .toIntStream()
                .sorted()
                .toArray()
        var top = 0
        var bottom = array.size-1
        var sum:Int
        while(top<bottom){
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
    println("Completed in $time ms")
}

