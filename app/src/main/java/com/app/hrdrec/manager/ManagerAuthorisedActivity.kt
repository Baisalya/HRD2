package com.app.hrdrec.manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.admin.Admin
import com.app.hrdrec.databinding.ActivityManagerAuthorisedBinding
import com.app.hrdrec.home.ClickInterface
import com.app.hrdrec.home.HomeViewModel
import com.app.hrdrec.home.ModuleDataAdapter
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.leaves.AllLeavesActivity
import com.app.hrdrec.organization.Organization
import com.app.hrdrec.timesheet.TimeSchedulerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ManagerAuthorisedActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    var from:String=""

    @Inject
    lateinit var albumDataAdapter: ModuleDataAdapter

    private val binding by lazy { ActivityManagerAuthorisedBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        from=intent.getStringExtra("from")!!
        setAdapter()
        binding.headerMain.imgBackBtn.setOnClickListener {
            finish()
        }

    }

    private fun setAdapter() {
        binding.recyclerView.adapter = albumDataAdapter
        var list= ArrayList<ModuleData>()
        if(from == "authLeave") {
            list.add(ModuleData(name = "Authorized"))
            list.add(ModuleData(name = "Leaves"))
        }
        else{
            list.add(ModuleData(name = "Authorized"))
            list.add(ModuleData(name = "Timesheets"))
        }
        albumDataAdapter.updateAlbumData(list)


        albumDataAdapter.setItemClick(object : ClickInterface<ModuleData> {
            override fun onClick(data: ModuleData) {
                Log.e("Data","onClick"+data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {


                    "Leaves" -> {
                        val intent =
                            Intent(this@ManagerAuthorisedActivity, AllLeavesActivity::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(intent)

                    }
                    "Authorized" -> {

                        if(from == "authLeave") {
                            val intent =
                                Intent(
                                    this@ManagerAuthorisedActivity,
                                    MangerLeaveReviewActivity::class.java
                                )
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }
                        else{
                            val intent =
                                Intent(
                                    this@ManagerAuthorisedActivity,
                                    ManagerTimeSheetActivity::class.java
                                )
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }

                    }

                    "Timesheets"  -> {
                        val intent =
                            Intent(this@ManagerAuthorisedActivity, TimeSchedulerActivity::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(intent)

                    }


                }

            }
        })


    }
}