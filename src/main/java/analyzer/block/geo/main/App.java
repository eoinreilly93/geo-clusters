package analyzer.block.geo.main;

import analyzer.block.geo.result.GeoResult;

import java.io.IOException;

public class App {

  public static void main(final String[] args) throws IOException {
    final GeoBlockAnalyzer geoBlockAnalyzer =
        new GeoBlockAnalyzer(10000, 10000, "src/main/resources/geosLarge.csv");
    final long start = System.currentTimeMillis();
    final GeoResult result = geoBlockAnalyzer.getLargestGeoBlock();
    final long end = System.currentTimeMillis();

    System.out.println("Run time: " + (end - start) + " ms");
    System.out.println(result.toString());
  }
}
