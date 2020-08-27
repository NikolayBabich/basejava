package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath()
                    + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath()
                    + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected final void saveImpl(File file, Resume resume) {
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("I/O error while creating " + file.getAbsolutePath(),
                    file.getName(), e);
        }
        updateImpl(file, resume);
    }

    protected abstract void doWrite(Resume resume, OutputStream os) throws IOException;

    @Override
    protected final void updateImpl(File file, Resume resume) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("I/O error while writing ", file.getName(), e);
        }
    }

    @Override
    protected final void deleteImpl(File file) {
        if (!file.delete()) {
            throw new StorageException("I/O error while deleting", file.getName());
        }
    }

    @Override
    protected final Resume getImpl(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("I/O error while reading", file.getName(), e);
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected final File getSpecificSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected final boolean isExists(File file) {
        return file.exists();
    }

    @Override
    protected final Collection<Resume> getAllResumes() {
        return Arrays.stream(getListFiles())
                .map(this::getImpl)
                .collect(Collectors.toList());
    }

    @Override
    public final void clear() {
        Arrays.stream(getListFiles())
                .forEach(this::deleteImpl);
    }

    @Override
    public final int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("I/O error with directory", null);
        }
        return files;
    }
}
