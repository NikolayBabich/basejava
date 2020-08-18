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
    protected void saveImpl(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateImpl(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

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
