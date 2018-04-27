package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage{

    protected List<Resume> storage = new ArrayList<>();


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
    public void clear() {

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
