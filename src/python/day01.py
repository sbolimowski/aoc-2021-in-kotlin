from aoc_utils import *


def part1(numbers):
    return sum(
        1
        for i in range(1, len(numbers))
        if numbers[i] > numbers[i-1]
    )


def part2(numbers):
    return sum(
        1
        for i in range(3, len(numbers))
        if numbers[i] > numbers[i-3]
    )


test_input = read_numbers("day01_test")
assert part1(test_input) == 7
assert part2(test_input) == 5

my_input = read_numbers("day01_2")
print(part1(my_input))
print(part2(my_input))