from typing import List
from utils.api import get_input, read_test_input


directions = [
    (-1, 0),
    (-1, 1),
    (0, 1),
    (1, 1),
    (1, 0),
    (1, -1),
    (0, -1),
    (-1, -1),
]

def test_part1():
    test_input = read_test_input(4)
    thing = do_the_thing_part_1(test_input)
    assert thing == 13, f'Expected 13, got {thing}'


def solve_part1():
    actual_input = get_input(4)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    rows = len(input)
    columns = len(input[0].strip())

    buffer_row = (['*'] * (columns + 2))
    middle = [(['*'] + [char for char in input[i].strip()] + ['*']) for i in range(rows) ]

    floor_map = [buffer_row] + middle + [buffer_row]


    accessible_rolls = 0

    for row in range(1, rows+1):
        for column in range(1, columns+1):
            if floor_map[row][column] != '@':
                continue
            neighbor_rolls = 0
            for (x,y) in directions:
                if floor_map[row + x][column + y] == '@':
                    neighbor_rolls += 1
            if neighbor_rolls < 4:
                print(f"{row}, {column}")
                accessible_rolls += 1

    return accessible_rolls


def test_part2():
    test_input = read_test_input(4)
    result = do_the_thing_part_2(test_input)
    assert result == 43, f'Expected 43, got {result}'

def solve_part2():
    actual_input = get_input(4)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')

def do_the_thing_part_2(input: List[str]):
    rows = len(input)
    columns = len(input[0].strip())

    buffer_row = (['*'] * (columns + 2))
    middle = [(['*'] + [char for char in input[i].strip()] + ['*']) for i in range(rows)]

    floor_map = [buffer_row] + middle + [buffer_row]

    accessible_rolls = 0

    while True:
        to_remove = []

        for row in range(1, rows + 1):
            for column in range(1, columns + 1):
                if floor_map[row][column] != '@':
                    continue
                neighbor_rolls = 0
                for (x, y) in directions:
                    if floor_map[row + x][column + y] == '@':
                        neighbor_rolls += 1
                if neighbor_rolls < 4:
                    to_remove.append((row, column))
                    accessible_rolls += 1

        if len(to_remove) > 0:
            for (x,y) in to_remove:
                floor_map[x][y] = '.'
            to_remove = []
        else:
            break

    return accessible_rolls


test_part1()
solve_part1()

test_part2()
solve_part2()
