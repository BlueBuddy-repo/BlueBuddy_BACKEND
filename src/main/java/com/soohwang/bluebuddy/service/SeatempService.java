package com.soohwang.bluebuddy.service;

import com.soohwang.bluebuddy.dto.SeatempDto;
import com.soohwang.bluebuddy.entity.Seatemp;
import com.soohwang.bluebuddy.repository.SeatempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatempService {

    private final SeatempRepository repository;

    public void saveAll(List<Seatemp> points) {
        repository.saveAll(points);
    }

    public List<SeatempDto> getAllDto() {
        return repository.findAll().stream()
                .map(r -> new SeatempDto(
                        r.getLat(),
                        r.getLng(),
                        convertToLevel(r.getTemp()),
                        r.getTime()

                ))
                .toList();
    }


    private int convertToLevel(double temp) {
        if (temp < 0.0) return 0;
        else if (temp < 10.0) return 1;
        else if (temp < 20.0) return 2;
        else if (temp < 28.0) return 3;
        else return 4;
    }


    public void saveFromCSV(String filePath) {
        List<Seatemp> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains("UTC") || line.contains("degree") || line.contains("missing") || line.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = line.split(",");

                if (tokens.length < 4 || tokens[3].isEmpty()) continue;

                try {
                    double latitude = Double.parseDouble(tokens[1]);
                    double longitude = Double.parseDouble(tokens[2]);
                    double temperature = Double.parseDouble(tokens[3]);
                    String time = tokens[0];

                    dataList.add(new Seatemp(latitude, longitude, temperature, time));
                } catch (NumberFormatException e) {
                    System.err.println("파싱 실패, 스킵된 행: " + line);
                }
            }

            repository.saveAll(dataList);
        } catch (Exception e) {
            System.err.println("CSV 파싱 실패 : " + e.getMessage());
        }
    }




}

