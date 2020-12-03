package com.example.playground.repository

import androidx.lifecycle.LiveData
import com.example.playground.data.db.dao.MusicDAO
import com.example.playground.data.db.entity.MusicEntity
import java.lang.Exception

class MusicRepositoryImp(
    val musicDAO: MusicDAO
) : MusicRepository {

    override suspend fun insertMusic(
        name: String,
        artist: String?,
        track: Int?,
        albumName: String?
    ) : Long {

        if(name.isEmpty()){
            throw Exception("O nome da música é obrigatório!")
        }

        return musicDAO.insert(
            MusicEntity(
                name = name,
                artist = artist,
                track = track
            )
        )
    }

    override suspend fun updateMusic(
        id: Long,
        name: String,
        artist: String?,
        track: Int?,
        albumName: String?
    ) {

        if(name.isEmpty()){
            throw Exception("O nome da música é obrigatório!")
        }

        musicDAO.update(
            MusicEntity(
                id = id,
                name = name,
                artist = artist,
                track = track
            )
        )
    }

    override suspend fun deleteMusic(id: Long) {
        musicDAO.delete(id)
    }

    override suspend fun deleteAllMusics() {
        musicDAO.deleteAll()
    }

    override suspend fun getMusic(id: Long): List<MusicEntity> {
        return musicDAO.getById(id)
    }

    override suspend fun getMusicsByArtist(artist: String): List<MusicEntity> {
        return musicDAO.getByArtist(artist)
    }

    override fun getAllMusics(): LiveData<List<MusicEntity>> {
        return musicDAO.getAll()
    }
}