package com.vedam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Weather {
    private static class DayMinSpreadPair {
        int day;
        double minSpread;
        DayMinSpreadPair(int day, double minSpread) {
            this.day = day;
            this.minSpread = minSpread;
        }

        public double getMinSpread() {
            return minSpread;
        }
    };

    public static void main(String[] args) {
        Path path;
        if(args.length > 2) {
            path = Paths.get(args[1]);
            return;
        } else {
            System.out.print("Enter filename: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String pathname = reader.readLine();
                path = Paths.get(pathname);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            List<String> stream = Files.lines(path).collect(Collectors.toList());
            List<String> dataSet = stream.subList(2, stream.size() - 1);

            Predicate<String> isNumeric = (s) -> !s.isEmpty() && s.matches("^\\d+(\\.\\d+)?");
            Function<List<String>, DayMinSpreadPair> generateDayMinSpreadPair = (data) -> {
                double max = Double.parseDouble(data.get(1));
                double min = Double.parseDouble(data.get(2));
                double minSpread = max - min;
                return new DayMinSpreadPair(Integer.parseInt(data.get(0)), minSpread);
            };

            Optional<DayMinSpreadPair> minSpread = dataSet.stream().map((s) -> s.split("\\s+"))
                    .map((s) -> Arrays.stream(s).filter(isNumeric).collect(Collectors.toList()))
                    .map(generateDayMinSpreadPair).min(Comparator.comparing(DayMinSpreadPair::getMinSpread));

            if(minSpread.isPresent()) {
                System.out.println(minSpread.get().day);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}