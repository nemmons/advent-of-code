toboggan_map = []
with open('input') as f:
    for line in f:
        toboggan_map.append(line.rstrip())

map_height = len(toboggan_map)
chunk_width = len(toboggan_map[0])

x = 0
y = 0

slope_x = 3
slope_y = 1

tree_count = 0

while y < map_height - 1:
    x = (x + slope_x) % chunk_width
    y += slope_y

    print(y,x)

    if toboggan_map[y][x] == '#':
        tree_count += 1

print(tree_count)

