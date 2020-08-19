package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

public final class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void deleteImpl(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Resume getImpl(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected Resume getSpecificSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExists(Object resume) {
        return resume != null;
    }
}
