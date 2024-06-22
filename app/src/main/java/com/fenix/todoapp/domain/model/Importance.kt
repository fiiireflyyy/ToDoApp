package com.fenix.todoapp.domain.model

sealed class Importance(val level: String) {
    object Low : Importance("низкая")
    object Medium : Importance("обычная")
    object High : Importance("срочная")
}