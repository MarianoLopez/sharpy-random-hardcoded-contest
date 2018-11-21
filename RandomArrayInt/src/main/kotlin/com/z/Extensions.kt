package com.z

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStreamReader
import java.nio.channels.FileChannel
import java.util.stream.IntStream

fun ByteArray.toIntStream():IntStream = BufferedReader(InputStreamReader(ByteArrayInputStream(this))).lines().parallel().mapToInt { it.toInt() }

fun File.toByteArray(): ByteArray = this.inputStream().use { it.channel.toByteArray() }

fun FileChannel.toByteArray(mode:FileChannel.MapMode = FileChannel.MapMode.READ_ONLY, position:Long = 0, size:Long = this.size()):ByteArray{
    return this.use {
        it.map(mode,position,size).let { buffer->//file to memory
            ByteArray(size.toInt()).apply {
                buffer.get(this)//MappedByteBuffer to ByteArray
            }
        }
    }
}