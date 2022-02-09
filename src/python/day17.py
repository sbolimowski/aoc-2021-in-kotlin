def find_max_y(step_x, step_y, area_min_x, area_max_x, area_min_y, area_max_y):
    x = y = 0
    result = y
    in_area = False
    while x <= area_max_x and y >= area_min_y:
        x += step_x
        y += step_y

        if step_x > 0:
            step_x -= 1

        step_y -= 1

        if y > result:
            result = y

        if area_min_x <= x <= area_max_x and area_min_y <= y <= area_max_y:
            in_area = True

    if in_area:
        return result
    else:
        return -1000


def part1(input):
    _x_range, _y_range = input[len("target area:"):].split(",")
    area_min_x, area_max_x = _x_range.strip()[2:].split("..")
    area_min_y, area_max_y = _y_range.strip()[2:].split("..")

    area_min_x = int(area_min_x.strip())
    area_max_x = int(area_max_x.strip())
    area_min_y = int(area_min_y.strip())
    area_max_y = int(area_max_y.strip())

    # print(area_min_x, area_max_x, area_min_y, area_max_y)

    result = -1
    for step_x in range(0, area_max_x):
        for step_y in range(0, area_max_x):
            tmp = find_max_y(step_x, step_y, area_min_x, area_max_x, area_min_y, area_max_y)
            # print(step_x, step_y, tmp)
            if tmp > result:
                result = tmp


    # print(result)
    return result


def part2(input):
    _x_range, _y_range = input[len("target area:"):].split(",")
    area_min_x, area_max_x = _x_range.strip()[2:].split("..")
    area_min_y, area_max_y = _y_range.strip()[2:].split("..")

    area_min_x = int(area_min_x.strip())
    area_max_x = int(area_max_x.strip())
    area_min_y = int(area_min_y.strip())
    area_max_y = int(area_max_y.strip())


    counter = 0
    for step_x in range(0, area_max_x+1):
        for step_y in range(area_min_y, area_max_x):
            tmp = find_max_y(step_x, step_y, area_min_x, area_max_x, area_min_y, area_max_y)
            if tmp > -1000:
                # print(step_x, step_y)
                counter += 1
    print(counter)
    return counter


# assert part1("target area: x=20..30, y=-10..-5") == 45
#
# print(part1("target area: x=235..259, y=-118..-62"))
# print(part1("target area: x=137..171, y=-98..-73"))

assert part2("target area: x=20..30, y=-10..-5") == 112

print(part2("target area: x=235..259, y=-118..-62"))
print(part2("target area: x=137..171, y=-98..-73"))
