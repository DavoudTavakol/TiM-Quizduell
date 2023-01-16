package com.tim.quiz_api.controller.dto

import com.tim.quiz_api.data.Question

data class CheckResponse (var isReady : Boolean, var questions : List<Question>) {
}