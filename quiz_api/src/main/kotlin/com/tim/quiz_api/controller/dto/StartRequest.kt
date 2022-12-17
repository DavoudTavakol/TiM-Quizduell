package com.tim.quiz_api.controller.dto

import com.tim.quiz_api.data.Player

data class StartRequest(var player1 : Player, var categories : List<String> ) {
}