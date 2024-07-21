package com.crowdfund.projects.microservices.common.code.util;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author Manjunath Asundi
 */
public final class UniqueIDGenerator {

    private static final int NO_OF_UUIDS = 7;

    private UniqueIDGenerator() {

    }

    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        IntStream.range(0, NO_OF_UUIDS).forEach(i -> System.out.println(UniqueIDGenerator.generateUniqueID()));
    }
}
