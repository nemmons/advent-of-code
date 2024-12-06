from typing import List

import requests
from os.path import exists


def get_session_id(filename):
    with open(filename) as f:
        return f.read().strip()


def get_url(year, day):
    return f"https://adventofcode.com/{year}/day/{day}/input"


YEAR = 2024
SESSION_ID_FILE = "session.cookie"
SESSION = get_session_id(SESSION_ID_FILE)
HEADERS = {
    "User-Agent": "github.com/tomfran/advent-of-code-setup reddit:u/fran-sch, discord:@tomfran#5786"
}
COOKIES = {"session": SESSION}


def get_input(day: int) -> List[str]:
    path = f"inputs/{day:02d}"

    if not exists(path):
        url = get_url(YEAR, day)
        response = requests.get(url, headers=HEADERS, cookies=COOKIES)
        if not response.ok:
            raise RuntimeError(
                f"Request failed\n\tstatus code: {response.status_code}\n\tmessage: {response.content}"
            )
        with open(path, "w") as f:
            f.write(response.text[:-1])

    with open(path, "r") as f:
        return [line for line in f]


def create_test_input_file(day: int):
    path = f"test_inputs/{day:02d}"

    with open(path, "w") as f:
        f.write("[Put the test input here]")


def read_test_input(day: str) -> List[str]:
    path = f"test_inputs/{day:02}"

    with open(path, "r") as f:
        return [line for line in f]
