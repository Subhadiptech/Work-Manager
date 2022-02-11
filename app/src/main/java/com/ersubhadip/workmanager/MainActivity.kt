package com.ersubhadip.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import androidx.work.*
import com.ersubhadip.workmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    companion object {
        const val INPUT_KEY = "key"
    }

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.workBtn.setOnClickListener {
//            work()   --> OneTimeWork
            setPeriodicWorkReq()
        }


    }

    private fun work() {
        val instance = WorkManager.getInstance(applicationContext)
        val constraints =
            Constraints.Builder().setRequiresCharging(true).build()  //Work manager with Constraints

        val input =
            Data.Builder().putInt(INPUT_KEY, 200).build()        //Work manager with input data


        val oneTimeWorkRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(WorkerClass::class.java)
                .setConstraints(constraints)
                .setInputData(input)
                .build()

        val filtering = OneTimeWorkRequest.Builder(FilteringWork::class.java).build()
        val compressing = OneTimeWorkRequest.Builder(CompressingWork::class.java).build()
        val download = OneTimeWorkRequest.Builder(DownloadParallel::class.java).build()

        //For parallel workers we need to create a mutable List Object
        val parallel = mutableListOf<OneTimeWorkRequest>()
        parallel.add(download)
        parallel.add(filtering)

        //Chaining the Workers
        instance
            .beginWith(parallel)  //for parallel working start
            .then(compressing)
            .then(oneTimeWorkRequest)
            .enqueue()

        instance.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
            binding.statusTv.text = it.state.name

            if (it.state.isFinished) {
                val show =
                    it.outputData.getString(WorkerClass.OUTPUT_KEY)  //getting output String Data
                Toast.makeText(applicationContext, show, LENGTH_LONG).show()
            }
        })

    }

    private fun setPeriodicWorkReq(){
        val periodicWork = PeriodicWorkRequest.Builder(WorkerClass::class.java,16,TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueue(periodicWork)

    }
}