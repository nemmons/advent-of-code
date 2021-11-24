import timeit

#https://adventofcode.com/2020/day/1

def naive_solution(numbers: list, target_sum=2020):
    for number in numbers:
        sub_target = target_sum - number

        sublist = numbers.copy()
        sublist.remove(number)
        for sub_number in sublist:
            if sub_target - sub_number in sublist:
                result = (number * sub_number * (sub_target - sub_number))
                return result
    return None


def process_input(file='input'):
    with open(file) as f:
        numbers = [int(line) for line in f]

    return numbers


def benchmark_solution():
    numbers = process_input()

    start = timeit.timeit()
    solution = naive_solution(numbers, 2020)
    end = timeit.timeit()
    if solution:
        print(solution)
        print("naive solution in" + str(end - start))
    else:
        print("No solution found")

benchmark_solution()


def test():
    numbers = [1721, 979, 366, 299, 675, 1456]
    result = naive_solution(numbers, 2020)
    assert result == 241861950
test()
