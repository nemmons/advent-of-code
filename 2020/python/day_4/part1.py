from typing import List


def process_input(file='input') -> List[dict]:
    passports = []

    with open(file) as f:
        working_passport = {}
        for line in f:
            if line == '\n' and working_passport.keys():
                passports.append(working_passport)
                working_passport = {}
            else:
                fields = line.rstrip().split()
                for field in fields:
                    split = field.split(':')
                    if len(split) == 2:
                        working_passport[split[0]] = split[1]
        if working_passport.keys():
            passports.append(working_passport)
    return passports


passport_list = process_input()
required_fields = {'byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid'}

valid_passport_count = 0

for passport in passport_list:
    if required_fields - set(passport.keys()) == set():
        valid_passport_count += 1

print(valid_passport_count)
