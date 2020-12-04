import re

#https://adventofcode.com/2020/day/2

def check_password_policies(file='input'):
    valid_password_count = 0
    with open(file) as f:
        for line in f:
            result = re.search("(\d+)-(\d+) ([a-z]): ([a-z]+)", line)
            if result:
                (char_count_min, char_count_max, char, password) = result.groups()

                if int(char_count_min) <= password.count(char) <= int(char_count_max):
                    valid_password_count += 1
            else:
                raise Exception("Improper formatting for line: " + line)
    return valid_password_count

print(check_password_policies())
