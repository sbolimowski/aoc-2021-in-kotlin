from aoc_utils import *


def part1(lines):
    horizontal = depth = 0
    commands = [line.split() for line in lines]

    for operation, value in commands:
        if (operation == 'forward'): horizontal += int(value)
        if (operation == 'down'): depth += int(value)
        if (operation == 'up'): depth -= int(value)

    return horizontal * depth


def part2(lines):
    horizontal = depth = aim = 0
    commands = [line.split() for line in lines]

    for operation, value in commands:
        if (operation == 'forward'): 
            horizontal += int(value)
            depth += aim * int(value)
        if (operation == 'down'): 
            aim += int(value)
        if (operation == 'up'): 
            aim -= int(value)

    return horizontal * depth


test_input = read_lines("day02_test")
assert part1(test_input) == 150
assert part2(test_input) == 900

my_input = read_lines("day02_2")
print(part1(my_input))
print(part2(my_input))