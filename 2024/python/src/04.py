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


def test_part2():
    test_input = read_test_input(4)
    result = count_mas_xs(test_input)
    assert result == 9, f'Expected 9, got {result}'


def solve_part2():
    actual_input = get_input(4)
    result = count_mas_xs(actual_input)
    print(f'Part 2: {result}')


def count_mas_xs(input: List[str]):
    input = [line.strip() for line in input]

    xmas_count = 0

    diagonal_coordinate_pairs = [
        ((-1,1),(1,-1)),
        ((-1,-1),(1,1)),
        ((1,-1),(-1,1)),
        ((1,1),(-1,-1)),
    ]

    for y in range(1, len(input) -1):
        for x in range(1, len(input[y]) -1):
            if input[y][x] == 'A':
                found_x = False
                for first, second in diagonal_coordinate_pairs:
                    if found_x:
                        break

                    # search each diagonal coordinate pair for M and S (making 'MAS')
                    if input[y+first[0]][x+first[1]] == 'M' and input[y+second[0]][x+second[1]] == 'S':
                        # search other diagonal coordinate pairs for a second M and S (making an X out of two 'MAS's)
                        for other_first, other_second in [pair for pair in diagonal_coordinate_pairs if pair != (first, second)]:
                            if input[y + other_first[0]][x + other_first[1]] == 'M' and input[y + other_second[0]][x + other_second[1]] == 'S':
                                xmas_count += 1
                                found_x = True


    return xmas_count


test_part1()
solve_part1()

test_part2()
solve_part2()
