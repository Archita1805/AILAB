% Facts: vehicle(Name, Wheels, FuelType, Capacity)

vehicle(car1, 4, petrol, 5).
vehicle(car2, 4, diesel, 5).
vehicle(electric_car, 4, electric, 5).
vehicle(motorcycle1, 2, petrol, 2).
vehicle(truck1, 6, diesel, 2).
vehicle(bus1, 6, diesel, 40).
vehicle(bicycle1, 2, human, 1).

% -----------------------
% Classification Rules
% -----------------------

car(X) :-
    vehicle(X, Wheels, _, Capacity),
    Wheels =:= 4,
    Capacity >= 4.

motorcycle(X) :-
    vehicle(X, 2, _, _),
    \+ bicycle(X).

bicycle(X) :-
    vehicle(X, 2, human, _).

truck(X) :-
    vehicle(X, Wheels, diesel, Capacity),
    Wheels >= 4,
    Capacity < 20.

bus(X) :-
    vehicle(X, _, diesel, Capacity),
    Capacity >= 20.

electric_vehicle(X) :-
    vehicle(X, _, electric, _).

%?- car(car1).
%?- motorcycle(motorcycle1).
%?- truck(truck1).
%?- bus(bus1).
%?- bicycle(bicycle1).
%?- electric_vehicle(electric_car).
