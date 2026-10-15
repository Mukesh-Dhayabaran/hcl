
import model.Asset;
import repository.CrudRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class AssetCsvLoader {

    public void load(CrudRepository<Asset, Integer> repository)
            throws IOException {

        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("assets.csv");

        if (input == null) {
            throw new IOException("Could not find assets.csv");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String line = reader.readLine();

            if (line == null) {
                throw new IOException("The assets.csv file is empty.");
            }

            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",", -1);

                if (values.length != 5) {
                    throw new IOException(
                            "Invalid CSV format at line " + lineNumber
                                    + ": " + line);
                }

                try {
                    int id = Integer.parseInt(values[0].trim());

                    if (id <= 0) {
                        throw new IOException(
                                "Asset ID must be positive at line "
                                        + lineNumber);
                    }

                    String assetTag = values[1].trim();
                    String assetName = values[2].trim();
                    String category = values[3].trim();
                    String status = values[4].trim()
                            .toUpperCase(Locale.ROOT);

                    if (assetTag.isEmpty()
                            || assetName.isEmpty()
                            || category.isEmpty()
                            || status.isEmpty()) {
                        throw new IOException(
                                "Empty asset field at line " + lineNumber);
                    }

                    Asset asset = new Asset(
                            id,
                            assetTag,
                            assetName,
                            category,
                            status
                    );

                    repository.save(asset);

                } catch (NumberFormatException exception) {
                    throw new IOException(
                            "Invalid asset ID at line " + lineNumber,
                            exception);
                }
            }
        }
    }
}
