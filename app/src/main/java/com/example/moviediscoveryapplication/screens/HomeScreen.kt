@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.moviediscoveryapplication.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.mocks.moviesList
import com.example.moviediscoveryapplication.model.CarouselItem

object ProfileStrings {
    const val HELLO_TEXT = "Hello, Smith"
    const val STREAM_TEXT = "Let’s stream your favorite movie"
    const val CIRCULAR_IMAGE_PROFILE = "circular image profile"
}

object SearchStrings {
    const val SEARCH_PLACEHOLDER = "Search"
    const val CLEAR_ALL_HISTORY = "Clear all history"
    const val CLOSE_ICON = "Close icon"
    const val SEARCH_ICON = "Search icon"
    const val HISTORY_ICON = "History icon"
    const val EMPTY_TEXT = ""
}

object CarouselItemStrings {
    const val MOVIE_IMAGE = "Movie image"
}

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ProfileSection()
            Spacer(modifier = Modifier.size(8.dp))
            SearchSection()
            Spacer(modifier = Modifier.size(30.dp))
            FeaturedMoviesCarousel(itemList = moviesList)
        }
    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(size = 180.dp))
                .background(Color.LightGray),
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = ProfileStrings.CIRCULAR_IMAGE_PROFILE
        )

        Column {
            Text(
                text = ProfileStrings.HELLO_TEXT,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )

            CompositionLocalProvider(LocalContentColor provides Color.LightGray) {
                Text(
                    text = ProfileStrings.STREAM_TEXT,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                )
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SearchSection() {
    var textState by remember { mutableStateOf("") }
    var isActiveState by remember { mutableStateOf(false) }
    val searchHistoryState by remember { mutableStateOf(mutableStateListOf("")) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        query = textState,
        onQueryChange = { textState = it },
        onSearch = {
            searchHistoryState.add(textState)
            isActiveState = false
            textState = SearchStrings.EMPTY_TEXT },
        active = isActiveState,
        onActiveChange = { isActiveState = it },
        placeholder = {
            Text(text = SearchStrings.SEARCH_PLACEHOLDER)
        },
        trailingIcon = {
            if (isActiveState) {
                Icon(
                    modifier = Modifier.clickable {
                        if (textState.isNotEmpty()) {
                            textState = SearchStrings.EMPTY_TEXT
                        } else {
                            isActiveState = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = SearchStrings.CLOSE_ICON
                )
            }
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = SearchStrings.SEARCH_ICON)
        }
    ) {
        searchHistoryState.forEach {
            if (it.isNotEmpty()) {
                Row(modifier = Modifier.padding(all = 14.dp)) {
                    Icon(imageVector = Icons.Default.History, contentDescription = SearchStrings.HISTORY_ICON)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = it)
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(all = 14.dp)
                .fillMaxWidth()
                .clickable {
                    searchHistoryState.clear()
                },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            text = SearchStrings.CLEAR_ALL_HISTORY
        )
    }
}

@Composable
fun FeaturedMoviesCarousel(
    itemList: List<CarouselItem>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { itemList.size }
    )

    Box {
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                pageSpacing = 12.dp,
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) { page ->
                val selectedPage = itemList[page]
                Box(modifier = Modifier
                    .width(320.dp)
                    .height(154.dp)
                ) {
                    CarouselCustomItem(
                        title = selectedPage.title,
                        release = selectedPage.release,
                        image = selectedPage.image
                    )
                }
            }
        }
    }
}

@Composable
fun CarouselCustomItem(
    title: String,
    release: String,
    image: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = CarouselItemStrings.MOVIE_IMAGE,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            CompositionLocalProvider(LocalContentColor provides Color.LightGray) {
                Text(
                    text = release,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
@Preview
fun HomeScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ProfileSection()
            Spacer(modifier = Modifier.size(8.dp))
            SearchSection()
            Spacer(modifier = Modifier.size(30.dp))
            FeaturedMoviesCarousel(itemList = moviesList)
        }
    }
}