package com.noahcharlton.zulu.widget

import com.noahcharlton.zulu.Point
import com.noahcharlton.zulu.Rectangle
import com.noahcharlton.zulu.Size

abstract class FlexPane(var spacing: Int = 10) : Pane(){

    private val children: LinkedHashMap<Widget, Int> = LinkedHashMap();

    fun addChild(child: Widget, flex: Int){
        children[child] = flex;
    }

    override fun layout() {
        val staticChildren = children.filter { entry -> entry.value == 0 }
        val dynamicChildren = children.filter { entry -> entry.value != 0 }
        val contentPane = getContentRect()
        var space: Float = getMajor(contentPane.asSize()).toFloat()//contentPane.width.toFloat()

        staticChildren.keys.forEach {widget ->
            space -= getMajor(widget.size) + spacing
        }

        var dynamicSegments = 0
        dynamicChildren.values.forEach { flex -> dynamicSegments += flex }

        space -= spacing * (dynamicChildren.size - 1)//Last child has no spacing

        var spacePerSegment : Float = space / dynamicSegments
        layoutChildren(contentPane, spacePerSegment)

        super.layout()
    }

    private fun layoutChildren(contentPane: Rectangle, spacePerSegment: Float) {
        var pos: Float = getMajor(contentPane.asPoint()).toFloat()

        this.children.forEach { entry ->
            val widget = entry.key
            val flex = entry.value

            setMajor(widget.pos, pos.toInt())
            setMinor(widget.pos, getMinor(contentPane.asPoint()))
            setMinor(widget.size, getMinor(contentPane.asSize()))

            if (flex == 0) {
                pos += getMajor(widget.size)
            } else {
                setMajor(widget.size, (flex * spacePerSegment).toInt())
                pos += flex * spacePerSegment
            }

            pos += spacing
        }
    }

    abstract fun getMajor(pos: Point) : Int

    abstract fun getMajor(size: Size) : Int

    abstract fun setMajor(pos: Point, value: Int)

    abstract fun setMajor(size: Size, value: Int)

    abstract fun getMinor(pos: Point) : Int

    abstract fun getMinor(size: Size) : Int

    abstract fun setMinor(pos: Point, value: Int);

    abstract fun setMinor(size: Size, value: Int);

    override fun getChildren(): List<Widget> {
        return children.keys.toList()
    }

}

class RowPane : FlexPane(){
    override fun getMajor(pos: Point): Int{
        return pos.x
    }

    override fun getMajor(size: Size): Int {
        return size.width
    }

    override fun setMajor(pos: Point, value: Int){
        pos.x = value
    }

    override fun setMajor(size: Size, value: Int){
        size.width = value
    }

    override fun getMinor(pos: Point): Int{
        return pos.y
    }

    override fun getMinor(size: Size): Int {
        return size.height
    }

    override fun setMinor(pos: Point, value: Int){
        pos.y = value
    }

    override fun setMinor(size: Size, value: Int){
        size.height = value
    }
}

class ColumnPane : FlexPane(){
    override fun getMajor(pos: Point): Int{
        return pos.y
    }

    override fun getMajor(size: Size): Int {
        return size.height
    }

    override fun setMajor(pos: Point, value: Int){
        pos.y = value
    }

    override fun setMajor(size: Size, value: Int){
        size.height = value
    }

    override fun getMinor(pos: Point): Int{
        return pos.x
    }

    override fun getMinor(size: Size): Int {
        return size.width
    }

    override fun setMinor(pos: Point, value: Int){
        pos.x = value
    }

    override fun setMinor(size: Size, value: Int){
        size.width = value
    }
}
