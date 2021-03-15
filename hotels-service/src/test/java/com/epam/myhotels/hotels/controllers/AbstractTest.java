package com.epam.myhotels.hotels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class AbstractTest {

    protected static EasyRandomParameters parameters = new EasyRandomParameters().seed(123L).objectPoolSize(100)
                                                                                 .randomizationDepth(3)
                                                                                 .charset(StandardCharsets.UTF_8)
                                                                                 .timeRange(LocalTime
                                                                                         .of(9, 0), LocalTime.of(17, 0))
                                                                                 .dateRange(LocalDate.now(), LocalDate
                                                                                         .now().plusDays(1))
                                                                                 .stringLengthRange(5, 50)
                                                                                 .collectionSizeRange(1, 5)
                                                                                 .scanClasspathForConcreteTypes(true)
                                                                                 .overrideDefaultInitialization(false)
                                                                                 .ignoreRandomizationErrors(true);
    protected static EasyRandom easyRandom = new EasyRandom(parameters);

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() {
        Assertions.assertTrue(true);
    }
}