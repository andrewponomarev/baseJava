package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();


    @Override
    public void clear() {

    }

    @Override
    protected Object getKey(String uuid) {
        return null;
    }

    @Override
    protected void doUpdate(Resume r, Object key) {

    }

    @Override
    protected boolean isExist(Object key) {
        return false;
    }

    @Override
    protected void doSave(Resume r, Object key) {

    }

    @Override
    protected Resume doGet(Object key) {
        return null;
    }

    @Override
    protected void doDelete(Object key) {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
