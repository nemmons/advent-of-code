from typing import List
from math import log2

ROW_COUNT = 128
SEATS_PER_ROW = 8


def process_input(file='input') -> List[str]:
    passes = []

    with open(file) as f:
        for line in f:
            passes.append(line.rstrip())
    return passes


def get_seat_id(boarding_pass: str):
    pass_char_count = log2(ROW_COUNT)
    seat_char_count = len(boarding_pass) - pass_char_count
    assert seat_char_count == 3

    rows = range(ROW_COUNT)

    while len(boarding_pass) > seat_char_count:
        next_char = boarding_pass[0]
        boarding_pass = boarding_pass[1:]

        if next_char == 'F':
            rows = rows[:int(len(rows)/2)]
        elif next_char == 'B':
            rows = rows[int(len(rows)/2):]
    row = rows[0]

    cols = range(SEATS_PER_ROW)
    while len(boarding_pass) > 0:
        next_char = boarding_pass[0]
        boarding_pass = boarding_pass[1:]

        if next_char == 'L':
            cols = cols[:int(len(cols)/2)]
        elif next_char == 'R':
            cols = cols[int(len(cols)/2):]
    col = cols[0]

    return (row * 8) + col


seat_ids = [get_seat_id(bp) for bp in process_input()]
print("Part 1: Max Seat Id:")
print(max(seat_ids))

seat_ids.sort()
print("Part 2: My Seat Id:")
for i, seat_id in enumerate(seat_ids):
    if seat_ids[i+1] == seat_id+2:
        print(seat_id+1)
        break

