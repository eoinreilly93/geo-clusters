package analyzer.block.geo.main;

import analyzer.block.geo.result.GeoResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GeoBlockAnalyzerTest {

  private GeoBlockAnalyzer geoBlockAnalyzer;

  @Test
  void getLargestGeoBlockTestOne() throws IOException {
    this.geoBlockAnalyzer = new GeoBlockAnalyzer(4, 7, "src/test/resources/geosSmall.csv");
    final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
    Assertions.assertEquals(4, result.getSize());
  }

  @Test
  void getLargestGeoBlockTestTwo() throws IOException {
    this.geoBlockAnalyzer = new GeoBlockAnalyzer(7, 4, "src/test/resources/geosSmall.csv");
    final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
    Assertions.assertEquals(5, result.getSize());
  }

  @Test
  void getLargestGeoBlockTestLarge() throws IOException {
    this.geoBlockAnalyzer = new GeoBlockAnalyzer(10000, 10000, "src/test/resources/geosLarge.csv");
    final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
    Assertions.assertEquals(8, result.getSize());
  }

  @Test
  void missingDataExceptionTest() throws IOException {
    try {
      this.geoBlockAnalyzer =
          new GeoBlockAnalyzer(100, 300, "src/test/resources/geosWithMissingData.csv");
    } catch (final IllegalArgumentException e) {
      Assertions.assertTrue(
          e.getMessage().contains("There is missing data in the csv file at line: 4"));
    }
  }

  @Test
  void invalidInputExceptionTest() throws IOException {
    try {
      this.geoBlockAnalyzer = new GeoBlockAnalyzer(0, 5, "src/test/resources/geosSmall.csv");
    } catch (final IllegalArgumentException e) {
      Assertions.assertTrue(e.getMessage().contains("Input height or width is 0 or smaller"));
    }

    try {
      this.geoBlockAnalyzer = new GeoBlockAnalyzer(-1, -8, "src/test/resources/geosSmall.csv");
    } catch (final IllegalArgumentException e) {
      Assertions.assertTrue(e.getMessage().contains("Input height or width is 0 or smaller"));
    }
  }
}
