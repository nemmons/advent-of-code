package main

import (
	"fmt"
	"github.com/nemmons/advent-of-code/util"
	"regexp"
	"strconv"
	"strings"
)

func main() {
	input := util.ReadInput("day02/input.txt")

	fmt.Printf("Part 1 output: %d \n", part1(input))
	fmt.Printf("Part 2 output: %d", part2(input))
}

func part1(input []string) int {
	re, err := regexp.Compile("(\\d+)-(\\d+) ([a-z]): ([A-Za-z]+)")
	if err != nil {
		panic(err)
	}

	correct := 0

	for _, line := range input {
		match := re.FindStringSubmatch(line)
		if match == nil {
			errorString := fmt.Sprintf("Could not parse input line: %q", line)
			panic(errorString)
		}

		floor, err := strconv.ParseInt(match[1], 10, 64)
		if err != nil {
			panic(err)
		}

		ceil, err := strconv.ParseInt(match[2], 10, 64)
		if err != nil {
			panic(err)
		}

		targetChar := match[3]
		password := match[4]

		occurrences := strings.Count(password, targetChar)

		if int(floor) <= occurrences && occurrences <= int(ceil) {
			correct++
		}
	}

	return correct
}

func part2(input []string) int {
	re, err := regexp.Compile("(\\d+)-(\\d+) ([a-z]): ([A-Za-z]+)")
	if err != nil {
		panic(err)
	}

	correct := 0

	for _, line := range input {
		match := re.FindStringSubmatch(line)
		if match == nil {
			errorString := fmt.Sprintf("Could not parse input line: %q", line)
			panic(errorString)
		}

		floor, err := strconv.ParseInt(match[1], 10, 64)
		if err != nil {
			panic(err)
		}

		ceil, err := strconv.ParseInt(match[2], 10, 64)
		if err != nil {
			panic(err)
		}

		targetChar := match[3]
		password := match[4]

		match1 := password[floor-1:floor] == targetChar
		match2 := password[ceil-1:ceil] == targetChar

		if (match1 || match2) && !(match1 && match2) {
			correct++
		}
	}

	return correct
}
