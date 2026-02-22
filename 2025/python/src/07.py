from typing import List, Dict
from utils.api import get_input, read_test_input


def test_part1():
    test_input = read_test_input(7)
    thing = do_the_thing_part_1(test_input)
    assert thing == 21, f'Expected 21, got {thing}'


def solve_part1():
    actual_input = get_input(7)
    result = do_the_thing_part_1(actual_input)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str]):
    splits = 0
    beams = [[], [i for i in range(len(input[0].strip())) if input[0][i] == 'S']]
    print(beams)

    for row in range(2, len(input)):
        new_beams = []
        for beam in beams[row-1]:
            if input[row][beam] == '^':
                print(f"Found a split at {beam} on row {row}")
                splits += 1
                new_beams.extend([beam-1, beam+1])
            else:
                new_beams.append(beam)
        beams.append(list(set(new_beams)))

    return splits


def test_part2():
    test_input = read_test_input(7)
    result = do_the_thing_part_2(test_input)
    assert result == 40, f'Expected 40, got {result}'

def solve_part2():
    actual_input = get_input(7)
    result = do_the_thing_part_2(actual_input)
    print(f'Part 2: {result}')

def do_the_thing_part_2(input: List[str]):
    beams: List[List[int]] = [[], [i for i in range(len(input[0].strip())) if input[0][i] == 'S']]
    particles: List[Dict[int, int]] = [{}, {beams[1][0]: 1}]
    print(beams)

    for row in range(2, len(input)):
        new_beams = []
        new_particles: Dict[int, int] = {}
        print(f"beams to examine: {beams[row-1]}")
        for beam in beams[row-1]:
            if input[row][beam] == '^':
                new_beams.extend([beam-1, beam+1])
                new_particles[beam-1] = new_particles.get(beam-1, 0) + particles[row-1][beam]
                new_particles[beam+1] = new_particles.get(beam+1, 0) + particles[row-1][beam]
            else:
                new_beams.append(beam)
                new_particles[beam] = new_particles.get(beam, 0) + particles[row-1][beam]
        beams.append(list(set(new_beams)))
        particles.append(new_particles)
        print(f"new particles: {new_particles}")
        print("----")

    for x,p in enumerate(particles):
        row = []
        for i in range(len(input[0].strip())):
            if i in p:
                res = str(p[i])
            elif input[x][i] == '^':
                res = '^'
            else:
                res = '-'

            row.append(res)
        print(''.join(row))

    return sum(particles[-1].values())


test_part1()
solve_part1()

test_part2()
solve_part2()
