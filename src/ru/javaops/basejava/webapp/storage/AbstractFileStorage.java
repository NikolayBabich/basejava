package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void saveImpl(File file, Resume resume) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("I/O error occurred while creating", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected void updateImpl(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("I/O error occurred while updating", file.getName(), e);
        }
    }

    @Override
    protected void deleteImpl(File file) {
        if (!file.delete()) {
            throw new StorageException("I/O error occurred while deleting", file.getName());
        }
    }

    @Override
    protected Resume getImpl(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("I/O error occurred while reading", file.getName(), e);
        }
    }

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected File getSpecificSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExists(File file) {
        return file.exists();
    }

    @Override
    protected Collection<Resume> getAllResumes() {
        return Arrays.stream(directory.listFiles())
                .map(this::getImpl)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        Arrays.stream(directory.listFiles())
                .forEach(this::deleteImpl);
    }

    @Override
    public int size() {
        return (int) Arrays.stream(directory.listFiles())
                .count();
    }
}
