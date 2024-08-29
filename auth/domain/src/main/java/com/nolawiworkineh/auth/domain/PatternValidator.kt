package com.nolawiworkineh.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}