% Facts: plant(Name, edible_part, has_seeds, taste)
plant(apple, fruit, yes, sweet).
plant(tomato, fruit, yes, sweet).
plant(cucumber, fruit, yes, bland).
plant(carrot, root, no, sweet).
plant(potato, tuber, no, bland).
plant(banana, fruit, yes, sweet).
plant(strawberry, fruit, yes, sweet).

% Botanical vs culinary rules
is_fruit(Name) :- plant(Name, fruit, yes, _).
is_vegetable(Name) :- plant(Name, Part, _, _), Part \= fruit.

%commands - swipl file_name.pl
% Example queries
% ?- is_fruit(apple).         % true
% ?- is_vegetable(carrot).    % true
% ?- plant(X, root, _, _).    % X = carrot
