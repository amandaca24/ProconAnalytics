package com.projetos.amanda.proconanalytics.models

import com.orm.SugarRecord
import java.util.*

class Posto(): SugarRecord (){

    var bairro:String = ""
    var bandeira:String = ""
    var nome:String = ""
    var data:Date? = null
    var valor:String = ""
    var zona:String = ""
    var posicao:String = ""

}