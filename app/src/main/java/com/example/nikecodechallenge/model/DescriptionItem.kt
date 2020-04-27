package com.example.nikecodechallenge.model

import com.squareup.moshi.Json

data class DescriptionItem(
    @field:Json(name = "definition")
    var definition: String,

    @field:Json(name = "example")
    var example: String,

    @field:Json(name = "thumbs_up")
    var thumbs_up: Int,

    @field:Json(name = "thumbs_down")
    var thumbs_down: Int,

    @field:Json(name = "author")
    var author: String
)

data class DescriptionResponse(
    @field:Json(name = "list")
    var list: List<DescriptionItem>
)
