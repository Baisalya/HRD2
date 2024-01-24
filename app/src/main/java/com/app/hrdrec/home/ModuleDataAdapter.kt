package com.app.hrdrec.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.hrdrec.databinding.AllModuleRowBinding
import com.app.hrdrec.home.getallmodules.ModuleData
import com.squareup.picasso.Picasso

import javax.inject.Inject


class ModuleDataAdapter @Inject constructor() : RecyclerView.Adapter<ModuleDataAdapter.ModuleDataViewHolder>() {

    var mList = mutableListOf<ModuleData>()
    private var clickInterface: ClickInterface<ModuleData>? = null

    fun updateAlbumData(ModuleDatas: List<ModuleData>) {
        this.mList = ModuleDatas.toMutableList()
        notifyItemRangeInserted(0, ModuleDatas.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleDataViewHolder {
        val binding =
            AllModuleRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleDataViewHolder, position: Int) {
        val mObj = mList[position]
        holder.view.tvTitle.text = mObj.name

        // Load image based on the type
        val imageUrl = when (mObj.name) {
            "Organization" -> "https://cdn-icons-png.flaticon.com/128/3696/3696724.png"
            "Users" -> "https://cdn-icons-png.flaticon.com/512/9131/9131529.png"
            "Employees" -> "https://cloud.squidex.io/api/assets/sorwe-cms/ab723ac3-dc60-4364-adcc-660f45644b56"
            "Leaves" -> "https://img.freepik.com/free-vector/quitting-time-concept-illustration_114360-2175.jpg?w=740&t=st=1706099978~exp=1706100578~hmac=6895ed8a0de2816585cb6abd2bbdc6ba6571846d629dc36a06f0d7f0c67a9342"
            "Timesheets" -> "https://img.freepik.com/free-vector/time-management-concept-landing-page_52683-20654.jpg?w=900&t=st=1706099785~exp=1706100385~hmac=ef647e740902dc9419a01748e3f061b3945240ede2a3acee44ece503532952a2"
            "Expences" -> "https://img.freepik.com/free-vector/tax-preparation-concept-illustration_114360-15617.jpg?w=740&t=st=1706099887~exp=1706100487~hmac=ba8f3b9b0d21b313eee9d5a9f435e23aa924a1633c62ee871d42431fc1ac36b2"
            else -> "https://img.freepik.com/free-vector/loading-circles-blue-gradient_78370-2646.jpg?w=740&t=st=1706100028~exp=1706100628~hmac=4d2bca82de23ea1a783736007c5d75f15348ac34cb8fc28da40c1d7216326145"
        }

        Picasso
            .get()
            .load(imageUrl)
            .into(holder.view.imgModule)
        // Load image using Picasso with the provided URL
      /*  Picasso
            .get()
            .load("https://images.unsplash.com/photo-1575936123452-b67c3203c357?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D")
            .into(holder.view.imgModule)*/
//        Picasso
//            .get()
//            .load(mObj.url)
//            .into(holder.view.imgAlbum)

        holder.itemView.setOnClickListener {
            clickInterface?.onClick(mObj)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemClick(clickInterface: ClickInterface<ModuleData>) {
        this.clickInterface = clickInterface
    }

    class ModuleDataViewHolder(val view: AllModuleRowBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}