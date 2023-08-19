@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalCoilApi::class
)

package com.example.moviediscoveryapplication.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.moviediscoveryapplication.R
import com.example.moviediscoveryapplication.utils.constants.CarouselItemStrings
import com.example.moviediscoveryapplication.utils.constants.CarouselTransitionConstants
import com.example.moviediscoveryapplication.utils.constants.MostPopularStrings
import com.example.moviediscoveryapplication.utils.constants.MovieCategoriesFilterStrings
import com.example.moviediscoveryapplication.utils.constants.ProfileStrings
import com.example.moviediscoveryapplication.utils.constants.SearchStrings
import com.example.moviediscoveryapplication.utils.constants.TopRatedStrings
import com.example.moviediscoveryapplication.model.CarouselItem
import com.example.moviediscoveryapplication.utils.constants.NetworkConstants
import com.example.moviediscoveryapplication.utils.mocks.moviesListCarouselMock
import com.example.moviediscoveryapplication.utils.mocks.moviesListMock
import com.example.moviediscoveryapplication.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun HomeScreen() {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    viewModel.loadPopularMoviesList()
    viewModel.loadGenresList()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .heightIn(max = constraints.maxHeight.dp)
        ) {
            ProfileSection()
            Spacer(modifier = Modifier.size(8.dp))
            SearchSection()
            Spacer(modifier = Modifier.size(30.dp))
            FeaturedMoviesCarousel(featuredMoviesList = moviesListCarouselMock)
            Spacer(modifier = Modifier.size(30.dp))
            MovieCategoriesFilter(viewModel)
            Spacer(modifier = Modifier.size(30.dp))
            MostPopularSection(viewModel)
            Spacer(modifier = Modifier.size(30.dp))
            TopRatedSection()
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
    featuredMoviesList: List<CarouselItem>,
    autoScrollDuration: Long = 3000L
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { featuredMoviesList.size }
    )

    AutoScrollLogic(
        pagerState = pagerState,
        autoScrollDuration = autoScrollDuration
    )

    Box {
        Column {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                pageSpacing = (-30).dp,
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) { page ->
                val selectedPage = featuredMoviesList[page]
                Box(modifier = Modifier
                    .carouselTransition(page, pagerState)
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
            DotIndicators(
                pageCount = moviesListCarouselMock.size,
                pagerState = pagerState,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
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
fun AutoScrollLogic(
    pagerState: PagerState,
    autoScrollDuration: Long
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    if (isDragged.not()) {
        with(pagerState) {
            var currentPageKey by remember { mutableIntStateOf(0) }
            var isScrollingForward by remember { mutableStateOf(true) }

            LaunchedEffect(key1 = currentPageKey) {
                while (true) {
                    delay(timeMillis = autoScrollDuration)

                    if (isScrollingForward) {
                        val nextPage = (currentPage + 1).mod(moviesListCarouselMock.size)
                        animateScrollToPage(page = nextPage)
                        currentPageKey = nextPage

                        if (nextPage == moviesListCarouselMock.size - 1) {
                            isScrollingForward = false
                        }
                    } else {
                        val previousPage = (currentPage - 1 + moviesListCarouselMock.size).mod(
                            moviesListCarouselMock.size)
                        animateScrollToPage(page = previousPage)
                        currentPageKey = previousPage

                        if (previousPage == 0) {
                            isScrollingForward = true
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.carouselTransition(page: Int, pagerState: PagerState): Modifier = this.then(
    graphicsLayer {
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        val absPageOffset = pageOffset.absoluteValue

        val scaleFactor =
            CarouselTransitionConstants.MIN_SCALE_FACTOR + (CarouselTransitionConstants.SCALE_FACTOR_RANGE * (CarouselTransitionConstants.MAX_SCALE_FACTOR - absPageOffset.coerceIn(
                0f,
                1f
            )))
        alpha = scaleFactor
        scaleX = scaleFactor
        scaleY = scaleFactor
    }
)

@Composable
fun DotIndicators(
    modifier: Modifier,
    pageCount: Int,
    pagerState: PagerState
) {
    val selectedColor = Color.Cyan
    val unselectedColor = Color.Gray

    Row(modifier = modifier) {
        repeat(pageCount) { iteration ->
            val isSelected = pagerState.currentPage == iteration
            val color = if (pagerState.currentPage == iteration) selectedColor else unselectedColor
            Box(
                modifier = Modifier
                    .width(if (isSelected) 24.dp else 8.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
                    .padding(horizontal = 4.dp)
            )

            if (iteration < pageCount - 1) {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }
}

@Composable
fun MovieCategoriesFilter(viewModel: HomeScreenViewModel) {
    val genresList by viewModel.genresList.observeAsState()

    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = MovieCategoriesFilterStrings.CATEGORIES,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        val selectedCategories = remember { mutableStateListOf<String>() }

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            content = {
                items(genresList?.size ?: 0) { categoryIndex ->
                    val category = genresList?.get(categoryIndex)?.name
                    val isSelected = selectedCategories.contains(category)

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background)
                            .clickable {
                                if (isSelected) {
                                    selectedCategories.remove(category)
                                } else {
                                    selectedCategories.add(category.orEmpty())
                                }
                            }
                            .height(31.dp)
                            .width(80.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category.orEmpty(),
                            color = if (isSelected) Color.Cyan else Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun MostPopularSection(viewModel: HomeScreenViewModel) {
    val moviesList by viewModel.popularMoviesList.observeAsState(initial = emptyList())
    val genresList by viewModel.genresList.observeAsState()
    val genresMap = genresList?.associateBy {
        it.id
    }

    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = MostPopularStrings.MOST_POPULAR,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        LazyRow(
            modifier = Modifier
                .padding(vertical = 16.dp),
            content = {
                items(moviesList.size) { movieIndex ->
                    val movie = moviesList[movieIndex]
                    val genreNames = movie.genreIds.take(2).mapNotNull {
                        genresMap?.get(it)?.name
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray)
                            .height(231.dp)
                            .width(135.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(data = "${NetworkConstants.POSTER_BASE_URL}${movie.posterPath}"),
                            contentDescription = MostPopularStrings.MOVIE_IMAGE,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight(0.82f)
                                .fillMaxWidth()
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            CompositionLocalProvider(LocalContentColor provides Color.LightGray) {
                                Text(
                                    text = genreNames.joinToString(MostPopularStrings.COMMA_SEPARATOR),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TopRatedSection() {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            text = TopRatedStrings.TOP_RATED,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        LazyRow(
            modifier = Modifier
                .padding(vertical = 16.dp),
            content = {
                items(moviesListMock.size) { movieIndex ->
                    val movie = moviesListMock[movieIndex]

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Gray)
                            .height(231.dp)
                            .width(135.dp),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Image(
                            painter = painterResource(id = movie.image),
                            contentDescription = TopRatedStrings.MOVIE_IMAGE,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            CompositionLocalProvider(LocalContentColor provides Color.LightGray) {
                                Text(
                                    text = movie.genre,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}


@Composable
@Preview
fun HomeScreenPreview() {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    viewModel.loadPopularMoviesList()
    viewModel.loadGenresList()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            ProfileSection()
            Spacer(modifier = Modifier.size(8.dp))
            SearchSection()
            Spacer(modifier = Modifier.size(30.dp))
            FeaturedMoviesCarousel(featuredMoviesList = moviesListCarouselMock)
            Spacer(modifier = Modifier.size(30.dp))
            MovieCategoriesFilter(viewModel)
            Spacer(modifier = Modifier.size(30.dp))
            MostPopularSection(viewModel)
            Spacer(modifier = Modifier.size(30.dp))
            TopRatedSection()
        }
    }
}