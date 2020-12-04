import timeit


def naive_solution(numbers: list, target_sum=2020):
    for number in numbers:
        remainder = target_sum - number
        if remainder in numbers:
            solution = number * remainder
            return solution
    return None


def optimized_solution(numbers: list, target=2020):
    numbers.sort()

    lower_index = 0
    upper_index = len(numbers)-1

    while lower_index < upper_index:
        lower = numbers[lower_index]
        upper = numbers[upper_index]

        if lower + upper == target:
            return lower * upper
        elif lower + upper < target:
            lower_index += 1
        elif lower + upper > target:
            upper_index -= 1
    return None


def process_input(file='input'):
    with open(file) as f:
        numbers = [int(line) for line in f]

    return numbers


def benchmark_solutions():
    numbers = process_input()

    start = timeit.timeit()
    solution = naive_solution(numbers, 2020)
    end = timeit.timeit()
    if solution:
        print(solution)
        print("naive solution in" + str(end - start))
    else:
        print("No solution found")

    start = timeit.timeit()
    solution = optimized_solution(numbers, 2020)
    end = timeit.timeit()
    if solution:
        print(solution)
        print("optimized solution in" + str(end-start))
    else:
        print("No solution found")

benchmark_solutions()


def test():
    numbers = [1721, 979, 366, 299, 675, 1456]
    result = naive_solution(numbers, 2020)
    assert result == 514579

    numbers = [1721, 979, 366, 299, 675, 1456]
    result = optimized_solution(numbers, 2020)
    assert result == 514579
test()
