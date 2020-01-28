package analyzer.block.geo.model;

import java.awt.Point;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Geo {

    private final int id;
    private final String name;
    private final LocalDate dateOccupied;
    private final Point coordinate;
    private final List<Geo> neighbours = new ArrayList<>();

    public Geo (final int id, final String name, final LocalDate dateOccupied) {
        this.id = id;
        this.name = name;
        this.dateOccupied = dateOccupied;
        this.coordinate = new Point();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDateOccupied() {
        return this.dateOccupied;
    }

    public void setCoordinates(final int x, final int y) {
        this.coordinate.setLocation(x, y);
    }

    public Point getCoordinates() {
        return this.coordinate;
    }

    public String toString() {
        return this.id + ", " + this.name + ", " + this.dateOccupied;
    }

    public List<Geo> getNeighbours() {
        return this.neighbours;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.dateOccupied);
    }

    @Override
    public boolean equals(final Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()) {
           return false;
        }

        final Geo geo = (Geo) obj;
        return this.id == geo.getId() &&
                this.name == geo.getName() &&
                this.dateOccupied == geo.getDateOccupied();
    }
}
