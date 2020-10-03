package analyzer.block.geo.main;

import analyzer.block.geo.result.GeoResult;

import java.io.IOException;

public class App {

  public static void main(final String[] args) throws IOException {
    if (args.length > 3) {
      throw new IllegalArgumentException(
          "You provided too many arguments. You need to provide the GeoBlock width, height and csv file");
    } else {
      final GeoBlockAnalyzer geoBlockAnalyzer =
          new GeoBlockAnalyzer(Integer.parseInt(args[0]), Integer.parseInt(args[1]), args[2]);
      final GeoResult result = geoBlockAnalyzer.getLargestGeoBlock();
      System.out.println(result.toString());
    }
  }
}
