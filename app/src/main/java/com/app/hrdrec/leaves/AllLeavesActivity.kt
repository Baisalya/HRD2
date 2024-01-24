package com.app.hrdrec.leaves

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityAllLeavesBinding
import com.app.hrdrec.leaves.response.LeaveModel
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllLeavesActivity : AppCompatActivity(), CommonInterface,
    AllLeaveListAdapter.FeatureCallBack, View.OnClickListener {

    private val viewModel: LeavesViewModel by viewModels()
    private var productFeatureAdapter: AllLeaveListAdapter? = null

    private val binding by lazy { ActivityAllLeavesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.setCallBacks(this);
        setObserver()
        binding.headerMain.txtTitle.text = "Leaves"
        viewModel.getAllLeaves()

        setUpListerner()

    }

    private fun setUpListerner() {
        binding.apply {
            addLeaves.setOnClickListener(this@AllLeavesActivity)
            headerMain.imgBackBtn.setOnClickListener(this@AllLeavesActivity)
            conFromDate.setOnClickListener(this@AllLeavesActivity)
            conToDate.setOnClickListener(this@AllLeavesActivity)
            btnSubmit.setOnClickListener(this@AllLeavesActivity)

        }

    }

    private fun setObserver() {


        viewModel.myResponseList.observe(this) {
                Log.e("Data", "herrrr" + it.data.size)

            binding.recyclerLeaves.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.GONE
                productFeatureAdapter = AllLeaveListAdapter(it.data, this)
                binding.recyclerLeaves.adapter = productFeatureAdapter
            }

    }
    override fun onClick(v: View) {
        when (v.id) {


            R.id.addLeaves -> {

                val intent = Intent(this, AddLeaveActivity::class.java)
                intent.putExtra("from","add")
                //startActivity(intent)
                resultLauncher.launch(intent)
            }

            R.id.img_back_btn->{
                finish()
            }

            R.id.conFromDate -> {

                val datePicker = CommonMethods.selectAnyDate(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {
                if(binding.txtFrom.text.toString().isNotEmpty()) {
                val datePicker = CommonMethods.selectAnyDate(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                   // binding.txtTo.text = getDate(it)
                    if (CommonMethods.validateDate(binding.txtFrom.text.toString(), getDate(it))) {

                        binding.txtTo.text = getDate(it)
                    } else {
                        println("End date is not after start date.")
                        CommonMethods.showToast(this,"To date is not after start date.")
                    }
                }
                }
                else{
                    CommonMethods.showToast(this, "Please select from first")
                }

            }

            R.id.btnSubmit -> {


                if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                    viewModel.getAllLeavesDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
            }
        }
    }

    override fun onErrorMessage(message: String) {

    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
       // mList.clear()
        binding.recyclerLeaves.visibility=View.GONE
        binding.txtNoData.visibility=View.VISIBLE

    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun selectedItem(week: LeaveModel) {
        val intent = Intent(this, AddLeaveActivity::class.java)
        intent.putExtra("from","edit")
        intent.putExtra("mObj",week)
        //startActivity(intent)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 121) {
                viewModel.getAllLeaves()
            } else {
                Log.e("Location Result", "Location Is Not Fetched")
            }
        }



}