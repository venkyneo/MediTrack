package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable<T> {
    T findById(String id);
    List<T> findByName(String name);

    default String formatSearchResult(String id, String name) {
        return "ID: " + id + " | Name: " + name;
    }
}
