package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationStrategy strategy;

    PathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void saveImpl(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("I/O error while creating " + path,
                    path.getFileName().toString(), e);
        }
        updateImpl(path, resume);
    }

    @Override
    protected void updateImpl(Path path, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("I/O error while writing ",
                    path.getFileName().toString(), e);
        }
    }

    @Override
    protected void deleteImpl(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("I/O error while deleting",
                    path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getImpl(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("I/O error while reading",
                    path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSpecificSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExists(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Collection<Resume> getAllResumes() {
        return getAllPaths()
                .map(this::getImpl)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getAllPaths().forEach(this::deleteImpl);
    }

    @Override
    public int size() {
        return (int) getAllPaths().count();
    }

    private Stream<Path> getAllPaths() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("I/O error with directory", null, e);
        }
    }
}
