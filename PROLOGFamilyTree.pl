% ── Facts: gender ─────────────────────────────────────────────
male(shailesh).
male(siddheshwar).
male(amol).

female(seema).
female(manisha).
female(akanksha).
female(jyoti).
female(vaishnavi).
female(you).        % change to male(you) if needed

% ── Facts: parent(Parent, Child) ──────────────────────────────
% Paternal grandparents → their children
parent(siddheshwar, shailesh).   parent(manisha, shailesh).
parent(siddheshwar, amol).       parent(manisha, amol).
parent(siddheshwar, akanksha).   parent(manisha, akanksha).

% Your parents → you
parent(shailesh, you).           parent(seema, you).

% Mom's sister → her daughter (your cousin)
parent(jyoti, vaishnavi).

% ── Rules ─────────────────────────────────────────────────────
father(X, Y)      :- parent(X, Y), male(X).
mother(X, Y)      :- parent(X, Y), female(X).

grandparent(X, Y) :- parent(X, Z), parent(Z, Y).
grandfather(X, Y) :- grandparent(X, Y), male(X).
grandmother(X, Y) :- grandparent(X, Y), female(X).

sibling(X, Y)     :- parent(P, X), parent(P, Y), X \= Y.
brother(X, Y)     :- sibling(X, Y), male(X).
sister(X, Y)      :- sibling(X, Y), female(X).

uncle(X, Y)       :- brother(X, P), parent(P, Y).
aunt(X, Y)        :- sister(X, P), parent(P, Y).

ancestor(X, Y)    :- parent(X, Y).
ancestor(X, Y)    :- parent(X, Z), ancestor(Z, Y).

cousin(X, Y)      :- parent(Px, X), parent(Py, Y),
                      sibling(Px, Py), X \= Y.

%commands to run- swipl PROLOGFamilyTree.pl
%to stop - halt.
% ── Sample queries ────────────────────────────────────────────
% ?- father(X, you).           → shailesh
% ?- mother(X, you).           → seema
% ?- grandparent(X, you).      → siddheshwar, manisha
% ?- uncle(X, you).            → amol
% ?- aunt(X, you).             → akanksha, jyoti
% ?- cousin(X, you).           → vaishnavi
% ?- sibling(X, you).          → false (no siblings)
% ?- ancestor(X, you).         → shailesh, seema, siddheshwar, manisha
