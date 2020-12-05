from typing import List
import re


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


class PassportValidator(object):
    def validate(self, passport: dict):
        validations = [
            'fields',
            'byr',
            'iyr',
            'eyr',
            'hgt',
            'hcl',
            'ecl',
            'pid'
        ]
        for validation in validations:
            method = 'validate_' + validation
            fn = getattr(self, method)
            if not fn(passport):
                return False
        return True

    def validate_fields(self, passport):
        required_fields = {'byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid'}
        return required_fields - set(passport.keys()) == set()

    def validate_byr(self, passport):
        byr = passport['byr']
        return bool(re.search(r"^\d{4}$", str(byr))) and 1920 <= int(byr) <= 2002

    def validate_iyr(self, passport):
        iyr = passport['iyr']
        return bool(re.search(r"^\d{4}$", str(iyr))) and 2010 <= int(iyr) <= 2020

    def validate_eyr(self, passport):
        eyr = passport['eyr']
        return bool(re.search(r"^\d{4}$", str(eyr))) and 2020 <= int(eyr) <= 2030

    def validate_hcl(self, passport):
        hcl = passport['hcl']
        return bool(re.search(r"^#[a-f0-9]{6}$", hcl))

    def validate_hgt(self, passport):
        hgt_units = passport['hgt'][-2:]
        try:
            hgt = int(passport['hgt'][:-2])
        except ValueError:
            return False
        if hgt_units == 'cm' and (hgt < 150 or hgt > 193):
            return False
        elif hgt_units == 'in' and (hgt < 59 or hgt > 76):
            return False
        elif hgt_units not in ['cm', 'in']:
            return False

        return True

    def validate_ecl(self, passport):
        return passport['ecl'] in ['amb', 'blu', 'brn', 'gry', 'grn', 'hzl', 'oth']

    def validate_pid(self, passport):
        pid = passport['pid']
        return bool(re.search(r"^\d{9}$", pid))


passport_list = process_input()


valid_passport_count = 0
invalid = 0

print(len(passport_list))

v = PassportValidator()
for passport in passport_list:
    if v.validate(passport):
        valid_passport_count += 1
    else:
        invalid += 1

print(valid_passport_count, invalid)
