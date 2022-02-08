package ru.dtx12.coolblue.core.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import ru.dtx12.coolblue.R
import ru.dtx12.coolblue.databinding.ActivityRootBinding

@AndroidEntryPoint
class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityRootBinding>(this, R.layout.activity_root)
    }
}