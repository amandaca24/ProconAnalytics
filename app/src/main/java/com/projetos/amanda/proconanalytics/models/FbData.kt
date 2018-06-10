package com.projetos.amanda.proconanalytics.models

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class FbData (
    var bairroFB: String,
    var bandeiraFB: String ,
    var nomeFB: String,
    var produtoFB: String,
    var valorFB: String,
    var latitude: Double,
    var longitude: Double
){
}