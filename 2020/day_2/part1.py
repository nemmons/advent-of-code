import re
from typing import List

# https://adventofcode.com/2020/day/2


class PasswordPolicy(object):
    char: str
    char_min_count: int
    char_max_count: int

    def __init__(self, char: str, char_min_count: int, char_max_count: int):
        if len(char) > 1:
            raise Exception("char should be a single character")

        self.char = char
        self.char_min_count = char_min_count
        self.char_max_count = char_max_count

    def check(self, password: str):
        return self.char_min_count <= password.count(self.char) <= self.char_max_count

def process_input(file='input') -> List[dict]:
    passwords_and_policies = []
    with open(file) as f:
        for line in f:
            result = re.search("(\d+)-(\d+) ([a-z]): ([a-z]+)", line)
            if result:
                (char_count_min, char_count_max, char, password) = result.groups()

                passwords_and_policies.append({
                    'password': password,
                    'policy': PasswordPolicy(char, int(char_count_min), int(char_count_max))
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
