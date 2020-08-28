package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage<SK> implements Storage {
    private static final Comparator<Resume> DEFAULT_RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    static {
        LOG.setLevel(Level.WARNING);
    }

    /**
     * @param resume Resume to be saved to this storage
     * @throws ExistStorageException if the Resume already exists in this storage
     * @throws StorageException      if this storage reaches limit size (for limited implementations)
     */
    @Override
    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        doSave(getNotExistedSearchKey(resume.getUuid()), resume);
    }

    protected abstract void doSave(SK searchKey, Resume resume);

    /**
     * @param resume Resume to replace one with the same uuid in this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        doUpdate(getExistedSearchKey(resume.getUuid()), resume);
    }

    protected abstract void doUpdate(SK searchKey, Resume resume);

    /**
     * @param uuid identifier of Resume to be deleted from this storage
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final void delete(String uuid) {
        LOG.info("Delete #" + uuid);
        doDelete(getExistedSearchKey(uuid));
    }

    protected abstract void doDelete(SK searchKey);

    /**
     * @param uuid identifier of Resume to be returned
     * @return Resume with {@code uuid} identifier
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    @Override
    public final Resume get(String uuid) {
        LOG.info("Get #" + uuid);
        return doGet(getExistedSearchKey(uuid));
    }

    protected abstract Resume doGet(SK searchKey);

    /**
     * Returns search key if Resume with {@code uuid} already exists in this storage
     *
     * @param uuid identifier of the Resume to be searched for
     * @return abstract search key (concrete specification in implementations) for the Resume
     * @throws NotExistStorageException if the Resume doesn't exist in this storage
     */
    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSpecificSearchKey(uuid);
        if (!isExists(searchKey)) {
            LOG.warning(String.format("Resume #%s is not found in storage", uuid));
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    /**
     * Returns search key if Resume with {@code uuid} doesn't exist in this storage
     *
     * @param uuid identifier of the Resume to be searched for
     * @return specific search key (depending on implementation) for the Resume
     * @throws ExistStorageException if the Resume already exists in this storage
     */
    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSpecificSearchKey(uuid);
        if (isExists(searchKey)) {
            LOG.warning(String.format("Resume #%s already exists in storage", uuid));
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    /**
     * @param uuid identifier of the Resume to be searched for
     * @return specific search key (depending on implementation) for the Resume
     */
    protected abstract SK getSpecificSearchKey(String uuid);

    /**
     * Checks whether the Resume with specific search key already exists in this storage
     *
     * @param searchKey specific search key (depending on implementation) for the Resume
     * @return {@code true} if the Resume exists in this storage, {@code false} otherwise
     */
    protected abstract boolean isExists(SK searchKey);

    /**
     * @return {@code List} containing all Resumes sorted
     *          with {@code DEFAULT_RESUME_COMPARATOR}
     */
    @Override
    public final List<Resume> getAllSorted() {
        Collection<Resume> resumes = getAll();
        return resumes.stream()
                .sorted(DEFAULT_RESUME_COMPARATOR)
                .collect(Collectors.toList());
    }

    protected abstract List<Resume> getAll();
}
