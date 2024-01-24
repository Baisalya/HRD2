package com.app.hrdrec.timesheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.R
import com.app.hrdrec.databinding.ActivityTimeSchedulerBinding
import com.app.hrdrec.timesheet.request_payload.AddTimeModal
import com.app.hrdrec.utils.CommonInterface
import com.app.hrdrec.utils.CommonMethods
import com.app.hrdrec.utils.CommonMethods.getCurrentDateTime
import com.app.hrdrec.utils.CommonMethods.selectAnyDate
import com.app.hrdrec.utils.getDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeSchedulerActivity : AppCompatActivity(), CommonInterface, View.OnClickListener,
    TimeSchudeListAdapter.FeatureCallBack {

    private val timeSheetViewModel: TimeSheetViewModel by viewModels()
    private var productFeatureAdapter: TimeSchudeListAdapter? = null

    private val binding by lazy { ActivityTimeSchedulerBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        timeSheetViewModel.setCallBacks(this)
        setObserver()
        timeSheetViewModel.getTimesheetscheduler()
        binding.headerMain.txtTitle.text = "TimeSheet"
        setUpListerner()

    }

    private fun setObserver() {
        timeSheetViewModel.myResponseList.observe(this) {
            Log.e("Data", "herrrr" + it.data.size)
            binding.recyclerTimeSchedule.visibility=View.VISIBLE
            binding.txtNoData.visibility=View.GONE
            productFeatureAdapter = TimeSchudeListAdapter(it.data, this)
            binding.recyclerTimeSchedule.adapter = productFeatureAdapter
        }

        timeSheetViewModel.addClientsResponse.observe(this) {
            it.message?.let { it1 -> CommonMethods.showToast(this, it1)
                CommonMethods.hideLoader()
            }

        }


    }

    private fun setUpListerner() {
        binding.apply {
            headerMain.imgBackBtn.setOnClickListener(this@TimeSchedulerActivity)
            addTimeSheet.setOnClickListener(this@TimeSchedulerActivity)
            conFromDate.setOnClickListener(this@TimeSchedulerActivity)
            conToDate.setOnClickListener(this@TimeSchedulerActivity)
            btnSubmit.setOnClickListener(this@TimeSchedulerActivity)

        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == 121) {
                timeSheetViewModel.getTimesheetscheduler()
            } else {
                Log.e("Location Result", "Location Is Not Fetched")
            }
        }
    override fun onErrorMessage(message: String) {
        CommonMethods.showToast(this, message)
    }

    override fun onResponseSuccess() {

    }

    override fun noListData() {
        binding.recyclerTimeSchedule.visibility=View.GONE
        binding.txtNoData.visibility=View.VISIBLE
    }

    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.addTimeSheet -> {
//                val intent = Intent(this, AddTimeSheetActivity::class.java)
//                intent.putExtra("selectedWeek", "")
//                startActivity(intent)
                val current=getCurrentDateTime()
                val model= AddTimeModal()
                model.weekendDate=current
                model.totalHours=0.0
                model.status="Saved"
                val data = timeSheetViewModel.sharedPreferences.getUserInfo()
                val empId = data?.employeeId
                model.employeeId=empId
                model.organizationId=data?.organizationId
                model.locationId=data?.locationId
                Log.e("modell","ss"+model)
                timeSheetViewModel.addTimesheetscheduler(model)
            }

            R.id.img_back_btn -> {
                finish()
            }

            R.id.conFromDate -> {

                val datePicker = selectAnyDate(null)
                datePicker.show(supportFragmentManager, "DatePicker")
                datePicker.addOnPositiveButtonClickListener {
                    binding.txtFrom.text = getDate(it)
                }
            }

            R.id.conToDate -> {

                if(binding.txtFrom.text.toString().isNotEmpty()) {
                    val datePicker = selectAnyDate(null)
                    datePicker.show(supportFragmentManager, "DatePicker")
                    datePicker.addOnPositiveButtonClickListener {
                        //  binding.txtTo.text = getDate(it)

                        if (CommonMethods.validateDate(
                                binding.txtFrom.text.toString(),
                                getDate(it)
                            )
                        ) {

                            binding.txtTo.text = getDate(it)
                        } else {
                            println("End date is not after start date.")
                            CommonMethods.showToast(this, "To date is not after start date.")
                        }
                    }
                }
                else{
                    CommonMethods.showToast(this, "Please select from first")
                }



            }

            R.id.btnSubmit -> {


                if(binding.txtFrom.text.toString().isNotEmpty()&&binding.txtTo.text.toString().isNotEmpty())

                timeSheetViewModel.getTimesheetschedulerDates(binding.txtFrom.text.toString(),binding.txtTo.text.toString())
            }

        }
    }

    override fun selectedItem(selectedWeek: String, id: Int?) {
        val intent = Intent(this, AddTimeSheetActivity::class.java)
        intent.putExtra("selectedWeek", selectedWeek)
        intent.putExtra("timeId", id)
      //  startActivity(intent)
        resultLauncher.launch(intent)
    }
}
