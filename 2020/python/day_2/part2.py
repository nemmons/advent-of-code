import re
from typing import List

# https://adventofcode.com/2020/day/2


class PasswordPolicy(object):
    char: str
    char_pos_1: int
    char_pos_2: int

    def __init__(self, char: str, char_pos_1: int, char_pos_2: int):
        if len(char) > 1:
            raise Exception("char should be a single character")

        self.char = char
        self.char_pos_1 = char_pos_1
        self.char_pos_2 = char_pos_2

    def check(self, password: str):
        char_in_pos_1 = (password[self.char_pos_1 - 1] == self.char)
        char_in_pos_2 = (password[self.char_pos_2 - 1] == self.char)

        # the char should only be filled in one of the two positions
        return (int(char_in_pos_1) + int(char_in_pos_2)) == 1


def process_input(file='input') -> List[dict]:
    passwords_and_policies = []
    with open(file) as f:
        for line in f:
            result = re.search("(\d+)-(\d+) ([a-z]): ([a-z]+)", line)
            if result:
                (char_pos_1, char_pos_2, char, password) = result.groups()

                passwords_and_policies.append({
                    'password': password,
                    'policy': PasswordPolicy(char, int(char_pos_1), int(char_pos_2))
                })
            else:
                raise Exception("Improper formatting for line: " + line)
    return passwords_and_policies


def check_password_policies():
    passwords_and_policies = process_input()

    valid_password_count = 0

    for item in passwords_and_policies:
        policy = item['policy']  # type: PasswordPolicy
        password = item['password']

        if policy.check(password):
            valid_password_count += 1
    return valid_password_count


print(check_password_policies())
