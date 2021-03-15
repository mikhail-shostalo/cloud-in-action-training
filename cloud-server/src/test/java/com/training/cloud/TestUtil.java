package com.training.cloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

public final class TestUtil {
    private TestUtil() {
    }

    public static EasyRandom easyRandomInstance() {
        EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
                .seed(300)
                .stringLengthRange(3, 10)
                .excludeField(FieldPredicates.named("id"));
        return new EasyRandom(easyRandomParameters);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
