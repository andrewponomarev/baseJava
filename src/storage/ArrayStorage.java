package storage;

import model.Resume;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected int getIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void addToStorage(Resume r, int index) {
        storage[size++] = r;
    }

    @Override
    protected void removeFromStorage(int index) {
        storage[index] = storage[--size];
        storage[size] = null;
    }

}
