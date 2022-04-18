package com.bhaskar.test3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bhaskar.test3.R
import com.bhaskar.test3.adapters.MainRecyclerAdapter
import com.bhaskar.test3.databinding.ActivityMainBinding
import com.bhaskar.test3.databinding.BottomSheetLayoutBinding
import com.bhaskar.test3.models.Article
import com.bhaskar.test3.models.News
import com.bhaskar.test3.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip


class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var mainAdapter: MainRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private val articles: List<Article> = listOf()
    private var news = News(articles, "", 0)
    private var businessToday = ""
    private var newYorkTimes = ""
    private var elonMusk = ""
    private var washington = ""

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
                news = it
                submitData(news)
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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
                        dialog.hide()
                    }
                    R.id.applyButton -> {
                        if (sourceCheckBox.isChecked || descriptionCheckBox.isChecked) {
                            mainAdapter.apply {
                                submitFilters(news, businessToday, newYorkTimes, elonMusk, washington)
                                notifyDataSetChanged()
                            }
                        } else {
                            mainAdapter.apply {
                                submitData(news)
                                notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            chipBusinessToday.setOnCheckedChangeListener { _, isChecked ->
                businessToday = if (isChecked) "Business Today" else ""
            }
            newYorkTimesChip.setOnCheckedChangeListener { _, isChecked ->
                newYorkTimes = if (isChecked) "New York Times" else ""
            }
            elonMaskChip.setOnCheckedChangeListener { _, isChecked ->
                elonMusk = if (isChecked) "Elon Musk" else ""
            }
            elonMaskChip.setOnCheckedChangeListener { _, isChecked ->
                washington = if (isChecked) "Washington" else ""
            }
            sourceCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    sourceChipGroup.visibility = View.VISIBLE
                } else {
                    sourceChipGroup.visibility = View.GONE
                }
            }
            descriptionCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    descriptionChipGroup.visibility = View.VISIBLE
                } else {
                    descriptionChipGroup.visibility = View.GONE
                }
            }
        }
    }
}