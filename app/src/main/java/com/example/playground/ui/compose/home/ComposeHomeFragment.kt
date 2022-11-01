package com.example.playground.ui.compose.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.ComposeHomeFragmentBinding
import com.example.playground.repository.SampleData
import com.example.playground.ui.manage.MusicModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ComposeHomeFragment :
    CoreFragment<ComposeHomeFragmentBinding>(ComposeHomeFragmentBinding::inflate) {

    @Preview
    @Composable
    fun PreviewContent() {
        ComposeHomeScreen(
            onClickFetch = ::previewFun,
            onClickAdd = ::previewFun,
            state = Success(SampleData.musicsModel)
        )
    }

    private fun previewFun() = Unit

    override fun setupViews() {
        binding.content.setContent { ComposeHomeRoute() }
    }

    @Composable
    private fun ComposeHomeRoute(
        viewModel: ComposeHomeViewModel = getViewModel()
    ) {
        val state by viewModel.state.collectAsState()

        ComposeHomeScreen(
            onClickFetch = viewModel::fetchList,
            onClickAdd = viewModel::addItem,
            state = state
        )
    }

    @Composable
    private fun ComposeHomeScreen(
        onClickFetch: () -> Unit,
        onClickAdd: () -> Unit,
        state: ComposeHomeState
    ) {
        Column(Modifier.padding(16.dp)) {

            ButtonFetch(onClick = onClickFetch)

            Spacer(modifier = Modifier.height(8.dp))

            ButtonAdd(state = state, onClick = onClickAdd)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    is Empty -> {
                        Text(text = "Nenhum item")
                    }
                    is Loading -> {
                        CircularProgressIndicator()
                    }
                    is Success -> {
                        Column {
                            Text(text = "${state.list.size} items")
                            Spacer(modifier = Modifier.height(8.dp))
                            MusicList(list = state.list)
                        }
                    }
                    is Error -> {
                        Text(text = "Erro!", color = Color.Red, fontSize = 48.sp)
                    }
                }
            }
        }
    }

    @Composable
    private fun ButtonFetch(
        onClick: () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClick
        ) {
            Text(text = "Fetch")
        }
    }

    @Composable
    private fun ButtonAdd(
        state: ComposeHomeState,
        onClick: () -> Unit
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClick,
            enabled = state is Success
        ) {
            Text(text = "Add Item")
        }
    }

    @Composable
    private fun MusicList(
        list: List<MusicModel>
    ) {
        LazyColumn {
            items(list) { music ->
                MusicCard(music)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun MusicCard(music: MusicModel) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            elevation = 2.dp,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                Toast.makeText(context, music.name, Toast.LENGTH_SHORT).show()
            }
        ) {
            Row(Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_music),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(text = music.name, style = MaterialTheme.typography.subtitle2)
                    music.artist?.let { Text(text = it, style = MaterialTheme.typography.body2) }
                }
            }
        }
    }
}