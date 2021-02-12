package com.example.moviescatalogue.favorite

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.core.utils.Constants.Companion.FAVORITE
import com.example.moviescatalogue.di.FavoriteModuleDependencies
import com.example.moviescatalogue.favorite.databinding.ActivityFavoriteBinding
import com.example.moviescatalogue.favorite.di.DaggerFavoriteComponent
import dagger.hilt.android.EntryPointAccessors

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var navController: NavController
    lateinit var favoriteComponent: DaggerFavoriteComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        favoriteComponent = DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    application.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build() as DaggerFavoriteComponent
        favoriteComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_favorite) as NavHostFragment
        navController = navHostFragment.navController

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = FAVORITE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}