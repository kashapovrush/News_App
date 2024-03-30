package com.example.features_news_post.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.database.dbModel.NewsHeadlinesDb
import com.example.features_common.viewmodel.FavouriteViewModel
import com.example.features_news_post.R
import com.example.features_news_post.databinding.FragmentNewsPostBinding
import com.example.features_news_post.di.NewsPostComponentProvider
import com.google.android.material.appbar.AppBarLayout
import com.kashapovrush.api.modelsDto.NewsHeadlines
import com.kashapovrush.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs


class NewsPostFragment : Fragment() {

    private lateinit var binding: FragmentNewsPostBinding
    private lateinit var viewModel: FavouriteViewModel
    private var current = false

    @Inject
    lateinit var viewModelFactory: ViewModelFactory



    override fun onAttach(context: Context) {
        (requireActivity().application as NewsPostComponentProvider).getNewsPostComponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsPostBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]

        val post = arguments?.getParcelable<NewsHeadlines>(EXTRA_NEWS_HEADLINES)
        super.onViewCreated(view, savedInstanceState)


        viewModel.newsList.observe(viewLifecycleOwner) { it ->
            it.forEach {news ->
                if (post?.title == news.title) {
                    current = true
                    binding.btnAddToFavourite.setImageResource(R.drawable.ic_bookmark_enabled)
                }
            }
        }

        binding.titleUnderCollapsing.text = post?.title
        binding.timeTv.text = showTime(post?.publishedAt)
        Glide.with(requireContext()).load(post?.urlToImage).into(binding.imageContent)
        binding.contentNewsText.text = post?.content
        binding.sourcesTv.text = post?.source?.name

        if (post?.content != null) {
            setSpannableText(post)
        }

        requireActivity().window.statusBarColor = Color.TRANSPARENT
        requireActivity().window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        setCollapsingToolbar(post)

        binding.btnAddToFavourite.setOnClickListener {
            if (!current) {
                current = true
                binding.btnAddToFavourite.setImageResource(R.drawable.ic_bookmark_enabled)
                if (post != null) {
                    viewModel.addNewsPost(
                        NewsHeadlinesDb(
                            title = post.title ?: "",
                            source = post.source.name ?: "",
                            url = post.url,
                            description = post.description,
                            publishedAt = post.publishedAt,
                            urlToImage = post.urlToImage,
                            content = post.content,
                            deleteTime = 0,
                            isFavourite = 1
                        )
                    )
                }
            } else {
                current = false
                binding.btnAddToFavourite.setImageResource(R.drawable.ic_bookmark_border)
                viewModel.deleteNewsPost(post?.title ?: "")
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    private fun setCollapsingToolbar(post: NewsHeadlines?) {
        val collapsingToolbar = binding.collapsingToolbar
        val appBarLayout = binding.appBarLayout

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val range = appBarLayout.totalScrollRange
            val percentage = (abs(verticalOffset).toFloat() / range.toFloat())

            if (percentage < 0.5) {
                binding.titleInCollapsingTolbar.visibility = View.INVISIBLE
                binding.layoutUnderCollapsing.visibility = View.VISIBLE
            } else {
                binding.titleInCollapsingTolbar.visibility = View.VISIBLE
                binding.titleInCollapsingTolbar.text = post?.title
                binding.layoutUnderCollapsing.visibility = View.INVISIBLE
            }
        })
    }

    private fun setSpannableText(post: NewsHeadlines?) {
        val startSpannableText = post?.content?.lastIndexOf(".")?.plus(2)
        val endSpannableText = post?.content?.length

        val spannableString = SpannableString(post?.content)

        val clickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post?.url))
                widget.context.startActivity(intent)
            }

        }

        spannableString.setSpan(
            clickable,
            startSpannableText ?: 0,
            endSpannableText ?: 0,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.contentNewsText.text = spannableString
        binding.contentNewsText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.statusBarColor = ContextCompat.getColor(
            requireContext(),
            com.example.palette.R.color.background_splash_screen
        )

        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    private fun showTime(date: String?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.ENGLISH)
        val newDate = inputFormat.parse(date ?: "")
        return outputFormat.format(newDate)
    }


    companion object {

        const val EXTRA_NEWS_HEADLINES = "news_headlines"

        fun newInstance(newsPost: NewsHeadlines) = NewsPostFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_NEWS_HEADLINES, newsPost)
            }
        }
    }
}