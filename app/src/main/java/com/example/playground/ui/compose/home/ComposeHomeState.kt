package com.example.playground.ui.compose.home

import com.example.playground.ui.manage.MusicModel

sealed interface ComposeHomeState

object Empty : ComposeHomeState
object Loading : ComposeHomeState
data class Success(val list: List<MusicModel>) : ComposeHomeState
object Error : ComposeHomeState