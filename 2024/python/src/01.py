from typing import List, Dict

from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(1)
    total_distance = calculate_total_distances(test_input)
    assert total_distance == 11, f'Expected 11, got {total_distance}'


def solve_part1():
    actual_input = get_input(1)
    result = calculate_total_distances(actual_input)
    print(f'The total distance is {result}')


def calculate_total_distances(input: List[str]):
    left: List[int] = []
    right: List[int] = []

    for line in input:
        tmp = line.split('   ')
        assert len(tmp) == 2
        left.append(int(tmp[0].strip()))
        right.append(int(tmp[1].strip()))

    left.sort()
    right.sort()

    assert len(left) == len(right)

    total_distance = 0

    for i, left_num in enumerate(left):
        total_distance += abs(left_num - right[i])

    return total_distance


def test_part2():
    test_input = read_test_input(1)
    score = calculate_similarity_score(test_input)
    assert score == 31, f'Expected 31, got {score}'


def solve_part2():
    actual_input = get_input(1)
    result = calculate_similarity_score(actual_input)
    print(f'The similarity score is {result}')


def calculate_similarity_score(input: List[str]):
    left: Dict[int, int] = {}
    right: Dict[int, int] = {}

    similarity_score = 0

    for line in input:
        tmp = line.split('   ')
        assert len(tmp) == 2
        left_num = int(tmp[0].strip())
        right_num = int(tmp[1].strip())

        if left_num in left:
            left[left_num] += 1
        else:
            left[left_num] = 1

        if right_num in right:
            right[right_num] += 1
        else:
            right[right_num] = 1

        if left_num in right:
            similarity_score += left_num * right[left_num]

        if right_num in left:
            similarity_score += right_num * left[right_num]

        if right_num == left_num:
            # my stupid math above double-accounts for a number; this fixes it
            similarity_score -= left_num

    return similarity_score


test_part1()
solve_part1()

test_part2()
solve_part2()
