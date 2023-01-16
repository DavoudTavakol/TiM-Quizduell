package de.mmapp.quiz_frontend.models

import de.mmapp.quiz_frontend.models.Question

data class CheckResponse (var isReady : Boolean, var questions : List<Question>) {
}