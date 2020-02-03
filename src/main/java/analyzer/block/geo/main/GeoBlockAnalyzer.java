package analyzer.block.geo.main;

import analyzer.block.geo.model.Geo;
import analyzer.block.geo.result.GeoResult;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoBlockAnalyzer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy-MM-dd");
    private final int width;
    private final int height;
    private final String csvFilePath;
    private final Map<Integer, Geo> geoMap = new HashMap<>();
    private final Map<Point, Geo> coordMap = new HashMap<>();
    private GeoResult result = new GeoResult();

    public GeoBlockAnalyzer(final int width, final int height, final String csvFilePath) throws IOException {

        if(!Files.exists(Paths.get(csvFilePath)) || Files.isDirectory(Paths.get(csvFilePath))) {
            throw new FileNotFoundException(csvFilePath);
        }

        if(width == 0 || height == 0) {
            throw new IllegalArgumentException("Input height or width is 0");
        }

        this.width = width;
        this.height = height;
        this.csvFilePath = csvFilePath;

        populateGeoGrid();
        populateGeoMap();
        calculateGeoNeighbours();
        //printNeighbours();
    }

    public GeoResult getLargestGeoBlock() {
        for(final Geo geo : this.geoMap.values()) {
            final List<Geo> visited = new ArrayList<>();
            search(geo, visited);
        }
        return this.result;
    }

    private void search(final Geo geo, final List<Geo> visited) {
        visited.add(geo);
        for(final Geo g : geo.getNeighbours()) {
            if(!visited.contains(g)) {
                search(g, visited);
            }
        }

        if(this.result.getSize() < visited.size()) {
            this.result = new GeoResult(visited);
        }
    }

    private void populateGeoGrid() throws IOException {
        try (final BufferedReader br = Files.newBufferedReader(Paths.get(this.csvFilePath))) {
            int lineNumber = 0;
            String line = "";
            while((line = br.readLine()) != null) {
                lineNumber++;
                final String[] geoData = line.split(",");
                LocalDate dateOccupied = null;

                //Handle for empty csv cells
                for(int i = 0; i < geoData.length; i++) {
                    if (geoData[i].isEmpty() || geoData.length > 3) {
                        throw new IllegalArgumentException("There is missing data in the csv file at line: " + lineNumber);
                    }

                    try {
                        dateOccupied = LocalDate.parse(geoData[2], formatter);
                    }
                    catch (final DateTimeParseException e) {
                        //throw new IllegalArgumentException("There input date is invalid on line: " + lineNumber);
                        dateOccupied = LocalDate.now();
                    }
                }
                this.geoMap.put(Integer.parseInt(geoData[0]), new Geo(Integer.parseInt(geoData[0]), geoData[1], dateOccupied));
            }
        }
    }

    private void populateGeoMap() {
        // Using the geo id, calculate its point on the grid
        for (int i = this.height - 1; i >= 0; i--) {
            int blockId = (i * this.width);
            for (int j = 0; j < this.width; j++) {
                if(this.geoMap.containsKey(blockId)) {
                    final Geo geo = this.geoMap.get(blockId) ;
                    geo.setCoordinates(i,j);
                    this.coordMap.put(geo.getCoordinates(), geo);
                }
                blockId++;
            }
        }
    }

    private void calculateGeoNeighbours() {
        for (final Geo geo : this.geoMap.values()) {
            addNeighboursToGeo(geo);
        }
    }

    private void addNeighboursToGeo(final Geo geo) {
        final int x = geo.getCoordinates().x;
        final int y = geo.getCoordinates().y;

        final Point[] tempArray = {
                                new Point(x, y+1),
            new Point(x-1, y),                  new Point(x+1, y),
                                new Point(x, y-1)
        };

        Geo g;
        for (final Point p : tempArray) {
            if (this.coordMap.containsKey(p)) {
                g = this.coordMap.get(p);
                if(g != null) {
                    geo.getNeighbours().add(g);
                }
            }
        }
    }

    private void printNeighbours() {
        for (final Geo geo : this.geoMap.values()) {
            System.out.println("Geo " + geo.getId() + " has the following neighbours: ");
            for(final Geo g : geo.getNeighbours()) {
                System.out.println(g.getId());
            }
        }
    }
}
