package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveImpl(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateImpl(Integer index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void deleteImpl(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected Resume getImpl(Integer index) {
        return storage.get(index);
    }

    @Override
    protected Integer getSpecificSearchKey(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExists(Integer index) {
        return index != null;
    }

    @Override
    protected Collection<Resume> getAllResumes() {
        return new ArrayList<>(storage);
    }
}
