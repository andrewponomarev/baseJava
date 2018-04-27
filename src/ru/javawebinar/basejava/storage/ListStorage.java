package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{

    protected List<Resume> storage = new ArrayList<>();


    @Override
    protected Integer getKey(String uuid) {
        int index = -1;
        for (Resume r : storage) {
            index++;
            if (uuid.equals(r.getUuid())) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.add((Integer) key, r);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key >= 0;
    }

    @Override
    protected void doSave(Resume r, Object key) {
        storage.add((Integer) key, r);
    }

    @Override
    protected Resume doGet(Object key) {
        return storage.get((Integer) key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove((Integer) key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] array = new Resume[storage.size()];
        for (int i = 0 ; i < storage.size(); i++) {
            array[i] = storage.get(i);
        }
        return array;
    }

    @Override
    public int size() {
        return 0;
    }
}
