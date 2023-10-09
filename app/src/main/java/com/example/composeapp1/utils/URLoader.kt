package com.example.composeapp1.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object URLoader {

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun loadPhotoByUrl(textFiled: String, context: Context, ret: (bitmap: Bitmap) -> Unit) =
        withContext(newSingleThreadContext("Network")) {
            var iS: InputStream?
            var btmp: Bitmap?
            var responseCode = -1
            try {
                val url = URL(textFiled)
                val con = url.openConnection() as HttpURLConnection
                con.doInput = true
                con.connect()
                responseCode = con.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    iS = con.inputStream
                    btmp = BitmapFactory.decodeStream(iS)
                    withContext(newSingleThreadContext("Disk")) {
                        ret.invoke(btmp)
                    }
                    iS.close()
                    Log.d("URRL", btmp.generationId.toString())
                    return@withContext "Success"
                } else {
                    Log.d("URRL", "err")
                    return@withContext "Error"
                }
            } catch (ex: Exception) {
                Log.e("Exception", ex.toString())
                return@withContext "Error"
            }
        }
}