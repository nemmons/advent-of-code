import os
from api import get_input, create_test_input_file

l = filter(lambda x: "__" not in x and ".py" in x, os.listdir("src"))
l = list(l)
n = int(sorted(l)[-1][:2]) + 1 if len(l) > 0 else 1

DEFAULT_FILE = open("src/utils/template.py").read().replace("[DAY_NUM]", str(n))

get_input(n)
create_test_input_file(n)

path = f"src/{n:02d}.py"
with open(path, "w") as f:
    f.write(DEFAULT_FILE)

print(f"Enter your solution in {path}")
