package com.projetos.amanda.proconanalytics.models

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class FbData {


    var bairroFB:String = ""
    var bandeiraFB:String = ""
    var nomeFB:String = ""
    var produtoFB:String = ""
    var valorFB:String = ""


    constructor(bairroFB: String, bandeiraFB: String, nomeFB: String, produtoFB: String, valorFB: String) {
        this.bairroFB = bairroFB
        this.bandeiraFB = bandeiraFB
        this.nomeFB = nomeFB
        this.produtoFB = produtoFB
        this.valorFB = valorFB
    }


}