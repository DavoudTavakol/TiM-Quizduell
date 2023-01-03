package com.tim.quiz_api.service

import com.tim.quiz_api.data.Score
import com.tim.quiz_api.repository.HighscoreRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class HighscoreService @Autowired constructor(private val highscoreRepo: HighscoreRepo) {

    fun getHighscoreList(): List<Score>{
        return highscoreRepo.findAll(Sort.by(Sort.Direction.DESC, "score")) as List<Score>
    }

    fun updateHighscore(score: Int, nickname: String){
        var highscoreList = getHighscoreList()

        if(score < highscoreList[highscoreList.size - 1].score)return

        if(highscoreList.size < 10){
            val scoreData = Score(score, nickname)
            highscoreRepo.save(scoreData)
            return
        }

        for(highscore in highscoreList){
            if(highscore.score < score){
                val scoreData = Score(score, nickname, id = highscore.id)
                highscoreRepo.save(scoreData)
                return
            }
        }

    }

}