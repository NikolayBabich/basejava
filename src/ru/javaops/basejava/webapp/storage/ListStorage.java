package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected int findByUuid(String uuid) {
        Collections.sort(storage);
        return Collections.binarySearch(storage, new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume resume) {
        checkNotExist(resume.getUuid());
        storage.add(resume);
    }

    @Override
    public void update(Resume resume) {
       int index = checkExist(resume.getUuid());
       storage.set(index, resume);
    }

    @Override
    public void delete(String uuid) {
        storage.remove(checkExist(uuid));
    }

    @Override
    public Resume get(String uuid) {
        return storage.get(checkExist(uuid));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
