package com.noahcharlton.stationalpha.world.save;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;
import java.io.StringWriter;

public class QuietXmlWriter {

    private final XmlWriter xmlWriter;

    public QuietXmlWriter(FileHandle handle) {
        this.xmlWriter = new XmlWriter(handle.writer(false));
    }

    public QuietXmlWriter(XmlWriter xmlWriter) {
        this.xmlWriter = xmlWriter;
    }

    public QuietXmlWriter(StringWriter writer){
        this(new XmlWriter(writer));
    }

    public QuietXmlWriter element(String name) {
        try{
            return new QuietXmlWriter(xmlWriter.element(name));
        }catch(IOException e){
            throw new GdxRuntimeException(e);
        }
    }

    public QuietXmlWriter element(String name, Object text) {
        try{
            return new QuietXmlWriter(xmlWriter.element(name, text));
        }catch(IOException e){
            throw new GdxRuntimeException(e);
        }
    }

    public QuietXmlWriter attribute(String name, Object value) {
        try{
            return new QuietXmlWriter(xmlWriter.attribute(name, value));
        }catch(IOException e){
            throw new GdxRuntimeException(e);
        }
    }

    public QuietXmlWriter pop() {
        try {
            return new QuietXmlWriter(xmlWriter.pop());
        } catch(IOException e) {
            throw new GdxRuntimeException(e);
        }
    }

    public void flush(){
        try {
            xmlWriter.flush();
        } catch(IOException e) {
            throw new GdxRuntimeException(e);
        }
    }

    public XmlWriter getXmlWriter() {
        return xmlWriter;
    }
}
