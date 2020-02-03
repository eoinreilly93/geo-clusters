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
import java.util.*;
import java.util.List;

public class GeoBlockAnalyzer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final int width;
    private final int height;
    private final String csvFilePath;
    private GeoResult result = new GeoResult();

    // Map of the geo id and respective geo object
    private final Map<Integer, Geo> geoMap = new HashMap<>();
    // Map of coordinates to each geo in the grid
    private final Map<Point, Geo> coordMap = new HashMap<>();

    /**
     * Constructs a geo grid of the given width and height, populated with the geo data provided in the csv file
     *
     * @param width the width of the grid
     * @param height the height of the grid
     * @param csvFilePath the csv file containing the geo data
     * @throws IOException
     */
    public GeoBlockAnalyzer(final int width, final int height, final String csvFilePath) throws IOException {

        if(!Files.exists(Paths.get(csvFilePath)) || Files.isDirectory(Paths.get(csvFilePath))) {
            throw new FileNotFoundException(csvFilePath);
        }

        if(width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Input height or width is 0 or smaller");
        }

        this.width = width;
        this.height = height;
        this.csvFilePath = csvFilePath;

        populateGeoGrid();
        populateGeoMap();
        calculateGeoNeighbours();
        //printNeighbours();
    }

    /**
     * @return the largest geo block in the input grid
     */
    public GeoResult getLargestGeoBlock() {
        for(final Geo geo : this.geoMap.values()) {
            final List<Geo> visited = new ArrayList<>();
            search(geo, visited);
        }
        return this.result;
    }

    /**
     * Iterative DFS implementation to find largest geo block.
     *
     * @param geo the geo to be evaluated
     * @param visited list of visited geos
     */
    private void search(Geo geo, final List<Geo> visited) {
        final Deque<Geo> stack = new LinkedList<>();
        stack.push(geo);
        while(!stack.isEmpty()) {
            geo = stack.pop();
            if(visited.contains(geo)) {
                continue;
            }
            visited.add(geo);

            final List<Geo> neighbours = geo.getNeighbours();
            for(int i = neighbours.size() - 1; i >= 0; i--) {
                final Geo g = neighbours.get(i);
                if(!visited.contains(g)) {
                    stack.push(g);
                }
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
                    //Remove leading and trailing whitespace
                    geoData[i] = geoData[i].replace(" ", "");

                    if (geoData[i].isEmpty() || geoData.length > 3) {
                        throw new IllegalArgumentException("There is missing data in the csv file at line: " + lineNumber);
                    }
                }
                try {
                    dateOccupied = LocalDate.parse(geoData[2], formatter);
                }
                catch (final DateTimeParseException e) {
                    throw new IllegalArgumentException("There input date is invalid on line: " + lineNumber);
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
