from typing import List
from functools import reduce

TREE_CHAR = '#'


def process_input(file='input') -> List[str]:
    toboggan_map = []
    with open(file) as f:
        for line in f:
            toboggan_map.append(line.rstrip())

    return toboggan_map


def count_trees(toboggan_map, slope: tuple):
    map_height = len(toboggan_map)
    chunk_width = len(toboggan_map[0])

    x = 0
    y = 0

    (slope_x, slope_y) = slope

    tree_count = 0

    while y < map_height - 1:
        x = (x + slope_x) % chunk_width
        y += slope_y

        if toboggan_map[y][x] == TREE_CHAR:
            tree_count += 1

    return tree_count


def solve():
    toboggan_map = process_input()
    slopes_to_check = [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]
    tree_counts = [count_trees(toboggan_map, slope) for slope in slopes_to_check]

    product = reduce(lambda a,b: a*b, tree_counts)
    print(product)


solve()