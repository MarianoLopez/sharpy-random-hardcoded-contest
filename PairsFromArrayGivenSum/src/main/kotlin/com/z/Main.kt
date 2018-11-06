package com.z

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.util.*
import kotlin.system.measureTimeMillis


val pairs: MutableList<String> = Collections.synchronizedList(mutableListOf<String>())

fun main(args: Array<String>) {
    val time = measureTimeMillis{
        val (array,sum) = getInputStream(args[0])
        println("array size: ${array.size} - sum: $sum")
        process(array, sum)
        println("pairs size: ${pairs.size} -> $pairs")
    }
    println("Completed in $time ms")
}

private fun process(array:List<Int>, sum:Int){
    val map:MutableMap<Int,Int> = Collections.synchronizedMap(mutableMapOf())
    array.distinct().stream().parallel().forEach {
        if(map.containsKey(sum-it)){
            pairs.add("(${sum-it},$it)")
        }else{
            map[it] = it
        }
    }
}

private fun getInputStream(path:String) = jacksonObjectMapper().apply { configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) }.readValue<Input>(File(path))

data class Input(val array:List<Int>, val sum:Int)
