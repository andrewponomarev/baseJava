package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Link homePage;

    private List<Position> positions;

    public Organization(String name, String url, Position... positions) {
        this.homePage = new Link(name, url);
        this.positions = new ArrayList<>(Arrays.asList(positions));
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

    public static class Position {

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(int startYear, Month startMonth, String title, String description) {
            this(LocalDate.of(startYear, startMonth, 1), NOW, title, description );
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(   LocalDate.of(startYear, startMonth, 1),
                    LocalDate.of(endYear, endMonth, 1)
                    , title, description );
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position that = (Position) o;

            if (!startDate.equals(that.startDate)) return false;
            if (!endDate.equals(that.endDate)) return false;
            if (!title.equals(that.title)) return false;
            return description != null ? description.equals(that.description) : that.description == null;

        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Position(" + startDate + ',' + endDate + ',' + title + ',' + description + ')';
        }

    }
}
