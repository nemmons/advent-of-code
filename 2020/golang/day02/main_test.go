package main

import "testing"

func TestPart1(t *testing.T) {
	input := []string{
		"1-3 a: abcde",
		"1-3 b: cdefg",
		"2-9 c: ccccccccc",
	}

	expected := 2
	actual := part1(input)

	if actual != expected {
		t.Fatalf("Part 1 failed. Expected %d, got %d", expected, actual)
	}
}

func TestPart2(t *testing.T) {
	input := []string{
		"1-3 a: abcde",
		"1-3 b: cdefg",
		"2-9 c: ccccccccc",
	}

	expected := 1
	actual := part2(input)

	if actual != expected {
		t.Fatalf("Part 2 failed. Expected %d, got %d", expected, actual)
	}
}
