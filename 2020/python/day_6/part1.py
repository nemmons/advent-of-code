from typing import List
from functools import reduce

def process_input(file='input') -> List[list]:
    group_answers = []

    with open(file) as f:
        working_group_answers = []
        for line in f:
            if line == '\n' and len(working_group_answers) > 0:
                group_answers.append(list(set(working_group_answers)))
                working_group_answers = []
            else:
                working_group_answers += [char for char in line.rstrip()]

        if len(working_group_answers) > 0:
            group_answers.append(list(set(working_group_answers)))
    return group_answers

answers = process_input()
print(answers)
print(reduce(lambda count, lst: count+len(lst), answers, 0))
