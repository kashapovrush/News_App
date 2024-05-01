package com.kashapovrush.newsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.error.ErrorFragment
import com.example.features_common.constans.Constants.CURRENT_STATE
import com.example.features_common.constans.Constants.EXTRA_FAVOURITE_STATE
import com.example.features_common.constans.Constants.EXTRA_HEADLINES_STATE
import com.example.features_common.constans.Constants.EXTRA_SOURCE_STATE
import com.example.features_common.constans.Constants.NAVIGATION_STATE
import com.example.features_favourite.ui.FavouriteFragment
import com.example.features_filter_news.ui.FilterNewsFragment
import com.example.features_filter_news.ui.FilteredNewsFragment
import com.example.features_news_post.ui.NewsPostFragment
import com.example.features_search.ui.SearchHeadlinesFragment
import com.example.features_source.ui.SourceNewsFragment
import com.example.features_source.ui.SourcesFragment
import com.example.features_splash_screen.ui.SplashScreenFragment
import com.example.features_splash_screen.ui.SplashScreenFragment.Companion.ANIMATION_FINISHED
import com.example.features_splash_screen.ui.SplashScreenFragment.Companion.ANIMATION_STATE
import com.example.features_splash_screen.ui.SplashScreenFragment.Companion.EXTRA_DATA
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.headlines_features.presentation.ui.HeadlinesFragment
import com.kashapovrush.headlines_features.presentation.ui.tabs.BusinessTab
import com.kashapovrush.headlines_features.presentation.ui.tabs.GeneralTab
import com.kashapovrush.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), HeadlinesFragment.OnClickListenerFromHeadlinesFragment,
    SearchHeadlinesFragment.OnClickListenerFromSearchHeadlinesFragment,
    FilterNewsFragment.OnClickListenerFromFilterNews,
    FilteredNewsFragment.ClickListenerFromFilteredNews, BusinessTab.ClickListener,
    FavouriteFragment.ClickListenerFromFavourite, SourcesFragment.ClickListenerFromSourcesFragment,
    GeneralTab.ClickListenerFromHeadlines, ErrorFragment.ClickListenerFromError {

    private lateinit var binding: ActivityMainBinding
    private var stateScreen = 0
    private var animationState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (animationState != ANIMATION_FINISHED) {
            invisibleBottomBar()
            supportFragmentManager.commit {
                replace(R.id.container_for_fragments, SplashScreenFragment.newInstance())
                animationState = ANIMATION_FINISHED
            }
        }

        supportFragmentManager.setFragmentResultListener(ANIMATION_STATE, this) { _, bundle ->
            animationState = bundle.getInt(EXTRA_DATA)
            when(animationState) {
                ANIMATION_FINISHED -> {
                    if (savedInstanceState == null) {
                        visibleBottomBar()
                        clearBottomBarBackground()
                        binding.layoutHeadlinesBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
                        binding.titleHeadlinesBottomBar.visibility = View.VISIBLE
                        supportFragmentManager.popBackStack()
                        supportFragmentManager.commit {
                            replace(R.id.container_for_fragments, HeadlinesFragment.newInstance())

                        }
                    }
                }
            }
        }

        binding.layoutHeadlines.setOnClickListener {
            clearBottomBarBackground()
            binding.layoutHeadlinesBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
            binding.titleHeadlinesBottomBar.visibility = View.VISIBLE
            supportFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.container_for_fragments, HeadlinesFragment.newInstance())

            }
        }

        binding.layoutFavourite.setOnClickListener {
            clearBottomBarBackground()
            binding.layoutFavouriteBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
            binding.titleFavouriteBottomBar.visibility = View.VISIBLE
            supportFragmentManager.popBackStack()
            supportFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.container_for_fragments, FavouriteFragment.newInstance())
            }
        }

        binding.layoutSources.setOnClickListener {
            clearBottomBarBackground()
            binding.layoutSourcesBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
            binding.titleSourcesBottomBar.visibility = View.VISIBLE
            supportFragmentManager.popBackStack()
            supportFragmentManager.commit {
                addToBackStack(null)
                replace(R.id.container_for_fragments, SourcesFragment.newInstance())
            }
        }
    }

    override fun clickListenerToFilter() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, FilterNewsFragment.newInstance())
        }
    }


    override fun fromHeadlinesToSearch() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, SearchHeadlinesFragment.newInstance())
        }
    }

    override fun clickListenerToNewsPost(post: NewsHeadlines?) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, NewsPostFragment.newInstance(post))
        }
    }

    override fun fromSearchToHeadlines() {
        supportFragmentManager.commit {
            onBackPressed()
        }
    }

    override fun clickListenerToFilterScreen() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, FilteredNewsFragment.newInstance())
        }
    }

    override fun clickListenerToHeadlinesFragment() {
        supportFragmentManager.commit {
            replace(R.id.container_for_fragments, HeadlinesFragment.newInstance())
        }
    }

    override fun clickListenerToChooseFilter() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, FilterNewsFragment.newInstance())
        }
    }

    override fun clickListenerToSearch() {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, SearchHeadlinesFragment.newInstance())
        }
    }

    override fun clickListenerFromTabs(post: NewsHeadlines) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, NewsPostFragment.newInstance(post))
        }
    }

    fun clearBottomBarBackground() {
        with(binding) {
            layoutSourcesBackground.setBackgroundResource(0)
            titleSourcesBottomBar.visibility = View.GONE
            layoutFavouriteBackground.setBackgroundResource(0)
            titleFavouriteBottomBar.visibility = View.GONE
            layoutHeadlinesBackground.setBackgroundResource(0)
            titleHeadlinesBottomBar.visibility = View.GONE
        }

    }

    fun invisibleBottomBar() {
        with(binding) {
            layoutHeadlines.visibility = View.INVISIBLE
            layoutSources.visibility = View.INVISIBLE
            layoutFavourite.visibility = View.INVISIBLE
        }
    }

    fun visibleBottomBar() {
        with(binding) {
            layoutHeadlines.visibility = View.VISIBLE
            layoutSources.visibility = View.VISIBLE
            layoutFavourite.visibility = View.VISIBLE
        }
    }

    override fun clickListenerToSourceNews(source: String?) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, SourceNewsFragment.newInstance(source ?: ""))
        }
    }

    override fun clickListenerToShowError(error: String) {
        supportFragmentManager.commit {
            addToBackStack(null)
            replace(R.id.container_for_fragments, ErrorFragment.newInstance(error))
        }
    }

    override fun onResume() {
        super.onResume()
        supportFragmentManager.setFragmentResultListener(NAVIGATION_STATE, this) { _, bundle ->
            stateScreen = bundle.getInt(CURRENT_STATE)
            when(stateScreen) {
                EXTRA_HEADLINES_STATE -> {
                    clearBottomBarBackground()
                    binding.layoutHeadlinesBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
                    binding.titleHeadlinesBottomBar.visibility = View.VISIBLE
                }
                EXTRA_FAVOURITE_STATE -> {
                    clearBottomBarBackground()
                    binding.layoutFavouriteBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
                    binding.titleFavouriteBottomBar.visibility = View.VISIBLE
                }
                EXTRA_SOURCE_STATE -> {
                    clearBottomBarBackground()
                    binding.layoutSourcesBackground.setBackgroundResource(com.kashapovrush.headlines_features.R.drawable.background_for_icon)
                    binding.titleSourcesBottomBar.visibility = View.VISIBLE
                }
            }
        }
    }

}