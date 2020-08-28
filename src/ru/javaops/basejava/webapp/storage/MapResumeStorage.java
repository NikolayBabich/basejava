package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

public final class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected Resume getSpecificSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExists(Resume resume) {
        return resume != null;
    }
}
