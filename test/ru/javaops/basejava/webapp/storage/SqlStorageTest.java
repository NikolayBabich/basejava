package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}
