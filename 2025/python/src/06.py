from typing import List

from apsw.unicode import line_break_next

from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(6)
    thing = do_the_thing_part_1(test_input)
    assert thing == 4277556, f'Expected 4277556, got {thing}'


def solve_part1():
    actual_input = get_input(6)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    total = 0
    start = 0

    max_end = len(input[0])

    for column in range(max_end):
        non_whitespace_found = False
        for line in input:
            try:
                if column < len(line) and line[column] != ' ' and line[column] != '\n':
                    non_whitespace_found = True
            except IndexError as e:
                print(f"Error at column {column} in line {line}")
                raise e
        if not non_whitespace_found:
            end = column
            vals = []
            for line in input[0:-1]:
                vals.append(int(line[start:end].strip()))
            operation = input[-1][start:end].strip()

            # print(f"Operation: {operation}, vals: {vals}")

            if operation == "+":
                total += sum(vals)
            elif operation == "*":
                result = 1
                for val in vals:
                    result *= val
                total += result

            start = end
    return total


def test_part2():
    test_input = read_test_input(6)
    result = do_the_thing_part_2(test_input)
    assert result == 3263827, f'Expected 3263827, got {result}'

def solve_part2():
    actual_input = get_input(6)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')

def do_the_thing_part_2(input: List[str]):
    total = 0
    max_end = len(input[0].strip())

    vals = []
    for column in range(max_end, -1, -1):
        non_whitespace_found = False
        for line in input:
            try:
                if column < len(line) and line[column] != ' ' and line[column] != '\n':
                    non_whitespace_found = True
            except IndexError as e:
                print(f"Error at column {column} in line {line}")
                raise e
        operation = None
        if non_whitespace_found:
            numlist = [input[i][column] for i in range(len(input)-1)]
            num = int(''.join(numlist).strip())
            vals.append(num)
            print(f"converted {numlist} to {num}")
        elif column < max_end:
            try:
                operation = input[-1][column+1].strip()
            except IndexError as e:
                print(f"Error at column {column} in line {input[-1]}")
                raise e
        if column == 0:
            operation = input[-1][column].strip()

        if operation is not None:
            if operation == "+":
                result = sum(vals)
                total += result
            elif operation == "*":
                result = 1
                for val in vals:
                    result *= val
                total += result

            vals = []
    return total

test_part1()
solve_part1()

test_part2()
solve_part2()
