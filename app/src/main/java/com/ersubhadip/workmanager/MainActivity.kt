package com.ersubhadip.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.ersubhadip.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    companion object{
        const val INPUT_KEY="key"
    }

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

        val input = Data.Builder().putInt(INPUT_KEY,200).build()        //Work manager with input data


        val oneTimeWorkRequest:OneTimeWorkRequest=OneTimeWorkRequest.Builder(WorkerClass::class.java)
            .setConstraints(constraints)
            .setInputData(input)
            .build()

        instance.enqueue(oneTimeWorkRequest)

        instance.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this, Observer {
            binding.statusTv.text = it.state.name

            if(it.state.isFinished){
                val show = it.outputData.getString(WorkerClass.OUTPUT_KEY)  //getting output String Data
                Toast.makeText(applicationContext,show,LENGTH_LONG).show()
            }
        })

    }
}