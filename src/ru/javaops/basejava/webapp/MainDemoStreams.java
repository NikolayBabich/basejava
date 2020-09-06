package ru.javaops.basejava.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class MainDemoStreams {
    private MainDemoStreams() {
    }

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        System.out.println(oddOrEven(Arrays.asList(9, 8)));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((r, n) -> r * 10 + n)
                .orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(i -> i % 2 != sum % 2)
                .collect(Collectors.toList());
    }
}
