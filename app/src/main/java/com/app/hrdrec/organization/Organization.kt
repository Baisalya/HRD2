package com.app.hrdrec.organization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.hrdrec.databinding.ActivityOrganizationBinding
import com.app.hrdrec.home.HomeViewModel
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.home.getallmodules.Paths
import com.app.hrdrec.organization.clients.Clients
import com.app.hrdrec.organization.employees.Employees
import com.app.hrdrec.organization.holidaycalander.HolidayCalendar
import com.app.hrdrec.organization.locations.Location
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Organization : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var mObj: ModuleData
    private val binding by lazy { ActivityOrganizationBinding.inflate(layoutInflater) }

    @Inject
    lateinit var albumDataAdapter: OrganizationDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mObj = intent.getSerializableExtra("mObj") as ModuleData
        binding.recyclerView.adapter = albumDataAdapter

        albumDataAdapter.updateAlbumData(mObj.paths)

        albumDataAdapter.setItemClick(object : ClickInterfaceOrgan<Paths> {
            override fun onClick(data: Paths) {
                Log.e("Data","org"+data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                when (data.name) {

                    "Locations" -> {
                        val intent = Intent(this@Organization, Location::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    "Holiday Calendars" -> {
                        val admin = Intent(this@Organization, HolidayCalendar::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(admin)

                    }

                    "Clients" -> {
                        val intent = Intent(this@Organization, Clients::class.java)
                        intent.putExtra("id", mObj.id)
                        startActivity(intent)

                    }

                    else -> {

//                        val intent = Intent(this@Organization, Organization::class.java)
//                        intent.putExtra("mObj",data)
//                        startActivity(intent)
                    }
                }
            }
        })
    }

    /* val organization = findViewById<ImageButton>(R.id.locations)
     organization.setOnClickListener {
         val intent = Intent(this, Location::class.java)
         startActivity(intent)
     }

     val leaveType = findViewById<ImageButton>(R.id.leavetype)
     leaveType.setOnClickListener {
         val intent = Intent(this, LeaveType::class.java)
         startActivity(intent)
     }

     val holiday = findViewById<ImageButton>(R.id.holiday)
     holiday.setOnClickListener {
         val intent = Intent(this, HolidayCalendar::class.java)
         startActivity(intent)
     }
*/


}



