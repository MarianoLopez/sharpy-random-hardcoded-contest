package com.z

import java.io.File
import kotlin.random.Random
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
    when(args.size){
        1 -> writeFile(args.first().toInt())
        2 -> writeFile(args.first().toInt(),args[1].toInt())
        3 -> writeFile(args.first().toInt(),args[1].toInt(),args[2])
    }
//    readFile()
    //readFile2()
}

fun writeFile(top:Int,randomTop:Int=1000,path:String = ".$top"){
    val time =  measureTimeMillis {
        File(path).apply { createNewFile() } .bufferedWriter().use { out->
            for (i in 1..top) {
                out.write((0..1000).random().toString())
                if(i!=top)out.newLine()
                if(i%100==0) println("%.4f".format(i*100.0/top)+"%")
            }
        }

        //jacksonObjectMapper().writeValue(File("Z:\\Documents\\randomInput10M.json"), input)
    }
    println("Completed in $time ms")
}
fun IntRange.random() = Random.nextInt(start, endInclusive + 1)

