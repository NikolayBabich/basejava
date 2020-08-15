package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveImpl(int index, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateImpl(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteImpl(int index, String uuid) {
        storage.remove(index);
    }

    @Override
    protected Resume getImpl(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected int searchByUuid(String uuid) {
        Collections.sort(storage);
        return Collections.binarySearch(storage, new Resume(uuid));
    }
}
