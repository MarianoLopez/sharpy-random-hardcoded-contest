package com.z

import com.fasterxml.jackson.core.JsonFactory
import com.fasterxml.jackson.core.JsonToken
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.system.measureTimeMillis


val foundPairs: MutableList<Pair<Int,Int>> = mutableListOf()
val inMemoryIndex:MutableMap<Int,Int> = mutableMapOf()
private var givenSum: Int = 0
private var arraySize:Int = 0


fun main(args: Array<String>) {
    val time = measureTimeMillis{
        //readTest(args.first())
        //withCustomParser(args.first())
        newWay(args.first())
    }
    println("CustomParser Completed in $time ms")
}
private fun newWay(path: String){
    val stream = Files.lines(Paths.get(path)).parallel().mapToInt { it.toInt() }.sorted().toArray()
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

fun readTest(path:String){
    val sb = StringBuilder()
    val list = mutableListOf<Int>()
    val size = 1024
    val f = FileInputStream(path)
    val fc = f.channel
    val mb = fc.map( FileChannel.MapMode.READ_ONLY, 0L, fc.size( ) )
    val barray = ByteArray(size)
    var nGet = 0
    var checkSum = 0L
    while (mb.hasRemaining()) {
        nGet = Math.min(mb.remaining(), size)
        mb.get(barray, 0, nGet)
        list.addAll(String(barray).replace("\n","").replace("\"","").trim() .split(",").map { it.toInt() })
        //sb.append(String(barray, Charset.defaultCharset()))
    }
    //File("Z:\\Documents\\copy.js").writeText(sb.toString())
    //println(sb.toString())
    println("size ${list.size}")
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
