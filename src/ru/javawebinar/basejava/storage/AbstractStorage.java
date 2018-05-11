package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class AbstractStorage<SK> implements Storage{

    protected static final int STORAGE_LIMIT = 10000;

    protected abstract SK getKey(String uuid);

    protected abstract void doUpdate(Resume r, SK key);

    protected abstract boolean isExist(SK key);

    protected abstract void doSave(Resume r, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract List<Resume> doCopyAll();

    @Override
    public void update(Resume r) {
        requireNonNull(r, "Resume is null");
        SK key = getExistedKey(r.getUuid());
        doUpdate(r, key);
    }

    @Override
    public void save(Resume r) {
        requireNonNull(r, "Resume is null");
        SK key = getNotExistedKey(r.getUuid());
        doSave(r, key);
    }

    @Override
    public Resume get(String uuid) {
        SK key = getExistedKey(uuid);
        return doGet(key);
    }

    @Override
    public void delete(String uuid) {
        SK key = getExistedKey(uuid);
        doDelete(key);
    }

    private SK getExistedKey(String uuid) {
        SK key = getKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getNotExistedKey(String uuid) {
        SK key = getKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> col = doCopyAll();
        Collections.sort(col);
        return col;
    }

}
