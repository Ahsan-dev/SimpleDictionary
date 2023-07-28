package com.example.simpledictionary.builder_pattern

class Dictionary private constructor(
    val word: Boolean,
    val type: Boolean,
    val meaning: Boolean
){
    class Builder{
        private var word: Boolean = true
        private var type: Boolean = true
        private var meaning: Boolean = true

        fun word(value: Boolean) = apply { word = value }

        fun type(value: Boolean) = apply { type = value }

        fun meaning(value: Boolean) = apply { meaning = value }

        fun build() = Dictionary(word,type,meaning)
    }
}