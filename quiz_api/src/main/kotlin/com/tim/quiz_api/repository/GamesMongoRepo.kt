package com.tim.quiz_api.repository

import com.tim.quiz_api.data.Game
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface GamesMongoRepo : MongoRepository<Game, String>{
    fun findGameByGameId(gameId : String) : Game
    fun removeGameByGameId(uid: String)
}