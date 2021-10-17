package com.example.ktoradapter

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktoradapter.databinding.ActivityMainBinding
import com.example.ktoradapter.services.PetServices
import com.example.ktoradapter.viewmodel.DogsAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import com.example.ktoradapter.common.MakeToast;
import com.example.ktoradapter.model.Pets
import kotlinx.coroutines.*
import java.util.*


class MainActivity : AppCompatActivity(), androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: DogsAdapter
    private var dogImages = mutableListOf<String>()
    private var listDogs = mutableListOf<String>()
    private lateinit var textView: TextView;

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)

        this.supportActionBar?.apply{
            hide()

        }

        binding.search.setOnQueryTextListener( this )
        initRecyclerView()
        getDogs().invokeOnCompletion {
           println(it?.localizedMessage)
            runOnUiThread {
                val list = listOf("sads", "asdas", "asdas")
                binding.planetsSpinner.visibility = View.VISIBLE;
                val arrayAdapter = ArrayAdapter(this, R.layout.item_spinner, listDogs)
                binding.planetsSpinner.adapter = arrayAdapter

                binding.planetsSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                        println("selected")
                        println()
                        runBlocking {
                            findByName(listDogs[position])
                        }
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        println("nada")
                        TODO("Not yet implemented")
                    }

                }
            }

        }






    }

    private fun initRecyclerView(){
        adapter = DogsAdapter(dogImages)
        binding.listDogs.layoutManager = LinearLayoutManager(this)
        binding.listDogs.adapter = adapter

    }

    private fun getRetrofit(plural: String = "breed"): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/$plural/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun findByName(query: String) {
        CoroutineScope(Dispatchers.IO).async {
            val callDogs = getRetrofit("breed")
                .create(PetServices::class.java)
                .getDogsByName("$query/images")
            val puppies = callDogs.body()
            println(puppies)
            runOnUiThread {
                if (callDogs.isSuccessful) {
                    val images = puppies?.messages ?: emptyList<String>()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else {
                    MakeToast("Ha ocurrido un error al carga perros")
                }
                hideKeyboard()
            }

        }
    }

    private  fun getDogs(): Deferred<Unit> {
      val token =  CoroutineScope(Dispatchers.IO).async  {
          val callDogs = getRetrofit("breeds")
                .create(PetServices::class.java)
                .getDogs("list/all")

            val response = callDogs.body()

            if (callDogs.isSuccessful) {
                runOnUiThread {
                    println(response?.message?.keys)
                    runBlocking {
                        val callsDogsd =  getRetrofit("breeds")
                                         .create(PetServices::class.java)
                        var responsePets: Pets
                        var listPets = listOf<Pets>()
                        try {
                            for(images in response?.message?.keys!!) {
                                listDogs.add(images)
                            }
                        }catch (error:Exception) {

                        }
                    }

                }
            }


        }
        return token
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        if (!p0.isNullOrEmpty()) {
            val search = p0.lowercase(Locale.getDefault()) ?: ""
            println(search)
            findByName(search)
            binding.search.onActionViewCollapsed()
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}


