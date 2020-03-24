package com.example.ipapp.model

class PAR {
    var parNumber:Int=0
    var docId:Int=0
    var text:String?=null
    var orig:String?=null
    var ro:String?=null

    constructor(){}

    constructor(parNumber:Int, docId:Int, text:String, orig:String, ro:String){
        this.parNumber=parNumber
        this.docId=docId
        this.text=text
        this.orig=orig
        this.ro=ro
    }
}