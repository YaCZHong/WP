package me.xh.wp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import kotlinx.coroutines.launch
import me.xh.wp.databinding.ActivityMainBinding
import me.xh.wp.ui.adapter.RvFooterAdapter
import me.xh.wp.ui.adapter.WallpaperAdapter
import me.xh.wp.vm.WallpaperVM

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val vm by lazy { ViewModelProvider(this)[WallpaperVM::class.java] }
    private val mAdapter = WallpaperAdapter()
    private val mFooterAdapter = RvFooterAdapter { mAdapter.retry() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        mAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.pb.isVisible = false
                    binding.btnRetry.isVisible = false
                }

                is LoadState.Loading -> {
                    binding.pb.isVisible = true
                    binding.btnRetry.isVisible = false
                }

                is LoadState.Error -> {
                    binding.pb.isVisible = false
                    binding.btnRetry.isVisible = true
                }
            }
        }
        binding.rv.adapter = mAdapter.withLoadStateFooter(mFooterAdapter)
        binding.btnRetry.setOnClickListener { mAdapter.retry() }
    }

    private fun initData() {
        lifecycleScope.launch {
            vm.loadWallpaperList().collect { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }
    }
}