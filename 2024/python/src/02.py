from collections import Counter
from typing import List
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(2)
    count = count_safe_reports(test_input)
    assert count == 2, f'Expected 2, got {count}'


def solve_part1():
    actual_input = get_input(2)
    result = count_safe_reports(actual_input)
    print(f'Safe Report Count: {result}')


def count_safe_reports(input: List[str]):
    safe_count = 0
    for line in input:
        values = list(map(lambda x: int(x), line.strip().split(' ')))

        if is_safe(values):
            safe_count += 1
    return safe_count


def test_part2():
    test_input = read_test_input(2)
    count = count_safe_reports_with_problem_damper(test_input)
    assert count == 4, f'Expected 4, got {count}'


def solve_part2():
    actual_input = get_input(2)
    result = count_safe_reports_with_problem_damper(actual_input)
    print(f'Safe Report Count via Problem Damper: {result}')


def count_safe_reports_with_problem_damper(input: List[str]):
    safe_count = 0
    for line in input:
        values = list(map(lambda x: int(x), line.strip().split(' ')))
        if is_safe(values):
            safe_count += 1
            continue

        for removal_index in range(0, len(values)):
            if is_safe(values[:removal_index] + values[removal_index+1:]):
                safe_count += 1
                break
    return safe_count


def is_safe(values: List[int]) -> bool:
    diff = values[0] - values[1]
    if diff in [1, 2, 3]:
        level_direction = 1
    elif diff in [-1, -2, -3]:
        level_direction = -1
    else:
        return False

    for i in range(2, len(values)):
        diff = values[i - 1] - values[i]
        if level_direction == 1 and diff in [1, 2, 3]:
            continue
        elif level_direction == -1 and diff in [-1, -2, -3]:
            continue
        else:
            return False

    return True


test_part1()
solve_part1()

test_part2()
solve_part2()
