% Animals with trait lists
animal(lion, [mammal, carnivore, four_legged, fur, roar]).
animal(eagle, [bird, carnivore, fly, feathers, beak]).
animal(shark, [fish, carnivore, aquatic, gills]).
animal(cow, [mammal, herbivore, four_legged, hooves]).
animal(duck, [bird, aquatic, feathers, fly, beak]).

% subset predicate (helper)
subset([], _).
subset([H|T], List) :- member(H, List), subset(T, List).

% identify_by_traits(RequestedTraits, Animal)
identify_by_traits(Requested, Animal) :-
    animal(Animal, Traits),
    subset(Requested, Traits).

% identify/1 returns all animals exactly matching given traits (optional)
identify(Animal) :- animal(Animal, _).

% Example queries
% ?- identify_by_traits([bird, fly], A). % A = eagle ; A = duck
% ?- identify_by_traits([mammal, carnivore], A). % A = lion
% ?- identify(Animal). % lists all animals
