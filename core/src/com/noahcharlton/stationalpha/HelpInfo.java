package com.noahcharlton.stationalpha;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HelpInfo {

    private static final Logger logger = LogManager.getLogger(HelpInfo.class);
    private static final Map<String, String> info = new HashMap<>();

    private static void init(){
        logger.info("Loading help info!");

        String xml = Gdx.files.internal("help.xml").readString();
        XmlReader.Element reader = new XmlReader().parse(xml);

        for(XmlReader.Element child: reader.getChildrenByName("Entry")){
            String id = child.getAttribute("id");
            String text = child.getText().replaceAll("\\s+", " ").replace("\\n", "\n");

            if(info.containsKey(id)){
                throw new GdxRuntimeException("Duplicate HelpInfo Entry: " + id);
            }

            info.put(id, text);
        }
    }

    public static String get(String id){
        if(info.isEmpty())
            init();

        return info.get(id);
    }
}
