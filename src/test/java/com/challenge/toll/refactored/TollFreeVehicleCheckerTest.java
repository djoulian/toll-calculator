package com.challenge.toll.refactored;

import com.challenge.toll.refactored.model.Car;
import com.challenge.toll.refactored.model.Motorbike;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.challenge.toll.refactored.TollFreeVehicleChecker.isTollFree;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TollFreeVehicleCheckerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TollFreeVehicleCheckerTest.class);

    @Test
    public void testWeekends() {
        assertFalse(isTollFree(new Car()));
        assertTrue(isTollFree(new Motorbike()));
    }

}
