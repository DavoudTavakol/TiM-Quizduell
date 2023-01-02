package com.tim.quiz_api.exceptions

import kotlin.reflect.KClass


class CategoryException(val msg:String) : RuntimeException(msg)
