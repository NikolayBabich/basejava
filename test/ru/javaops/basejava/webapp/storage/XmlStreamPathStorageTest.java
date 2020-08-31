package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serialization.XmlStreamStrategy;

public class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamStrategy()));
    }
}
