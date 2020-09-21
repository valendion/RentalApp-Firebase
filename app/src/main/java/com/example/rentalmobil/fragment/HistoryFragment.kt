package com.example.rentalmobil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rentalmobil.R
import com.example.rentalmobil.activity.login.mAuth
import com.example.rentalmobil.adapter.AdapterHistory
import com.example.rentalmobil.model.ModelHistoryCar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_history.view.*


class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var ref: DatabaseReference
    lateinit var list: MutableList<ModelHistoryCar>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.rv_history.setHasFixedSize(true)

        ref = FirebaseDatabase.getInstance().getReference("pesanan")
        list = mutableListOf()

        ref.orderByChild("idUser").equalTo(mAuth.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (data: DataSnapshot in snapshot.children){
                    val user = data.getValue(ModelHistoryCar::class.java)
                    list.add(user!!)
                }



                val historyAdater = AdapterHistory(list = list,context = view.context)

                if (historyAdater.itemCount == 0){
                    view.tv_empty_history.visibility = View.VISIBLE
                }else if(historyAdater.itemCount > 0){
                    view.tv_empty_history.visibility = View.GONE
                }

                view.rv_history.adapter = historyAdater
                view.rv_history.layoutManager = LinearLayoutManager(activity)

            }

        })


    }


}