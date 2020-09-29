package analyzer.block.geo.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TestDataGenerator {

  public static void main(final String[] args) throws IOException {

    final Random rand = new Random();
    final Path path = Paths.get("src/main/resources/geostest.csv");
    final List<String> names =
        List.of(
            "Tom", "Katie", "Mary", "Tony", "Bill", "Harry", "Greg", "Laura", "Dee", "Shane",
            "Paul", "Michael", "Isla", "Julia", "Bill", "Josie");
    final FileWriter fileWriter = new FileWriter(path.toFile());
    final PrintWriter printWriter = new PrintWriter(fileWriter);
    final Map<Integer, String> numList = new HashMap<>();

    for (int i = 0; i < 10000; i++) {
      // System.out.println(i);
      int number = rand.nextInt(100000);
      while (numList.containsKey(number)) {
        number = rand.nextInt(100000);
      }
      numList.put(number, String.valueOf(number));
      final StringBuilder sb = new StringBuilder();
      sb.append(number)
          .append(",")
          .append(names.get(rand.nextInt(names.size())))
          .append(",")
          .append(LocalDate.now())
          .append(System.lineSeparator());
      // Files.write(path, sb.toString().getBytes());
      printWriter.write(sb.toString());
    }
    printWriter.close();
    System.out.println("Exiting...");
  }
}
