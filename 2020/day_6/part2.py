from typing import List
from functools import reduce

def process_input(file='input') -> List[list]:
    group_answers = []

    with open(file) as f:
        working_group_answers = []
        new_group = True
        for line in f:
            person_answers = [char for char in line.rstrip()]
            if line == '\n' and not new_group:
                group_answers.append(working_group_answers)
                working_group_answers = []
                new_group = True
                continue
            elif len(working_group_answers) == 0 and new_group:
                working_group_answers = person_answers
            else:
                working_group_answers = list(set(person_answers) & set(working_group_answers))
            new_group = False
        if len(working_group_answers) > 0:
            group_answers.append(working_group_answers)
    return group_answers

answers = process_input()
print(answers)
print(reduce(lambda count, lst: count+len(lst), answers, 0))
