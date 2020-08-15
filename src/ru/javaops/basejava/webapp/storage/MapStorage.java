package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public final class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveImpl(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateImpl(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteImpl(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected Resume getImpl(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected int searchByUuid(String uuid) {
        return storage.get(uuid) == null ? -1 : 0;
    }
}
