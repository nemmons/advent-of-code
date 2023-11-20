package util

import (
	"os"
	"strconv"
)
import "strings"

func ReadInput(path string) []string {
	data, err := os.ReadFile(path)
	if err != nil {
		panic(err)
	}

	trimmed := strings.TrimSpace(string(data))
	return strings.Split(trimmed, "\n")
}

func ReadIntput(path string) []int {
	input := ReadInput(path)

	var output []int

	for _, str := range input {
		res, err := strconv.ParseInt(str, 10, 64)
		if err != nil {
			panic(err)
		}
		output = append(output, int(res))
	}

	return output
}
