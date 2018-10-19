package me.tatocaster.nasaappchallenge.common.utils

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {
    private val TAG = "FileUtil"

    private val isSDAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getFolderName(name: String): String {
        val mediaStorageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                name)

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return ""
            }
        }
        return mediaStorageDir.absolutePath
    }

    fun getNewFile(context: Context, folderName: String): File? {

        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)

        val timeStamp = simpleDateFormat.format(Date())

        val path: String
        path = if (isSDAvailable) {
            getFolderName(folderName) + File.separator + timeStamp + ".jpg"
        } else {
            context.filesDir.path + File.separator + timeStamp + ".jpg"
        }

        return if (TextUtils.isEmpty(path)) {
            null
        } else File(path)

    }
}