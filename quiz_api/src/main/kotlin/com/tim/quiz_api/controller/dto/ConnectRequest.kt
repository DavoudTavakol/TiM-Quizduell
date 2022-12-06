package com.tim.quiz_api.controller.dto

import com.tim.quiz_api.data.Player

data class ConnectRequest(val player2 : Player, val gameId : String)