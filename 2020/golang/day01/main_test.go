package main

import "testing"

func TestPart1(t *testing.T) {
	input := []int{
		1721,
		979,
		366,
		299,
		675,
		1456,
	}

	expected := 514579
	actual := part1(input)

	if actual != expected {
		t.Fatalf("Part 1 failed. Expected %d, got %d", expected, actual)
	}
}

func TestPart2(t *testing.T) {
	input := []int{
		1721,
		979,
		366,
		299,
		675,
		1456,
	}

	expected := 241861950
	actual := part2(input)

	if actual != expected {
		t.Fatalf("Part 1 failed. Expected %d, got %d", expected, actual)
	}
}
