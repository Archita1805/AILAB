% Facts about birds and properties
bird(sparrow).
bird(penguin).
bird(ostrich).
bird(eagle).

has_feathers(X) :- bird(X).

can_fly(sparrow).
can_fly(eagle).
% penguins and ostrich cannot fly
cannot_fly(penguin).
cannot_fly(ostrich).

swims(penguin).
runs(ostrich).
raptor(eagle).

% Classification rules
flightless(X) :- bird(X), cannot_fly(X).
aquatic_bird(X) :- bird(X), swims(X).
runnable_bird(X) :- bird(X), runs(X).
bird_of_prey(X) :- bird(X), raptor(X).
%swipl PROLOGBird.pl put ; to get the next entry
% Example queries
% ?- bird(X).                 % lists sparrow,penguin,ostrich,eagle
% ?- flightless(X).           % X = penguin ; X = ostrich
% ?- bird_of_prey(eagle).     % true
% ?- aquatic_bird(penguin).   % true
