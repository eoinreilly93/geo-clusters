package analyzer.block.geo.result;

import analyzer.block.geo.model.Geo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeoResult {

    private final List<Geo> geosInBlock = new ArrayList<>();

    public GeoResult() {
    }

    public GeoResult(final List<Geo> geosInBlock) {
        this.geosInBlock.addAll(geosInBlock);
    }

    public List<Geo> getGeosInBlock() {
        this.geosInBlock.sort(Comparator.comparingInt(Geo::getId));
        return this.geosInBlock;
    }

    public int getSize() {
        return this.geosInBlock.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("The geos in the largest cluster of occupied Geos for this GeoBlock are: \n");
        for(final Geo geo : this.geosInBlock) {
            sb.append(geo.toString()).append("\n");
        }
        return sb.toString();
    }
}
