% Gender facts
male(john).
male(david).
male(raj).

female(mary).
female(susan).
female(rita).

% Parent relationships
parent(john, mary).
parent(john, david).
parent(mary, susan).
parent(mary, raj).
parent(rita, mary).

% X is the father of Y if X is male and X is a parent of Y
father(X, Y) :- male(X), parent(X, Y).

% X is the mother of Y if X is female and X is a parent of Y
mother(X, Y) :- female(X), parent(X, Y).

% X is grandparent of Y if X is parent of Z and Z is parent of Y
grandparent(X, Y) :- parent(X, Z), parent(Z, Y).

% X is sibling of Y if they share the same parent and are not the same person
sibling(X, Y) :- parent(Z, X), parent(Z, Y), X \= Y.

% brother and sister
brother(X, Y) :- male(X), sibling(X, Y).
sister(X, Y) :- female(X), sibling(X, Y).

%querires
%sibling(Mary,X).
%parent(john,Y).
