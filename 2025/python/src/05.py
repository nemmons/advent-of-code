from typing import List


from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(5)
    thing = do_the_thing_part_1(test_input)
    assert thing ==3, f'Expected 3, got {thing}'


def solve_part1():
    actual_input = get_input(5)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    empty_line = [i for i in range(len(input)) if len(input[i].strip()) == 0][0]

    fresh_ingredient_ranges=[]
    for row in input[0:empty_line]:
        vals = row.strip().split('-')
        fresh_ingredient_ranges.append((int(vals[0]), int(vals[1])))

    # print(f'Fresh ingredient ranges: {fresh_ingredient_ranges}')

    fresh_count = 0
    for row in input[empty_line+1:]:
        # print(f"examining {row}")
        ingredient = int(row.strip())
        for (lower, upper) in fresh_ingredient_ranges:
            if lower <= ingredient <= upper:
                # print(f'Found {ingredient}, fresh because between {lower} and {upper}')
                fresh_count += 1
                break

    return fresh_count


def test_part2():
    test_input = read_test_input(5)
    result = do_the_thing_part_2(test_input)
    assert result == 14, f'Expected 14, got {result}'

def solve_part2():
    actual_input = get_input(5)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')

def do_the_thing_part_2(input: List[str]):
        empty_line = [i for i in range(len(input)) if len(input[i].strip()) == 0][0]

        fresh_range_inputs = input[0:empty_line]
        fresh_range_inputs.sort()

        fresh_ranges = []
        for row in fresh_range_inputs:
            vals = row.strip().split('-')
            lower = int(vals[0])
            upper = int(vals[1])

            intersected_existing = False
            for i, (elower, eupper) in enumerate(fresh_ranges):
                if lower <= elower and upper >= eupper:
                    fresh_ranges[i] = (lower, upper)
                    intersected_existing = True
                elif lower <= elower <= upper <= eupper:
                    fresh_ranges[i] = (lower, eupper)
                    intersected_existing = True
                elif elower <= lower <= eupper <= upper:
                    fresh_ranges[i] = (elower, upper)
                    intersected_existing = True
                elif elower <= lower <= upper <= eupper:
                    intersected_existing = True
                if intersected_existing:
                    break

            if not intersected_existing:
                fresh_ranges.append((lower, upper))

        fresh_count = 0
        for (lower, upper) in fresh_ranges:
            fresh_count += (upper - lower + 1)

        return fresh_count


test_part1()
solve_part1()

test_part2()
solve_part2()
