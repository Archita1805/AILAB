% Disease definitions: disease(Name, [key_symptoms])
disease(flu, [fever, cough, body_ache]).
disease(common_cold, [sneezing, cough, sore_throat]).
disease(malaria, [fever, chills, sweating, headache]).
disease(allergy, [sneezing, itchy_eyes, runny_nose]).

% Patient symptoms facts (example)
patient_has_symptom(john, fever).
patient_has_symptom(john, cough).
patient_has_symptom(john, body_ache).

patient_has_symptom(mary, sneezing).
patient_has_symptom(mary, itchy_eyes).

% Helper: patient has all symptoms in a list
patient_has_all_symptoms(Patient, []).
patient_has_all_symptoms(Patient, [S|Rest]) :-
    patient_has_symptom(Patient, S),
    patient_has_all_symptoms(Patient, Rest).

% Exact diagnosis (all key symptoms present)
diagnose_exact(Patient, Disease) :-
    disease(Disease, KeySymptoms),
    patient_has_all_symptoms(Patient, KeySymptoms).

% Partial diagnosis: compute match count and disease with at least N matches
symptom_match_count(Patient, Disease, Count) :-
    disease(Disease, Symptoms),
    include({Patient}/[Sym]>>patient_has_symptom(Patient,Sym), Symptoms, Matched),
    length(Matched, Count).

% Suggest diseases with highest match
diagnose_suggest(Patient, Disease) :-
    findall(Count-D, (disease(D, _), symptom_match_count(Patient, D, Count)), Pairs),
    sort(Pairs, Sorted),
    reverse(Sorted, [MaxCount-Disease|_]),
    MaxCount > 0.

% Example queries
% ?- diagnose_exact(john, D).     % D = flu
% ?- symptom_match_count(mary, D, C). % results listing matches: e.g. common_cold 1, allergy 1
% ?- diagnose_suggest(mary, D).  % suggests diseases that match symptoms
