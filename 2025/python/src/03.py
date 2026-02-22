from typing import List, Optional
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(3)
    thing = do_the_thing_part_1(test_input)
    assert thing == 357, f'Expected 357, got {thing}'


def solve_part1():
    actual_input = get_input(3)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')



def do_the_thing_part_1(input: List[str]):
    total_joltage = 0

    for line in input:
        batteries = [int(i) for i in line.strip()]
        first = batteries[0]
        second = batteries[1]
        last_index = len(batteries) - 1
        for i, battery in enumerate(batteries):
            if i == 0:
                continue
            if battery > first and i < last_index:
                first = battery
                second = batteries[i+1]
            elif battery > second:
                second = battery
        best = int(f"{first}{second}")
        total_joltage += best

    return total_joltage


def test_part2():
    test_input = read_test_input(3)
    result = do_the_thing_part_2(test_input)
    assert result == 3121910778619, f'Expected 3121910778619, got {result}'


def do_the_thing_part_2(input: List[str]):
    total_joltage = 0


    for line in input:
        line=line.strip()
        voltage = ""

        # print(f"looking at line {line} (len: {len(line)})")
        while len(voltage) < 12:
            first_index = 0
            first_digit = 0

            # checkline = line[0:len(line)-(12-len(voltage))+1]
            # print(f"checking chars {checkline} ({len(checkline)} chars)")
            # reserved = line[len(line)-(12-len(voltage))+1:]
            # print(f"reserving {reserved} ({len(reserved)} chars) for remaining")

            for i in range(0, len(line)-(12-len(voltage))+1):
                if int(line[i]) > first_digit:
                    first_index = i
                    first_digit = int(line[i])
            # print(f"first index: {first_index}")
            # print(f"first digit: {first_digit}")
            voltage += line[first_index]
            line=line[first_index+1:]

        total_joltage += int(voltage)

    return total_joltage

def solve_part2():
    actual_input = get_input(3)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')


test_part1()
solve_part1()

test_part2()
solve_part2()
