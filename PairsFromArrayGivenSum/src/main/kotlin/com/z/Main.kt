package com.z

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import java.io.BufferedInputStream
import java.io.FileInputStream
import kotlin.system.measureTimeMillis


val foundPairs: MutableList<Pair<Int,Int>> = mutableListOf()
val inMemoryIndex:MutableMap<Int,Int> = mutableMapOf()
private var givenSum: Int = 0
private var arraySize:Int = 0


fun main(args: Array<String>) {
    val time = measureTimeMillis{
        withCustomParser(args.first())
    }
    println("CustomParser Completed in $time ms")
}
private fun withCustomParser(path:String){
    val factory = JsonFactory()
    factory.createParser(BufferedInputStream(FileInputStream(path))).use {
        while(it.nextToken() != JsonToken.END_OBJECT){
            when(it.currentName){
                "sum" -> {
                    it.nextToken()
                    givenSum = it.intValue
                }
                "array"->{
                    it.nextToken()
                    while(it.nextToken()!=JsonToken.END_ARRAY){
                        arraySize++
                        val value = it.valueAsInt
                        if(!inMemoryIndex.containsKey(value)|| givenSum/2==value){
                            if(inMemoryIndex.containsKey(givenSum-value)){
                                (givenSum-value to value).apply { if(!foundPairs.contains(this))foundPairs.add(this) }
                            }else{
                                inMemoryIndex[value] = value
                            }
                        }
                    }
                }
            }
        }
    }
    println("array size: $arraySize - givenSum: $givenSum")
    println("foundPairs size: ${foundPairs.size} -> $foundPairs")
}


/*private fun withoutParser(path: String){
    val (array,sum) = getInputStream(path)
    println("array size: ${array.size} - givenSum: $sum")
    process(array, sum)
    println("foundPairs size: ${foundPairs.size} -> $foundPairs")
}

private fun process(array:List<Int>, sum:Int){
    val map:MutableMap<Int,Int> = mutableMapOf()
    array.forEach {
        if(map.containsKey(sum-it)){
            foundPairs.add(givenSum-it to it)
        }else{
            map[it] = it
        }
    }
}

private fun getInputStream(path:String) = jacksonObjectMapper().apply { configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true) }.readValue<Input>(File(path))

data class Input(val array:List<Int>, val sum:Int)*/
