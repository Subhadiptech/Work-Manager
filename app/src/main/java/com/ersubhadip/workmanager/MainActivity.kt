package com.ersubhadip.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ersubhadip.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.workBtn.setOnClickListener {
            work()
        }


    }

    fun work(){
        val instance = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder().setRequiresCharging(true).build()  //Work manager with Constraints

        val oneTimeWorkRequest:OneTimeWorkRequest=OneTimeWorkRequest.Builder(WorkerClass::class.java)
            .setConstraints(constraints)
            .build()

        instance.enqueue(oneTimeWorkRequest)

        instance.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
            binding.statusTv.text = it.state.name
        })

    }
}