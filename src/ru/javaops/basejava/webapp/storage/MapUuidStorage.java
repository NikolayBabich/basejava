package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

public final class MapUuidStorage extends AbstractMapStorage {

    @SuppressWarnings({"RedundantCast"})
    @Override
    protected void deleteImpl(Object uuid) {
        storage.remove((String) uuid);
    }

    @SuppressWarnings({"RedundantCast"})
    @Override
    protected Resume getImpl(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected String getSpecificSearchKey(String uuid) {
        return uuid;
    }

    @SuppressWarnings({"RedundantCast"})
    @Override
    protected boolean isExists(Object uuid) {
        return storage.containsKey((String) uuid);
    }
}
