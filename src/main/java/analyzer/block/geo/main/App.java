package analyzer.block.geo.main;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        GeoBlockAnalyzer geoBlockAnalyzer = new GeoBlockAnalyzer(4, 6, "src/main/resources/geos.csv");
        geoBlockAnalyzer.getLargestGeoBlock();
        long end = System.currentTimeMillis();

        System.out.println("Run time: " + (end - start) + " ms");
    }
}
