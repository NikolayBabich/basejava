package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy strategy;

    PathStorage(String dir, SerializationStrategy strategy) {
        Objects.requireNonNull(dir, "directory must not be null");
        Objects.requireNonNull(strategy, "strategy must not be null");
        this.directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        getAllPaths().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getAllPaths().count();
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("I/O error while creating " + path, getFileName(path), e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("I/O error while writing ", getFileName(path), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("I/O error while deleting", getFileName(path), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("I/O error while reading", getFileName(path), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        return getAllPaths()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    protected Path getSpecificSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected boolean isExists(Path path) {
        return Files.isRegularFile(path);
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getAllPaths() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("I/O error with directory", e);
        }
    }
}
