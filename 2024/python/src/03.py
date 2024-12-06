import re
from typing import List
from utils.api import get_input, read_test_input

mul_pattern = r'mul\((\d+),(\d+)\)'


def test_part1():
    test_input = read_test_input(3)
    thing = calculate_sum_of_products(test_input)
    assert thing == 161, f'Expected 161, got {thing}'


def solve_part1():
    actual_input = get_input(3)
    result = calculate_sum_of_products(actual_input)
    print(f'Part 1: {result}')


def calculate_sum_of_products(input: List[str]):
    total = 0
    for line in input:
        results = re.findall(mul_pattern, line)
        for a, b in results:
            total += int(a) * int(b)
    return total


def test_part2():
    test_input = read_test_input(3)
    result = calculate_conditional_sum_of_products(test_input)
    assert result == 48, f'Expected 48, got {result}'


def solve_part2():
    actual_input = get_input(3)
    result = calculate_conditional_sum_of_products(actual_input)
    print(f'Part 2: {result}')


def calculate_conditional_sum_of_products(input: List[str]):
    return 1

test_part1()
solve_part1()

test_part2()
solve_part2()
