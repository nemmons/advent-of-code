from typing import List, Tuple
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(4)
    thing = count_xmas(test_input)
    assert thing == 18, f'Expected 18, got {thing}'


def solve_part1():
    actual_input = get_input(4)
    result = count_xmas(actual_input)
    print(f'Part 1: {result}')


vectors = [(-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0), (1, -1), (0, -1), (-1, -1)]


def count_xmas(input: List[str]):
    input = [line.strip() for line in input]

    xmas_count = 0

    for y in range(len(input)):
        for x in range(len(input[y])):
            if input[y][x] == 'X':
                for v in vectors:
                    if scan("M", x, y, v, input):
                        xmas_count += 1
    return xmas_count


def scan(target: str, x: int, y: int, v: Tuple[int, int], input: List[str]) -> bool:
    dx, dy = v

    target_x = x + dx
    target_y = y + dy

    if target_x < 0 or target_y < 0 or target_x > len(input[0]) - 1 or target_y > len(input) - 1:
        return False

    if input[target_y][target_x] == target:
        if target == "M":
            new_target = "A"
        elif target == "A":
            new_target = "S"
        elif target == "S":
            return True
        else:
            raise ValueError(f'Invalid char {target}')
        return scan(new_target, target_x, target_y, v, input)
    return False


# def test_part2():
#     test_input = read_test_input(4)
#     result = do_the_thing_part_2(test_input)
#     assert score == 0, f'Expected 0, got {result}'

# def solve_part2():
#     actual_input = get_input(4)
#     result = do_the_thing_part_2(actual_input)
#     print(f'Part 2: {result}')
# def do_the_thing_part_2(input: List[str]):
#     return 1

test_part1()
solve_part1()

# test_part2()
# solve_part2()
