package com.challenge.toll.refactored.model;

public class UseCase {
    private String[] dates;
    private Vehicle vehicle;
    // only doing this so we can test both calculators in same test suite, this is not a standard
    private com.challenge.toll.existing.Vehicle oldVehicle;
    private int expected;

    UseCase(Builder builder) {
        this.dates = builder.dates;
        this.vehicle = builder.vehicle;
        this.oldVehicle = builder.oldVehicle;
        this.expected = builder.expected;
    }

    @Override
    public String toString() {
        return String.format("Use case[vehicle=%s, date=%s, expected=%d]", vehicle == null ? null : vehicle.getType(), dates, expected);
    }

    public String[] getDates() {
        return dates;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public com.challenge.toll.existing.Vehicle getOldVehicle() {
        return oldVehicle;
    }

    public int getExpected() {
        return expected;
    }

    public static class Builder {
        private String[] dates;
        private Vehicle vehicle;
        private com.challenge.toll.existing.Vehicle oldVehicle;
        private int expected;

        public Builder date(String...dates) {
            this.dates = dates;
            return this;
        }

        public Builder vehicle(Vehicle vehicle, com.challenge.toll.existing.Vehicle oldVehicle) {
            this.vehicle = vehicle;
            this.oldVehicle = oldVehicle;
            return this;
        }

        public Builder expect(int expected) {
            this.expected = expected;
            return this;
        }

        public UseCase build() {
            return new UseCase(this);
        }
    }
}