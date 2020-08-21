package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

public final class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected void deleteImpl(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected Resume getImpl(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected String getSpecificSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExists(String uuid) {
        return storage.containsKey(uuid);
    }
}
