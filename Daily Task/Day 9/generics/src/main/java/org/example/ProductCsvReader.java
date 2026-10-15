package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ProductCsvReader {

    public static void readProducts() throws IOException {
        InputStream input = ProductCsvReader.class
                .getResourceAsStream("/products.csv");

        if (input == null) {
            throw new IOException("products.csv was not found");
        }

        try (input;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(input, StandardCharsets.UTF_8))) {

            String line;
            boolean header = true;

            while ((line = reader.readLine()) != null) {
                if (header) {
                    header = false;
                    continue;
                }
                System.out.println(line);
            }
        }
    }
}
