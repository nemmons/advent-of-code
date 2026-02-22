from typing import List, Dict

from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(1)
    password = calculate_password(test_input)
    assert password == 3, f'Expected 3, got {password}'

dial_initial_position = 50

def solve_part1():
    actual_input = get_input(1)
    result = calculate_password(actual_input)
    print(f'The password is {result}')


def calculate_password(input: List[str]):
    zeroes_count = 0

    position = dial_initial_position

    for line in input:
        direction = line[0]
        amount = int(line[1:])

        if direction == "L":
            position = (position - amount) % 100
        elif direction == "R":
            position = (position + amount) % 100
        else:
            raise Exception(f'Unknown direction {direction}')

        if position == 0:
            zeroes_count += 1

    return zeroes_count

def test_part2():

    score2 = calculate_password_2(["L50"])
    assert score2 == 1, f'Expected 1, got {score2}'

    score2 = calculate_password_2(["R50"])
    assert score2 == 1, f'Expected 1, got {score2}'

    score2 = calculate_password_2(["L100"])
    assert score2 == 1, f'Expected 1, got {score2}'

    score2 = calculate_password_2(["R100"])
    assert score2 == 1, f'Expected 1, got {score2}'

    score2 = calculate_password_2(["L150"])
    assert score2 == 2, f'Expected 2, got {score2}'

    score2 = calculate_password_2(["R150"])
    assert score2 == 2, f'Expected 2, got {score2}'

    score2 = calculate_password_2(["L1000"])
    assert score2 == 10, f'Expected 10, got {score2}'

    score2 = calculate_password_2(["R1000"])
    assert score2 == 10, f'Expected 10, got {score2}'

    score2 = calculate_password_2(["L1000"])
    assert score2 == 10, f'Expected 10, got {score2}'

    score3 = calculate_password_2(["L150"])
    assert score3 == 2, f'Expected 2, got {score3}'

    test_input = read_test_input(1)
    score = calculate_password_2(test_input)
    assert score == 6, f'Expected 6, got {score}'

def solve_part2():
    print("--------------------------------------------")
    actual_input = get_input(1)
    result = calculate_password_2(actual_input)
    print(f'The password is {result}')

def calculate_password_2(input: List[str]):
    zeroes_count = 0

    position = dial_initial_position

    for line in input:
        direction = line[0]
        amount = int(line[1:])

        new_zeroes=0
        if direction == "L":
            target = position - amount
            if target < 1:
                if position != 0:
                    new_zeroes = 1
                new_zeroes += abs(target) // 100
                zeroes_count += new_zeroes
            position = target % 100
        elif direction == "R":
            target = position + amount
            if target > 99:
                new_zeroes += target // 100
                zeroes_count += new_zeroes
            position = target % 100
        else:
            raise Exception(f'Unknown direction {direction}')

    return zeroes_count

test_part1()
solve_part1()

test_part2()
solve_part2()
