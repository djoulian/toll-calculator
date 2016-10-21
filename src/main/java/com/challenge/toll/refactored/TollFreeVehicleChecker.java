package com.challenge.toll.refactored;

import com.challenge.toll.refactored.model.Vehicle;
import com.challenge.toll.refactored.model.VehicleType;
import com.google.common.collect.Sets;

import java.util.Set;

import static com.challenge.toll.refactored.model.VehicleType.*;

final class TollFreeVehicleChecker {

    private TollFreeVehicleChecker() {}

    private static final Set<VehicleType> TOLL_FREE_VEHICLES = Sets.newHashSet(
            MOTORBIKE, TRACTOR, EMERGENCY, DIPLOMAT, FOREIGN, MILITARY);

    static boolean isTollFree(Vehicle vehicle) {
        return TOLL_FREE_VEHICLES.contains(vehicle.getType());
    }

}
