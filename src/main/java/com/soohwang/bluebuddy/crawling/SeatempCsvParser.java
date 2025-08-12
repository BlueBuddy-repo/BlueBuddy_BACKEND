package com.soohwang.bluebuddy.crawling;

import com.soohwang.bluebuddy.entity.Seatemp;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeatempCsvParser {

    public List<Seatemp> parse(String filePath) {
        List<Seatemp> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                lineCount++;
                if (lineCount <= 2) continue;

                String[] tokens = line.split(",");
                if (tokens.length < 4) continue;

                String time = tokens[0];
                double lat = Double.parseDouble(tokens[1]);
                double lng = Double.parseDouble(tokens[2]);

                String tempStr = tokens[3];
                if (tempStr.equalsIgnoreCase("NaN")) continue;

                double temp = Double.parseDouble(tempStr);

                result.add(new Seatemp(lat, lng, temp, time));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
