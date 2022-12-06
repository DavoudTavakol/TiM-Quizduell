package com.tim.quiz_api.controller.dto

data class SubmitRequest(val gameId : String, val token : String , val answers : List<Boolean>)