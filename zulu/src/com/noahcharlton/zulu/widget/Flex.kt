package com.noahcharlton.zulu.widget

class FlexPane(var spacing: Int = 10) : Pane(){

    private val children: HashMap<Widget, Int> = HashMap();

    fun addChild(child: Widget, flex: Int){
        children[child] = flex;
    }

    override fun layout() {
//        val staticChildren = children.filter { entry -> entry.value == 0 }
//        val dynamicChildren = children.filter { entry -> entry.value != 0 }
        val contentPane = getContentRect()
        val children = getChildren()
        var width: Float = contentPane.width.toFloat()

        width -= spacing * (children.size - 1) //Last child has no spacing
        width /= children.size

        var x : Float = contentPane.x.toFloat()

        children.forEach { child ->
            child.pos.x = x.toInt();
            child.pos.y = contentPane.y

            child.size.width = width.toInt();
            child.size.height = contentPane.height

            x += width;
            x += spacing;
        }

        super.layout()
    }

    override fun getChildren(): List<Widget> {
        return children.keys.toList()
    }

}