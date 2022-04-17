package com.bhaskar.test3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bhaskar.test3.R
import com.bhaskar.test3.adapters.MainRecyclerAdapter
import com.bhaskar.test3.databinding.ActivityMainBinding
import com.bhaskar.test3.databinding.BottomSheetLayoutBinding
import com.bhaskar.test3.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var mainAdapter: MainRecyclerAdapter
    private lateinit var viewModel: MainViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        mainAdapter = MainRecyclerAdapter()
        viewModel.callApi()
        with(viewBinding) {
            onItemClick = View.OnClickListener { view ->
                when(view.id) {
                    R.id.mainFloatingButton -> showBottomSheet()
                }
            }
            mainRecyclerView.apply {
                adapter = mainAdapter
            }
        }
        viewModel.getNews().observe(this@MainActivity) {
            mainAdapter.apply {
                submitData(it)
                notifyDataSetChanged()
                Log.d("APICALL", "$it")
            }
        }
    }

    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialogTheme)
        val dialogViewBinding: BottomSheetLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.bottom_sheet_layout, null, false)
        dialog.apply {
            setContentView(dialogViewBinding.root)
            show()
        }
        with(dialogViewBinding) {
            onItemCLick = View.OnClickListener { view ->
                when(view.id) {
                    R.id.closeButton -> {
                        dialog.dismiss()
                    }
                }
            }
            sourceCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    sourceChipGroup.visibility = View.VISIBLE
                } else {
                    sourceChipGroup.visibility = View.GONE
                }
            }
            descriptionCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    descriptionChipGroup.visibility = View.VISIBLE
                } else {
                    descriptionChipGroup.visibility = View.GONE
                }
            }
        }
    }
}