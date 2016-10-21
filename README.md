# Toll calculator

This was an exercise in the context of a coding challenge for a job interview.

The assignment was to "refactor the code" that has now been placed in the package `src/main/java/com/challenge10/toll/existing` and "express myself as much as possible".

## Solution

### How it was solved
* Create a maven project, create some tests and make sure the refactpred version of the toll calculator passes the same tests as the existing one
* Simplify and refactor what's tricky to interpret, e.g. the time range and the calculation logic byt using more recent Date API and Google Guava library to handle ranges
* Split logic in different classes (could have been in the same class) and unit test each piece of logic
* Simplify methods that are exposed out

### Technologies
Java 8, Maven 3

### Run the tests
```
mvn clean test
```

## Some possible improvements
This was a test interview so I could not spend that much time on it, but here's a list of possible improvements...
* Handle timezone
* Split into multi-module project, eventually create a common interface between (could not change existing code)
* Tests can be improved for sure, possibly using BDD
* I chose to not use pure OO approach by making everything static to ensure stateless behavior, since the exercise is meant to be simple

