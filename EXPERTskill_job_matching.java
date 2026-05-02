% Person skills
has_skill(alice, python).
has_skill(alice, sql).
has_skill(alice, machine_learning).
has_skill(bob, java).
has_skill(bob, sql).
has_skill(charlie, html).
has_skill(charlie, css).
has_skill(charlie, javascript).

% Job requirements: job(Name, [skills_required])
job(ml_engineer, [python, machine_learning, sql]).
job(backend_dev, [java, sql]).
job(frontend_dev, [html, css, javascript]).

% Helper: subset of required skills present in person's skills
has_all_skills(_, []).
has_all_skills(Person, [Skill|Rest]) :-
    has_skill(Person, Skill),
    has_all_skills(Person, Rest).

% Match rule
match_job(Person, Job) :-
    job(Job, ReqSkills),
    has_all_skills(Person, ReqSkills).

%swipl file_name.pl
% Example queries
% ?- match_job(alice, J).     % J = ml_engineer
% ?- match_job(bob, backend_dev). % true
% ?- match_job(charlie, frontend_dev). % true
% ?- match_job(X, ml_engineer). % X = alice
