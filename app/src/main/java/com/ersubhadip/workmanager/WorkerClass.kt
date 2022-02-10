package com.ersubhadip.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class WorkerClass(context: Context,params:WorkerParameters):Worker(context,params) {

    companion object{
        const val OUTPUT_KEY = "key_output"
    }
    override fun doWork(): Result {

        try {

            val stop = inputData.getInt(MainActivity.INPUT_KEY,0)

            for (i in 0 until stop){

                Log.i("MY_TAG","Success")
            }

            val format = SimpleDateFormat("dd/mm/yyyy hh:mm:ss")

            val data = format.format(Date())
            val output = Data.Builder().putString(OUTPUT_KEY,data).build() //sending work output in given format

            return Result.success(output) //sending data


        }catch (e:Exception){

            return Result.failure()
        }

    }


}