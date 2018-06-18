package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;

    private List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = Arrays.asList(positions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return positions.equals(that.positions);

    }

    public Organization withPosition(Position position) {
        positions.add(position);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positions, homePage);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions + '\'' +
                '}';
    }
}
