package com.example.composeapp1.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.composeapp1.data.MyPhoto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DiskLoader {
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadIntoMemo(bitmap: Bitmap, context: Context) = withContext(
        newSingleThreadContext("Disk")
    ) {
        val time = LocalTime.now()
        val date = LocalDate.now()
        val photo = MyPhoto(
            time.format(DateTimeFormatter.ofPattern("HH:mm")),
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        )

        try {
            // Dir
            val rootPath = context.filesDir.path
            val dir = File(rootPath, "photos/${bitmap.generationId}")
            if (!dir.exists()) dir.mkdir()

            // Files
            val dataFile = File("$rootPath/photos/${bitmap.generationId}", "data")
            val infoFile = File("$rootPath/photos/${bitmap.generationId}", "info")
            dataFile.createNewFile()
            infoFile.createNewFile()

            val dtOut = dataFile.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, dtOut)
            dtOut.close()

            val infOut = infoFile.outputStream()
            val objectOutputStream = ObjectOutputStream(infOut)
            objectOutputStream.writeObject(photo)
            objectOutputStream.close()
            infOut.close()
            return@withContext "Success"
        } catch (e: Exception) {
            return@withContext "Error"
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadFromMemo(context: Context): List<Pair<Bitmap, MyPhoto>> = withContext(
        newSingleThreadContext("Disk")) {
        val list = mutableListOf<Pair<Bitmap, MyPhoto>>()
        try {
            val rootPath = context.filesDir.path
            val dir = File(rootPath, "photos")
            if (!dir.exists()) dir.mkdir()
            dir.listFiles()?.forEach {
                val dataFile = File(it.path, "data")
                val infoFile = File(it.path, "info")
                //

                val dtIn = dataFile.inputStream()
                val btmp = BitmapFactory.decodeStream(dtIn)
                dtIn.close()

                val infIn = infoFile.inputStream()
                val ois = ObjectInputStream(infIn)
                val obj = ois.readObject()
                ois.close()
                infIn.close()

                list.add(Pair(btmp, obj as MyPhoto))
            }

            return@withContext list
        }catch (e: Exception) {
            return@withContext emptyList()
        }
    }

}