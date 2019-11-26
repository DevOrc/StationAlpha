package com.noahcharlton.zulu.widget

import com.noahcharlton.zulu.Point
import com.noahcharlton.zulu.Rectangle
import com.noahcharlton.zulu.Size

enum class FlexDirection {
    HORIZONTAL, VERTICAL
}

class FlexPane(var flexDirection: FlexDirection, var spacing: Int = 10) : Pane() {

    private val children: LinkedHashMap<Widget, Int> = LinkedHashMap();

    fun addChild(child: Widget, flex: Int) {
        children[child] = flex;
    }

    override fun layout() {
        val staticChildren = children.filter { entry -> entry.value == 0 }
        val dynamicChildren = children.filter { entry -> entry.value != 0 }
        val contentPane = getContentRect()
        var space: Float = getMajor(contentPane.asSize()).toFloat()

        staticChildren.keys.forEach { widget ->
            space -= getMajor(widget.size) + spacing
        }

        var dynamicSegments = 0
        dynamicChildren.values.forEach { flex -> dynamicSegments += flex }

        space -= spacing * (dynamicChildren.size - 1)//Last child has no spacing

        var spacePerSegment: Float = space / dynamicSegments
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

    private fun getMajor(pos: Point): Int {
        return if (flexDirection == FlexDirection.HORIZONTAL) {
            pos.x
        } else {
            pos.y
        }
    }

    private fun getMajor(size: Size): Int {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            return size.width
        } else {
            return size.height
        }
    }

    private fun setMajor(pos: Point, value: Int) {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            pos.x = value
        } else {
            pos.y = value
        }
    }

    private fun setMajor(size: Size, value: Int) {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            size.width = value
        } else {
            size.height = value
        }
    }

    private fun getMinor(pos: Point): Int {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            return pos.y
        } else {
            return pos.x
        }
    }

    private fun getMinor(size: Size): Int {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            return size.height
        } else {
            return size.width;
        }
    }

    private fun setMinor(pos: Point, value: Int) {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            pos.y = value
        } else {
            pos.x = value;
        }
    }

    private fun setMinor(size: Size, value: Int) {
        if (flexDirection == FlexDirection.HORIZONTAL) {
            size.height = value
        } else {
            size.width = value;
        }
    }

    override fun getChildren(): List<Widget> {
        return children.keys.toList()
    }
}

