package com.challenge.toll.refactored.model;

// Not sure we need an interface here, given what's needed we could have Vehicle be an enum directly
public interface Vehicle {

    // previous keyword public was unnecessary since the interface is public
    VehicleType getType();
}
