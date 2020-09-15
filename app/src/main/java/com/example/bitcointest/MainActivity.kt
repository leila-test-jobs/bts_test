package com.example.bitcointest

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.bitcointest.adapter.NetworkAdapter
import com.example.bitcointest.databinding.ActivityMainBinding
import com.example.bitcointest.model.Network
import com.example.bitcointest.viewmodel.MainViewModel


class MainActivity : AppCompatActivity(), NetworkAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: NetworkAdapter

    private val PACKAGE_NAME = "com.ramzinex.ramzinex"
    private val BAZZAR_PACKAGE_NAME = "com.farsitel.bazaar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        initViewModel()
        adapter = NetworkAdapter(viewModel, this)
        val list = viewModel.getFromDatabase()
        if (list != null) {
            adapter.submitList(list)
            binding.networkBtnRecycle.adapter = adapter
        }
        initObservers()

        sendToCafeBazaar()
    }

    private fun sendToCafeBazaar() {
        val alertDialogBuilder = AlertDialog.Builder(this).apply {
            setTitle("دادن نظر در کافه بازار")
            setMessage("لطفا در بازار به ما ستاره بدهید یا با نظرات خود به ما در توسعه نرم افزار کمک کنید.")
            setPositiveButton("دادن ستاره", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Intent.ACTION_EDIT)
                intent.data = Uri.parse("bazaar://details?id=$PACKAGE_NAME")
                intent.setPackage(BAZZAR_PACKAGE_NAME)

                val checkForInstall = try {
                    packageManager.getPackageInfo(BAZZAR_PACKAGE_NAME, 0)
                    true
                } catch (e: PackageManager.NameNotFoundException) {
                    false
                }

                if (checkForInstall) {
                    //app is installed, do smth
                    startActivity(intent)
                } else {
                    val webpage: Uri = Uri.parse("https://cafebazaar.ir/app/com.ramzinex.ramzinex")
                    val i = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(i)
                }


            })
            setNeutralButton("بستن", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            setCancelable(false)
        }
        alertDialogBuilder.show()
    }

    private fun initObservers() {
        viewModel.getCurrency().observe(this, Observer { bts ->
            binding.currency = bts
            Glide.with(this).load("https://ramzinex.com/${bts.icon}").centerCrop()
                .into(binding.currencyImg)
        })

        viewModel.getNetworkList().observe(this, Observer { networks ->
            if (networks != null) {
                adapter.submitList(networks)
                binding.networkBtnRecycle.adapter = adapter
                viewModel.saveInDatabase(networks)
            }
        })

        viewModel.getDescription(0).observe(this, Observer {
            binding.loader.visibility = View.GONE
            var str = ""
            for (items in it) {
                str += items.description + "\n"
            }
            binding.desTxt.text = str
        })

//    viewModel.networkClickListener.observe(this, Observer { network ->
//        if (network != null) {
//            //adapter.changeColor()
//            //progress bar
//            binding.desTxt.text = null
//            binding.loader.visibility = View.VISIBLE
//            viewModel.getDescription(network.has_tag).observe(this, Observer {
//                binding.loader.visibility = View.GONE
//                var str = ""
//                for (items in it) {
//                    str += items.description + "\n"
//                }
//                binding.desTxt.text = str
//            })
//            viewModel.onClickFinished()
//        }
//    })
    }

    private fun initViewModel() {
        val application = requireNotNull(value = this).application
        viewModel =
            ViewModelProvider.AndroidViewModelFactory(application).create(MainViewModel::class.java)
    }

    override fun onItemClick(position: Int, network: Network) {
        //val view = adapter.getItemViewType(position)

        binding.desTxt.text = null
        binding.loader.visibility = View.VISIBLE
        viewModel.getDescription(network.has_tag).observe(this, Observer {
            binding.loader.visibility = View.GONE
            var str = ""
            for (items in it) {
                str += items.description + "\n"
            }
            binding.desTxt.text = str
        })
    }

}