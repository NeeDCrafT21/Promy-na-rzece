# Promy-na-rzece
Project made as a part of Concurrent Programming course at Military University of Technology using Java programing language and JavaFX.

## Project overview
Given number of ferries move between two marinas where they first unload cars from their decks and then retrieve new cars from a queue of waiting to be loaded cars.
Production of new cars is handled by two producers, each at a single marina. After production, car travels to a queue where it is inserted at it's end.
Ferries have a set period of time for at least one car to enter it's deck. If they fill up and are not past their stopping time they move on to the other marina. Ferries move to the other marina for a set period of time, then they queue up and move in the queue untill they are first in line where they they take care of unloading and loading the cars.
