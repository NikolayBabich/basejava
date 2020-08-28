package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {
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
    protected final void doSave(SK searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final void doUpdate(SK searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected final List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }
}
