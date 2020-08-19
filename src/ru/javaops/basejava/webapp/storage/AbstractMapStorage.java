package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {
    final Map<String, Resume> storage = new HashMap<>();

    @Override
    public final void clear() {
        storage.clear();
    }

    @Override
    public final int size() {
        return storage.size();
    }

    @Override
    protected final void saveImpl(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final void updateImpl(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final Collection<Resume> getAllResumes() {
        return storage.values();
    }
}
