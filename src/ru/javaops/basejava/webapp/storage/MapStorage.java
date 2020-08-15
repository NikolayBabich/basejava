package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public final class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int findByUuid(String uuid) {
        return storage.get(uuid) == null ? -1 : 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        checkNotExist(uuid);
        storage.put(uuid, resume);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        checkExist(uuid);
        storage.put(uuid, resume);
    }

    @Override
    public void delete(String uuid) {
        checkExist(uuid);
        storage.remove(uuid);
    }

    @Override
    public Resume get(String uuid) {
        checkExist(uuid);
        return storage.get(uuid);
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
