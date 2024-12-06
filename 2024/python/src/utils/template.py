from typing import List
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input([DAY_NUM])
    thing = do_the_thing_part_1(test_input)
    assert thing == 0, f'Expected 0, got {thing}'


def solve_part1():
    actual_input = get_input([DAY_NUM])
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    return 1


# def test_part2():
#     test_input = read_test_input([DAY_NUM])
#     result = do_the_thing_part_2(test_input)
#     assert score == 0, f'Expected 0, got {result}'

# def solve_part2():
#     actual_input = get_input([DAY_NUM])
#     result = do_the_thing_part_2(actual_input)
#     print(f'Part 2: {result}')
# def do_the_thing_part_2(input: List[str]):
#     return 1

test_part1()
solve_part1()

# test_part2()
# solve_part2()
