package analyzer.block.geo.main;

import analyzer.block.geo.model.Geo;
import analyzer.block.geo.result.GeoResult;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeoBlockAnalyzerTest {

    private GeoBlockAnalyzer geoBlockAnalyzer;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @org.junit.jupiter.api.Test
    void getLargestGeoBlockTestOne() throws IOException {
//        final Geo geoOne= new Geo(13, " Matt", LocalDate.parse("2010-10-14", FORMATTER));
//        final Geo geoTwo = new Geo(17, " Patrick", LocalDate.parse("2011-03-10", FORMATTER));
//        final Geo geoThree = new Geo(21, " Catherine", LocalDate.parse("2011-02-25", FORMATTER));
//        final Geo geoFour = new Geo(22, " Michael", LocalDate.parse("2011-02-25", FORMATTER));

//        final List<Geo> expectedGeoBlock = Arrays.asList(geoOne, geoTwo, geoThree, geoFour);

        this.geoBlockAnalyzer = new GeoBlockAnalyzer(4, 7, "src/main/resources/geos.csv");
        final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
        assertEquals(4, result.getSize());
        //assertEquals(expectedGeoBlock, result.getGeosInBlock());

    }

    @org.junit.jupiter.api.Test
    void getLargestGeoBlockTestTwo() throws IOException {
        this.geoBlockAnalyzer = new GeoBlockAnalyzer(7, 4, "src/main/resources/geos.csv");
        final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
        assertEquals(5, result.getSize());
    }

    @org.junit.jupiter.api.Test
    void getLargestGeoBlockTestLarge() throws IOException {
        this.geoBlockAnalyzer = new GeoBlockAnalyzer(10000, 10000, "src/main/resources/geosLarge.csv");
        final GeoResult result = this.geoBlockAnalyzer.getLargestGeoBlock();
        //assertEquals(5, result.getSize());
    }
}