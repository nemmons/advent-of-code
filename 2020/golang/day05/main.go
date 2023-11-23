package main

import (
	"fmt"
	"github.com/nemmons/advent-of-code/util"
	"slices"
	"strconv"
	"strings"
)

func main() {
	input := util.ReadInput("day05/input.txt")

	fmt.Printf("Part 1 output: %d \n", part1(input))
	fmt.Printf("Part 2 output: %d", part2(input))
}

func part1(input []string) int {
	maxSeatId := 0
	for _, pass := range input {
		seatID := calcSeatID(pass)
		if seatID >= maxSeatId {
			maxSeatId = seatID
		}
	}
	return maxSeatId
}

func part2(input []string) int {
	var seatIds []int
	for _, pass := range input {
		seatIds = append(seatIds, calcSeatID(pass))
	}

	slices.Sort(seatIds)

	for i, seatID := range seatIds {
		if seatIds[i+1] != seatID+1 {
			return seatID + 1
		}
	}

	return 0

}

func calcSeatID(boardingPass string) int {
	convertB := strings.Replace(boardingPass, "B", "1", -1)
	convertF := strings.Replace(convertB, "F", "0", -1)
	convertL := strings.Replace(convertF, "L", "0", -1)
	converted := strings.Replace(convertL, "R", "1", -1)

	row := converted[0:7]
	rowNum, err := strconv.ParseInt(row, 2, 64)
	if err != nil {
		println(err)
		return 0
	}

	col := converted[7:]
	colNum, err := strconv.ParseInt(col, 2, 64)
	if err != nil {
		println(err)
		return 0
	}

	return int(rowNum*8 + colNum)
}
