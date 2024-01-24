package com.app.hrdrec.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.app.hrdrec.R
import com.app.hrdrec.admin.Admin
import com.app.hrdrec.databinding.ActivityHomeBinding
import com.app.hrdrec.home.getallmodules.ModuleData
import com.app.hrdrec.leaves.AllLeavesActivity
import com.app.hrdrec.login.Login
import com.app.hrdrec.manager.ManagerAuthorisedActivity
import com.app.hrdrec.organization.Organization
import com.app.hrdrec.timesheet.TimeSchedulerActivity
import com.app.hrdrec.utils.CommonMethods
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeViewModel.CallBackLogin {

    @Inject
    lateinit var albumDataAdapter: ModuleDataAdapter

    var moduleSize:Int=0
    private val homeViewModel: HomeViewModel by viewModels()

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = albumDataAdapter
        setObserver()
        homeViewModel.setCallBacks(this)
        homeViewModel.getModuleList()
        //option
        val btnOptionsMenu: ImageButton = findViewById(R.id.btnOptionsMenu)
        btnOptionsMenu.setOnClickListener { view ->
            showPopupMenu(view)
        }
    
        ///
        val current= CommonMethods.getCurrentDateTime()
        Log.e("Current","ss "+current)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        // Add hamburger icon to open the drawer
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        albumDataAdapter.setItemClick(object : ClickInterface<ModuleData> {
            override fun onClick(data: ModuleData) {
                Log.e("Data","onClick"+data.name)
                //AlbumDetailActivity.launchActivity(this@ShowAlbumActivity,data)
                val transaction = supportFragmentManager.beginTransaction()
                when (data.name) {
                    "Organization" -> {
                        // Create the OrganizationFragment and pass the data
                        val organizationFragment = Organization().apply {
                            arguments = Bundle().apply {
                                putSerializable("mObj", data)
                            }
                        }

                        // Replace the current fragment with OrganizationFragment
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameContainer, organizationFragment)
                            .commit()
                    }

                    "Users", "User Administration" -> {
                        val admin = Intent(this@HomeActivity, Admin::class.java)
                        admin.putExtra("mObj", data)
                        startActivity(admin)

                    }

                    "Leave Management" -> {
                        val organizationFragment = Organization().apply {
                            arguments = Bundle().apply {
                                putSerializable("mObj", data)
                            }
                        }

                        // Replace the current fragment with OrganizationFragment
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameContainer, organizationFragment)
                            .commit()
                    }

                    "Leaves" -> {
                        if(moduleSize==2||moduleSize==3) {
                            val intent =
                                Intent(this@HomeActivity, AllLeavesActivity::class.java)
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }
                        else{
                            val intent =
                                Intent(this@HomeActivity, ManagerAuthorisedActivity::class.java)
                            intent.putExtra("mObj", data)
                            intent.putExtra("from", "authLeave")
                            startActivity(intent)
                        }

                    }
                    "Timesheets" -> {

                        if(moduleSize==2||moduleSize==3) {
                            val intent =
                                Intent(this@HomeActivity, TimeSchedulerActivity::class.java)
                            intent.putExtra("mObj", data)
                            startActivity(intent)
                        }
                        else{
                            /*val intent =
                                Intent(this@HomeActivity, ManagerTimeSheetActivity::class.java)
                            intent.putExtra("mObj", data)
                            startActivity(intent)*/

                            val intent =
                                Intent(this@HomeActivity, ManagerAuthorisedActivity::class.java)
                            intent.putExtra("mObj", data)
                            intent.putExtra("from", "authTime")
                            startActivity(intent)
                        }

                    }



                    else -> {
                        val organizationFragment = Organization().apply {
                            arguments = Bundle().apply {
                                putSerializable("mObj", data)
                            }
                        }

                        // Replace the current fragment with OrganizationFragment
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameContainer, organizationFragment)
                            .commit()
                      /*  val intent = Intent(this@HomeActivity, Organization::class.java)
                        intent.putExtra("mObj", data)
                        startActivity(intent)*/
                    }


                }

            }
        })
        binding.btnDrawer.setOnClickListener {
            // Show the drawer before handling sign out
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.nav_menu) // Create a menu resource file (res/menu/your_popup_menu.xml)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    // Handle option 1 click
                    true
                }
                R.id.menu_item2 -> {
                    // Handle option 2 click
                    true
                }
                R.id.menu_item3 -> {
                    // Handle option 3 click
                    CommonMethods.showAlertYesNoMessage(this,"Are you sure you want to sign out"){

                        homeViewModel.sharedPreferences.clearPreference()

                        val intent = Intent(this, Login::class.java)
                        //  val intent = Intent(this, AddRoles::class.java)
                        startActivity(intent)
                        finish()
                    }

                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)  // Optional: Add transaction to back stack
        transaction.commit()
    }

    private fun setObserver() {
        homeViewModel.moduleData.observe(this) { mList ->
            moduleSize=mList.size
            albumDataAdapter.updateAlbumData(mList)
            //  val adapter=ModuleDataAdapter(mList)

        }
    }

    override fun onErrorMessage(message: String) {

    }


    override fun showLoader() {
        CommonMethods.showLoader(this)
    }

    override fun hideLoader() {
        CommonMethods.hideLoader()
    }
}