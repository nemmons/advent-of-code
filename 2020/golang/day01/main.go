package main

import (
	"fmt"
	"github.com/nemmons/advent-of-code/util"
)

func main() {
	input := util.ReadIntput("day01/input.txt")

	fmt.Printf("Part 1 output: %d \n", part1(input))
	fmt.Printf("Part 2 output: %d", part2(input))
}

func part1(input []int) int {
	for _, i := range input {
		for _, j := range input {
			if i+j == 2020 {
				return i * j
			}
		}
	}

	return 0
}

func part2(input []int) int {
	for _, i := range input {
		for _, j := range input {
			for _, k := range input {
				if i+j+k == 2020 {
					return i * j * k
				}
			}
		}
	}

	return 0
}
