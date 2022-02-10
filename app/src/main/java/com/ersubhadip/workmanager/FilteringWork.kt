package com.ersubhadip.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*


class FilteringWork(context: Context, params: WorkerParameters): Worker(context,params) {


    override fun doWork(): Result {

        try {

            for (i in 0 until 200){

                Log.i("MY_TAG","Filtering $i")
            }


            return Result.success()


        }catch (e:Exception){

            return Result.failure()
        }

    }


}