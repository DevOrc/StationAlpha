package com.noahcharlton.zulu

class Point(var x: Int, var y: Int){
    constructor() : this(0, 0)

    fun set(x: Int, y: Int){
        this.x = x
        this.y = y
    }
}
class Size(var width: Int, var height: Int){
    constructor() : this(0, 0)

    fun set(width: Int, height: Int){
        this.width = width
        this.height = height
    }
}

class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int){
    constructor() : this(0, 0, 0, 0)
    constructor(point: Point, size: Size) : this(point.x, point.y, size.width, size.height)

    fun asPoint(): Point{
        return Point(x, y)
    }

    fun asSize(): Size{
        return Size(width, height)
    }

    fun set(x: Int, y: Int, width: Int, height: Int){
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}

enum class Direction{
    NORTH, SOUTH, EAST, WEST
}
