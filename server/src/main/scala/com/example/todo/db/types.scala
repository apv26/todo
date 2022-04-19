package com.example.db

case class Todo(id: Int, description: String, done: Boolean)

case class Actor(id: Int, name: String)

case class Movie(
    id: String,
    title: String,
    year: Int,
    actors: List[String],
    director: String
)
