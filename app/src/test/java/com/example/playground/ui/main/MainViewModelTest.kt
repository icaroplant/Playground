package com.example.playground.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.playground.CoroutinesTestRule
import com.example.playground.InstantTaskExecutorRule
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.repository.MusicRepository
import com.example.playground.utils.LiveDataSingleEvent
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val musicRepository: MusicRepository = mockk()

    private val repositoryReponseObserver = mockk<Observer<LiveDataSingleEvent<ResponseModel<MusicEntity>>>>()

    private val mainViewModel by lazy{
        MainViewModel(
            musicRepository
        ).apply {
            repositoryReponse.observeForever(repositoryReponseObserver)
        }
    }

    @Before
    fun setup() {
        coEvery { musicRepository.getAllMusics() } returns MutableLiveData()
    }

    @Test
    fun getAllMusicsEvent() {
    }


    @Test
    fun `when saveMusic sucess`() = runBlocking {
        coEvery { musicRepository.insertMusic(any(),any()) } returns musicEntity.id

        mainViewModel.saveMusic(musicEntity.name, musicEntity.artist)

        verify{
            repositoryReponseObserver.onChanged(
                LiveDataSingleEvent(
                    ResponseModel.INSERT(
                        s = true,
                        m = musicEntity
                    )
                )
            )
        }
    }

    @Test
    fun `when saveMusic failure`() = runBlocking {
        coEvery { musicRepository.insertMusic(any(),any()) } returns -1

        mainViewModel.saveMusic(musicEntity.name, musicEntity.artist)

        verify{
            repositoryReponseObserver.onChanged(
                LiveDataSingleEvent(
                    ResponseModel.INSERT(
                        s = false,
                        e = "ERRO NO INSERT"
                    )
                )
            )
        }
    }

    @Test
    fun `when saveMusic throws exception`() = runBlocking {
        coEvery { musicRepository.insertMusic(any(),any()) } throws Exception("erro in test")

        mainViewModel.saveMusic(musicEntity.name, musicEntity.artist)

        verify{
            repositoryReponseObserver.onChanged(
                LiveDataSingleEvent(
                    ResponseModel.INSERT(
                        s = false,
                        e = "erro in test"
                    )
                )
            )
        }
    }


    @Test
    fun updateMusic() {
    }

    @Test
    fun deleteMusic() {
    }

    @Test
    fun deleteMusicFromInsert() {
    }

    @Test
    fun restoreMusicFromDelete() {
    }

    @Test
    fun restoreMusicFromUpdate() {
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}

private val musicEntity = MusicEntity(
    id = 1,
    name = "name",
    artist = "artist"
)