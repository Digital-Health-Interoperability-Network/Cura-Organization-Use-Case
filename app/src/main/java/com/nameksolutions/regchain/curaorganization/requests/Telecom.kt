package com.nameksolutions.regchain.curaorganization.requests

import com.google.gson.annotations.SerializedName

//@Serializable
data class Telecom(
    //@SerializedName ("system")
    val system: String = "",
    //@SerializedName ("rank")
    val rank: String = "",
    //@SerializedName ("value")
    val value: String = "",
    //@SerializedName ("use")
    val use: String = "Official"
)
