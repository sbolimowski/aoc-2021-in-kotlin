def read_lines(name):
    file = open('../inputs/'+name+'.txt', 'r')
    return file.readlines()


def read_line(name):
    file = open('../inputs/'+name+'.txt', 'r')
    return file.read()


def read_numbers(file_name):
    file = open('../inputs/'+file_name+'.txt', 'r')
    lines = file.readlines()
    return [int(line.strip()) for line in lines]