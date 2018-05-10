package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUuidStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap<>();


    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected boolean isExist(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        storage.putIfAbsent((String) key, r);
    }

    @Override
    protected Resume doGet(Object key) {
      return storage.get(key);
    }

    @Override
    protected void doDelete(Object key) {
        storage.remove(key);
    }


    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
