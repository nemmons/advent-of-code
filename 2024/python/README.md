# 2024 Advent of Code

I copied a setup template from https://github.com/tomfran/advent-of-code-setup, the original readme is below
and the license is preserved in this directory.

Note: if you clone this repo and try to run it, the tests will fail. You'd need to create a test_inputs folder containing files (`01`,`02`,...) containing the test input. TODO: Write a command to autogenerate these.. 

# Advent of code setup

Jumpstart your Advent of Code experience. Automate input downloads and easily create templates for your solutions, so you can focus on solving the challenges.

## Setup
Run `make setup`, this will create the `inputs` directory and the `session.cookie` file, please remember to fill the latter with your session cookie to auto-download inputs, and add a newline after it, just in case.
To find it on Chrome: right-click, inspect, Application tab, Storage, Cookies, session.

If you plan on making a fork public, you might want to add this file to `.gitignore`.

## Creating a new solution

```make new``` creates a new file for today, it checks for the files in `src/` and creates the "next int" one. On the first run it will create `01.py`, later `02.py`, and so on.

A new solution is initialized as follows: 
```
from utils.api import get_input

input_str = get_input(1)

# WRITE YOUR SOLUTION HERE
```
The `get_input` function takes a day and returns the content of the input for that day, this internally makes a request to obtain the input if it is not found on disk. 

## Running a new solution

From the main directory, run `python src/<DAY>.py`.

## Pretty README
`make readme` creates a cool `README.md` file with a list of the solutions in the source directory, with working links. Note that this original readme will be overwritten.
If you want to use this, please edit `src/utils/build_md.py` with the correct repository link.
Also, the `parse` method could be extended to display what you want for each solution. For instance, by uncommenting line 8, and renaming your files like `DAY_Cool_Problem_name.py`, you will get a list entry like `DAY. Cool Problem name`.

## Solving other years
This repo can be used to setup past years aswell, the only downside is that you need to change the `YEAR = 2022` variable on `src/utils/api.py`.

--- 
If you found this useful please leave a star, or even better get in touch! Happy coding :christmas_tree: