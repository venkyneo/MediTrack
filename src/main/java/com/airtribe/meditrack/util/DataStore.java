package com.airtribe.meditrack.util;

import com.airtribe.meditrack.interfaces.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic DataStore for any Identifiable entity.
 * Eliminates code duplication across Patient, Doctor, Appointment storage.
 * T must implement Identifiable to guarantee getId() is available.
 */
public class DataStore<T extends Identifiable> {

    private final Map<String, T> store = new HashMap<>();

    public void add(T item) {
        store.put(item.getId(), item);
    }

    public T findById(String id) {
        return store.get(id);
    }

    public void remove(String id) {
        store.remove(id);
    }

    public void update(T item) {
        if (store.containsKey(item.getId())) {
            store.put(item.getId(), item);
        }
    }

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }

    public int size() {
        return store.size();
    }
}
