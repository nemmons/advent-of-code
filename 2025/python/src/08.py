import math
from functools import reduce
from itertools import product
from typing import List, Dict
from utils.api import get_input, read_test_input

class Point( object ):
    def __init__( self, x, y, z ):
        self.x, self.y, self.z = x, y, z
    def dist_from( self, other_point ):
        return math.sqrt( (self.x-other_point.x)**2 + (self.y-other_point.y)**2 + (self.z-other_point.z)**2 )

def test_part1():
    test_input = read_test_input(8)
    thing = do_the_thing_part_1(test_input, 10)
    assert thing == 40, f'Expected 0, got {thing}'


def solve_part1():
    actual_input = get_input(8)
    result = do_the_thing_part_1(actual_input, 1000)
    print(f'Part 1: {result}')


def do_the_thing_part_1(input: List[str], desired_connection_count=10):
    points: List[Point] = []
    networks: List[List[Point]] = []
    distances: Dict[float, List[tuple[Point, Point]]] = {}
    for line in input:
        points.append(Point(*map(int, line.strip().split(','))))

    for point in points:
        for other_point in points:
            if point != other_point:
                distance = point.dist_from(other_point)
                these_distances = distances.get(distance, [])
                these_distances.append((point, other_point))
                distances[distance] = these_distances

    total_connections = 0

    for distance, pairs in distances.items():
        while pairs and len(networks) <= desired_connection_count:
            p1, p2 = pairs.pop(0)
            p1_network = None
            p2_network = None
            for i, network in enumerate(networks):
                if p1 in network:
                    p1_network = i
                if p2 in network:
                    p2_network = i
            if p1_network is not None and p2_network is not None and p1_network != p2_network:
                raise Exception("Need to merge networks")
            elif p1_network is not None:
                networks[p1_network].append(p2)
            elif p2_network is not None:
                networks[p2_network].append(p1)
            else:
                networks.append([p1, p2])
            total_connections += 1

        network_sizes = [len(network) for network in networks]
        network_sizes.sort()

        print(networks)
        print(network_sizes)
        return reduce(lambda x,y: x*y, network_sizes[:3], 1)


def calc_distance(a, b):
    return math.sqrt((a[0] - b[0]) ** 2 + (a[1] - b[1]) ** 2 + (a[2] - b[2]) ** 2)


# def test_part2():
#     test_input = read_test_input(8)
#     result = do_the_thing_part_2(test_input)
#     assert result == 0, f'Expected 0, got {result}'

# def solve_part2():
#     actual_input = get_input(8)
#     result = do_the_thing_part_2(actual_input)
#     print(f'Part 2: {result}')
# def do_the_thing_part_2(input: List[str]):
#     return 1

test_part1()
solve_part1()

# test_part2()
# solve_part2()
