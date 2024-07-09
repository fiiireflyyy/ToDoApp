package com.fenix.todoapp.domain.model

sealed class Importance(val level: String) {
    object Low : Importance("low")
    object Medium : Importance("basic")
    object High : Importance("important")
}