package main

import (
	"fmt"
	"github.com/nemmons/advent-of-code/util"
)

func main() {
	input := util.ReadInput("day03/input.txt")

	fmt.Printf("Part 1 output: %d \n", part1(input))
	fmt.Printf("Part 2 output: %d", part2(input))
}

func part1(input []string) int {
	return treesHit(input, 3, 1)
}

func treesHit(slopeMap []string, dx int, dy int) int {
	x := 0
	width := len(slopeMap[0])

	treesHit := 0

	for y := 0; y < len(slopeMap); y += dy {
		line := slopeMap[y]
		if line[x:x+1] == "#" {
			treesHit++
		}
		x = (x + dx) % width
	}

	return treesHit
}

func part2(input []string) int {

	velocities := [5][2]int{
		{1, 1}, {3, 1}, {5, 1}, {7, 1}, {1, 2},
	}

	treeProduct := 1
	for i := 0; i < 5; i++ {
		treeProduct *= treesHit(input, velocities[i][0], velocities[i][1])
	}

	return treeProduct
}
