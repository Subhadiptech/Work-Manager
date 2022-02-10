package com.ersubhadip.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DownloadParallel(context: Context, params:WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {

        return try {

            for (i in 0 until 3000){

                Log.i("MY_TAG","Downloading Data $i")
            }

            Result.success()

        }catch (e:Exception){

            Result.failure()
        }

    }
}