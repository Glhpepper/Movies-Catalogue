package com.example.moviescatalogue.detail.shows

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.example.moviescatalogue.R
import com.example.core.ui.detail.ViewPagerAdapter
import com.example.core.utils.Constants.Companion.DETAIL_SHOWS_BUNDLE
import com.example.moviescatalogue.databinding.ActivityDetailShowsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailShowsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailShowsBinding? = null
    private val binding get() = _binding

    private val detailShowsViewModel: DetailShowsViewModel by viewModels()
    private val arg by navArgs<DetailShowsActivityArgs>()
    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailShowsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        lifecycleScope.launch {
            val checkFavorite = detailShowsViewModel.checkFavoriteShows(arg.id.toInt())
            statusFavorite = checkFavorite
        }

        setSupportActionBar(binding?.toolbarShows)
        binding?.toolbarShows?.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupView()
    }

    private fun setupView() {
        val fragment = ArrayList<Fragment>()
        fragment.add(OverviewShowsFragment())
        fragment.add(SeasonShowsFragment())

        val titles = ArrayList<String>()
        titles.add(getString(R.string.overview))
        titles.add(getString(R.string.season))

        val bundle = Bundle()
        bundle.putString(DETAIL_SHOWS_BUNDLE, arg.id)

        val pagerAdapter = ViewPagerAdapter(bundle, fragment, supportFragmentManager, lifecycle)

        binding?.viewPagerShows?.adapter = pagerAdapter

        binding?.tabLayoutShows?.let { tabLayout ->
            binding?.viewPagerShows?.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = titles[position]
                }.attach()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_menu, menu)
        val menuFavorite = menu?.findItem(R.id.favorite)
        setMenuColor(menuFavorite)
        return true
    }

    private fun setMenuColor(menuFavorite: MenuItem?) {
        if (statusFavorite) {
            menuFavorite?.icon?.setTint(ContextCompat.getColor(this, R.color.red))
        } else {
            menuFavorite?.icon?.setTint(ContextCompat.getColor(this, R.color.white))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        else if (item.itemId == R.id.favorite) setFavorite(item)
        return super.onOptionsItemSelected(item)
    }

    private fun setFavorite(item: MenuItem) {
        statusFavorite = !statusFavorite
        if (statusFavorite) {
            binding?.detailLayoutShows?.let {
                Snackbar.make(it, R.string.state_true, Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.okay)) {}.show()
            }
            setMenuColor(item)
            insetFavoriteShows()
        } else {
            binding?.detailLayoutShows?.let {
                Snackbar.make(it, R.string.state_false, Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.okay)) {}.show()
            }
            setMenuColor(item)
            deleteFavoriteShows()
        }
    }

    private fun insetFavoriteShows() {
        detailShowsViewModel.getDetailShows(arg.id).observe(this, { value ->
            val shows = value.data
            if (shows != null) {
                detailShowsViewModel.insertFavoriteShows(shows)
            }
        })
    }

    private fun deleteFavoriteShows() {
        detailShowsViewModel.getDetailShows(arg.id).observe(this, { value ->
            value.data?.id?.let { detailShowsViewModel.deleteFavoriteShowsById(it) }
        })
    }
}