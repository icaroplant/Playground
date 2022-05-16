package com.example.playground.ui.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.ComposeHomeFragmentBinding
import com.example.playground.repository.SampleData
import com.example.playground.ui.manage.MusicModel

class ComposeHomeFragment :
    CoreFragment<ComposeHomeFragmentBinding>(ComposeHomeFragmentBinding::inflate) {

    @Preview
    @Composable
    fun PreviewContent() {
        SetupContent()
    }

    override fun setupViews() {
        binding.content.setContent { SetupContent() }
    }

    @Composable
    private fun SetupContent() {
        Column(
            Modifier
                .padding(16.dp)
                .background(Color.White)) {
            Text(text = "Music List", Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(16.dp))
            MusicList(SampleData.musicsModel)
        }
    }

    @Composable
    private fun MusicList(list: List<MusicModel>) {
        LazyColumn {
            items(list) { music ->
                MusicCard(music)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    private fun MusicCard(music: MusicModel) {
        Row {
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
                Text(text = music.name)
                music.artist?.let { Text(text = it) }
            }
        }
    }
}