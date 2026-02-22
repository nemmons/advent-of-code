import re
from dataclasses import dataclass
from typing import List
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(5)
    thing = sum_middle_page_numbers_of_correct_updates(test_input)
    assert thing == 143, f'Expected 143, got {thing}'


def solve_part1():
    actual_input = get_input(5)
    result = sum_middle_page_numbers_of_correct_updates(actual_input)
    print(f'Part 1: {result}')


type Pages = list[list[int]]


@dataclass
class Rule:
    comes_after: set[int]
    comes_before: set[int]


type Rules = dict[int, Rule]


rule_pattern = r'(\d+)\|(\d+)'


def get_pages_and_rules(input_lines: List[str]) -> tuple[Rules, Pages]:
    rules = dict()
    pages = list()

    for line in input_lines:
        rule_result = re.match(rule_pattern, line.strip())
        if rule_result:
            first, second = rule_result.groups()
            if first not in rules:
                rules[first] = Rule(set(), set(second))
            else:
                rules[first].comes_before.add(second)
            if second not in rules:
                rules[second] = Rule(set(first), set())
            else:
                rules[second].comes_after.add(first)
        else:
            pages.append(list(map(lambda s: int(s), line.strip().split(','))))

    return rules, pages


def sum_middle_page_numbers_of_correct_updates(input: List[str]):
    rules, pages = get_pages_and_rules(input)

    result = 0

    for page in pages:



    return 1




# def test_part2():
#     test_input = read_test_input(5)
#     result = do_the_thing_part_2(test_input)
#     assert score == 0, f'Expected 0, got {result}'

# def solve_part2():
#     actual_input = get_input(5)
#     result = do_the_thing_part_2(actual_input)
#     print(f'Part 2: {result}')
# def do_the_thing_part_2(input: List[str]):
#     return 1

test_part1()
solve_part1()

# test_part2()
# solve_part2()
