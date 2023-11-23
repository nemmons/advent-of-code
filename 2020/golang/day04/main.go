package main

import (
	"fmt"
	"github.com/nemmons/advent-of-code/util"
	"regexp"
	"slices"
	"strconv"
	"strings"
)

func main() {
	input := util.ReadInput("day04/input.txt")

	fmt.Printf("Part 1 output: %d \n", part1(input))
	fmt.Printf("Part 2 output: %d", part2(input))
}

func part1(input []string) int {
	var expectedFields = []string{
		"byr",
		"iyr",
		"eyr",
		"hgt",
		"hcl",
		"ecl",
		"pid",
	}

	var passports = parse(input)

	var validCount = 0

PassportCheck:
	for _, passport := range passports {
		var passportKeys = getKeys(passport)
		for _, field := range expectedFields {
			if slices.Index(passportKeys, field) == -1 {
				continue PassportCheck
			}
		}

		validCount++
	}

	return validCount
}

func part2(input []string) int {
	var passports = parse(input)

	var validCount = 0

	hclRegex, _ := regexp.Compile("^#[0-9a-f]{6}$")
	pidRegex, _ := regexp.Compile("^[0-9]{9}$")

	for _, passport := range passports {
		if validateByr(passport) &&
			validateIyr(passport) &&
			validateEyr(passport) &&
			validateHgt(passport) &&
			validateHcl(hclRegex, passport) &&
			validateEcl(passport) &&
			validatePid(pidRegex, passport) {
			validCount++
		}
	}

	return validCount
}

func parse(input []string) []map[string]string {
	var passports []map[string]string

	var passport = make(map[string]string)

	for _, line := range input {
		if strings.TrimSpace(line) == "" {
			passports = append(passports, passport)
			passport = make(map[string]string)
			continue
		}

		var pairs = strings.Split(strings.TrimSpace(line), " ")
		for _, pair := range pairs {
			var split = strings.Split(pair, ":")
			passport[split[0]] = split[1]
		}
	}
	passports = append(passports, passport)

	return passports
}

func getKeys(passport map[string]string) []string {
	var keys []string
	for key, _ := range passport {
		keys = append(keys, key)
	}
	return keys
}

func validateByr(passport map[string]string) bool {
	byr := passport["byr"]
	if len(byr) != 4 {
		return false
	}
	parsed, err := strconv.ParseInt(byr, 10, 64)
	if err != nil {
		return false
	}
	return 1920 <= parsed && parsed <= 2002
}

func validateIyr(passport map[string]string) bool {
	iyr := passport["iyr"]
	if len(iyr) != 4 {
		return false
	}
	parsed, err := strconv.ParseInt(iyr, 10, 64)
	if err != nil {
		return false
	}
	return 2010 <= parsed && parsed <= 2020
}

func validateEyr(passport map[string]string) bool {
	eyr := passport["eyr"]
	if len(eyr) != 4 {
		return false
	}
	parsed, err := strconv.ParseInt(eyr, 10, 64)
	if err != nil {
		return false
	}
	return 2020 <= parsed && parsed <= 2030
}

func validateHgt(passport map[string]string) bool {
	hgt := passport["hgt"]
	if len(hgt) == 0 {
		return false
	}
	unit := hgt[len(hgt)-2:]
	amt := hgt[:len(hgt)-2]
	parsedAmt, err := strconv.ParseInt(amt, 10, 64)
	if err != nil {
		return false
	}

	if unit == "cm" && 150 <= parsedAmt && parsedAmt <= 193 {
		return true
	} else if unit == "in" && 59 <= parsedAmt && parsedAmt <= 76 {
		return true
	}
	return false
}

func validateHcl(r *regexp.Regexp, passport map[string]string) bool {
	return r.MatchString(passport["hcl"])
}

func validateEcl(passport map[string]string) bool {
	ecls := []string{
		"amb", "blu", "brn", "gry", "grn", "hzl", "oth",
	}

	ecl := passport["ecl"]

	return slices.Index(ecls, ecl) > -1
}

// yes, i realize i could lift the map reference into the caller and then just have one 'validateRegex'
// method for hcl+pid, but i am stubbornly sticking to my pattern. Might refactor later.
func validatePid(r *regexp.Regexp, passport map[string]string) bool {
	return r.MatchString(passport["pid"])
}
