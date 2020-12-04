import re

#https://adventofcode.com/2020/day/2

def check_password_policies(file='input'):
    valid_password_count = 0
    with open(file) as f:
        for line in f:
            result = re.search("(\d+)-(\d+) ([a-z]): ([a-z]+)", line)
            if result:
                (pos_1, pos_2, char, password) = result.groups()

                pos_1_correctly_filled = (password[int(pos_1) - 1] == char)
                pos_2_correctly_filled = (password[int(pos_2) - 1] == char)

                if (int(pos_1_correctly_filled) + int(pos_2_correctly_filled)) == 1:
                    valid_password_count += 1
            else:
                raise Exception("Improper formatting for line: " + line)
    return valid_password_count

print(check_password_policies())
