package com.dolezal.album

fun Throwable.getDescription(): String {
    return message ?: this::class.java.simpleName
}