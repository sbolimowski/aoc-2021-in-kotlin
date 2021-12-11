from aoc_utils import *

def part1(lines):
    line_length = len(lines[0].strip())
    counts = [0] * line_length

    for line in lines:
        for i, char in enumerate(line.strip()):
            if char == '1':
                counts[i] += 1

    gamma = 0
    epsilon = 0
    for i in range(line_length):
        gamma <<= 1
        epsilon <<= 1
        if counts[i] > len(lines) / 2:
            gamma +=1
        else:
            epsilon +=1

    return gamma * epsilon


def part2(lines):
    return 0


test_input = read_lines("Day03_test")
assert part1(test_input) == 198
assert part2(test_input) == 230

my_input = read_lines("day03_2")
assert part1(my_input) == 3985686
assert part2(my_input) == 2555739