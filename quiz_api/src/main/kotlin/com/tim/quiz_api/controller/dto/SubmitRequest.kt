package com.tim.quiz_api.controller.dto

import com.tim.quiz_api.data.Answer

data class SubmitRequest(val gameId : String, val nickname : String , val answers : List<Answer>, val time : Float)