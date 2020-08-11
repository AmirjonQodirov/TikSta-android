//https://pastebin.com/s0W1TVpu

package com.tiksta.test4.post.data

class Tag {
    var id: Int = 0
    var name: String = ""
    var tiktokId: Int = 0
    var instagramId: Int = 0

    constructor(name: String, tiktokId: Int, instagramId: Int) {
        this.name = name
        this.tiktokId = tiktokId
        this.instagramId = instagramId
    }

    constructor(){

    }
}