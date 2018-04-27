package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static java.util.Objects.requireNonNull;

public abstract class AbstractStorage implements Storage{

    protected abstract Object getKey(String uuid);

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract boolean isExist(Object key);

    protected abstract void doSave(Resume r, Object key);

    protected abstract Resume doGet(Object key);

    protected abstract void doDelete(Object key);

    @Override
    public void update(Resume r) {
        requireNonNull(r, "Resume is null");
        Object key = getExistedKey(r.getUuid());
        doUpdate(r, key);
    }

    @Override
    public void save(Resume r) {
        requireNonNull(r, "Resume is null");
        Object key = getNotExistedKey(r.getUuid());
        doSave(r, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExistedKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExistedKey(uuid);
        doDelete(key);
    }

    private Object getExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}