package com.example.ipapp.model

class DOC {
    var docId:Int=0
    var autoTranslate:Boolean=false

    constructor(){}

    constructor(docId: Int, autoTranlate: Boolean){
        this.docId=docId
        this.autoTranslate=autoTranslate
    }
}