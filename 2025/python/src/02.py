from os import MFD_ALLOW_SEALING
from typing import List
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(2)
    thing = do_the_thing_part_1(test_input)
    assert thing == 1227775554, f'Expected 1227775554, got {thing}'


def solve_part1():
    actual_input = get_input(2)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    sum_of_invalids = 0

    for line in input:
        ranges = line.strip().strip(",").split(",")
        for r in ranges:

            first_id, last_id = r.split("-")
            first_id = int(first_id)
            last_id = int(last_id)

            for i in range(first_id, last_id+1):
                if _check_invalid(i):
                    sum_of_invalids += i

    return sum_of_invalids

def _check_invalid(id: int) -> bool:
    id = str(id)
    id_len = len(id)
    if id_len % 2 == 1:
        return False
    halflen = int(id_len / 2)
    for i in range(halflen):
        if id[i] != id[halflen+i]:
            return False
    return True

def _check_invalid_2(id: int) -> bool:
    id = str(id)
    id_len = len(id)
    halflen = id_len // 2

    checks = []
    for i in range(halflen):
        if id_len % (i+1) == 0:
            checks.append(i+1)

    for check in checks:
        count = id_len // check
        if id[0:check]*count == id:
            return True
    return False

def test_part2():
    test_input = read_test_input(2)
    result = do_the_thing_part_2(test_input)
    assert result == 4174379265, f'Expected 4174379265, got {result}'

def solve_part2():
    actual_input = get_input(2)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')


def do_the_thing_part_2(input: List[str]):
    sum_of_invalids = 0

    for line in input:
        ranges = line.strip().strip(",").split(",")
        for r in ranges:

            first_id, last_id = r.split("-")
            first_id = int(first_id)
            last_id = int(last_id)

            for i in range(first_id, last_id+1):
                if _check_invalid_2(i):
                    sum_of_invalids += i

    return sum_of_invalids

# test_part1()
# solve_part1()

test_part2()
solve_part2()
