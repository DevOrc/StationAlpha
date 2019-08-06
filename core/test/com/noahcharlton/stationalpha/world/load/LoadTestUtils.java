package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.XmlReader;

public class LoadTestUtils {

    public static XmlReader.Element asElement(String xml){
        return new XmlReader().parse(xml);
    }

    public static XmlReader.Element asChild(String xml){
        return asElement("<Foo>" + xml + "</Foo>");
    }
}
