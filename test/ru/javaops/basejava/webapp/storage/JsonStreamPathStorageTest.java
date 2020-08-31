package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serialization.JsonStreamStrategy;

public class JsonStreamPathStorageTest extends AbstractStorageTest {
    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamStrategy()));
    }
}
