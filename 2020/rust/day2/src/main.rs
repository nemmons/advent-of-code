// use itertools::Itertools;
use regex::Regex;

fn main() {
    println!("{} {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    return
        string
            .lines()
            .filter(|x| validate_password_1(x))
            .count()
}

fn evaluate_part_2(string: &str) -> usize {
    return
        string
            .lines()
            .filter(|x| validate_password_2(x))
            .count()
}

fn validate_password_1(line: &str) -> bool {
    let re = Regex::new(r"(\d+)-(\d+) ([a-z]): ([a-z]+)").unwrap();
    let caps = re.captures(line).unwrap();
    let min_letter_count = caps[1].parse::<usize>().unwrap();
    let max_letter_count = caps[2].parse::<usize>().unwrap();
    let desired_letter = caps[3].parse::<char>().unwrap();
    let char_count = caps[4].matches(desired_letter).count();

    return char_count >= min_letter_count && char_count <= max_letter_count
}

fn validate_password_2(line: &str) -> bool {
    let re = Regex::new(r"(\d+)-(\d+) ([a-z]): ([a-z]+)").unwrap();
    let caps = re.captures(line).unwrap();
    let pos_1 = caps[1].parse::<usize>().unwrap();
    let pos_2 = caps[2].parse::<usize>().unwrap();
    let desired_letter = caps[3].as_bytes().first().unwrap();
    let password = caps[4].as_bytes();

    let pos_1_valid = password.get(pos_1 - 1).unwrap() == desired_letter;
    let pos_2_valid = password.get(pos_2 - 1).unwrap() == desired_letter;

    return pos_1_valid ^ pos_2_valid
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
    use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(2, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(1, evaluate_part_2(include_str!("../sample.txt")));
    }
}