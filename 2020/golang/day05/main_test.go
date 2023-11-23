package main

import "testing"

func TestCalcSeatID(t *testing.T) {
	input := map[string]int{
		"BFFFBBFRRR": 567,
		"FFFBBBFRRR": 119,
		"BBFFBBFRLL": 820,
	}

	for pass, expectedSeatId := range input {
		actual := calcSeatID(pass)
		if actual != expectedSeatId {
			t.Fatalf("TestCalcSeatId failed. Expected %d, got %d", expectedSeatId, actual)
		}
	}
}

func TestPart1(t *testing.T) {
	input := []string{
		"BFFFBBFRRR",
		"FFFBBBFRRR",
		"BBFFBBFRLL",
	}

	expected := 820
	actual := part1(input)

	if actual != expected {
		t.Fatalf("Part 1 failed. Expected %d, got %d", expected, actual)
	}
}

func TestPart2(t *testing.T) {
	input := []string{
		"BFFFBBFLRR",
		"BFFFBBFRLL",
		"BFFFBBFRRL",
		"BFFFBBFRRR",
	}
	expected := 565
	actual := part2(input)

	if actual != expected {
		t.Fatalf("Part 2 failed. Expected %d, got %d", expected, actual)
	}
}
