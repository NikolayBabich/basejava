package ru.javaops.basejava.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractSection<T> implements Serializable {

    public abstract T getContent();

    abstract void setContent(T content);

    public abstract String getSerializedContent();

    public abstract void setDeserializedContent(String serializedContent);
}
