package com.cache.core;

import java.util.Objects;

public class MapName {

    private String name;

    public MapName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapName mapName = (MapName) o;
        return Objects.equals(name, mapName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
