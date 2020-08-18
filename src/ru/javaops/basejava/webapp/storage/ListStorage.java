package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
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
    protected void saveImpl(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateImpl(Object index, Resume resume) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void deleteImpl(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected Resume getImpl(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected Integer getSpecificSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExists(Object index) {
        return index != null;
    }
}