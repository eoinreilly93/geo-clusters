package analyzer.block.geo.main;

import analyzer.block.geo.result.GeoResult;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeoBlockAnalyzerTest {

    private GeoBlockAnalyzer geoBlockAnalyzer;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        this.geoBlockAnalyzer = new GeoBlockAnalyzer(10000, 10000, "src/main/resources/geos.csv");
    }

    @org.junit.jupiter.api.Test
    void getLargestGeoBlock() {
        final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
        System.out.println(result.toString());

    }
}