use regex::Regex;

fn main() {
    println!(
        "Part 1: {}; Part 2: {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    let re = Regex::new(r"^([A-Za-z]+) (\d+)$").unwrap();

    let inputs: Vec<(String, usize)> = string
        .lines()
        .map(|line| parse_movement(&line, &re))
        .collect();

    let pos: usize = inputs.iter()
        .filter(|(direction, _)| direction == "forward")
        .map(|(_, amount)| *amount)
        .sum();

    let minus_depth: usize = inputs.iter()
        .filter(|(direction, _)| direction == "up")
        .map(|(_, amount)| *amount)
        .sum();

    let plus_depth: usize = inputs.iter()
        .filter(|(direction, _)| direction == "down")
        .map(|(_, amount)| *amount)
        .sum();

    let depth = plus_depth - minus_depth;

    pos * depth
}

fn evaluate_part_2(string: &str) -> usize {
    let re = Regex::new(r"^([A-Za-z]+) (\d+)$").unwrap();

    let mut aim: usize = 0;
    let mut position: usize = 0;
    let mut depth: usize = 0;

    string
        .lines()
        .map(|line| parse_movement(&line, &re))
        .for_each(|instruction| process_instruction(instruction, &mut aim, &mut position, &mut depth));

    return position * depth
}

fn process_instruction(instruction: (String, usize), aim: &mut usize, position: &mut usize, depth: &mut usize) {
    let (direction, amount) = instruction;

    if direction == "forward".parse::<String>().unwrap()
    {
        *position += amount;
        *depth += amount * *aim;
    } else if direction == "up".parse::<String>().unwrap() {
        *aim -= amount;
    } else if direction == "down".parse::<String>().unwrap() {
        *aim += amount;
    }
}

fn parse_movement(line: &str, re: &Regex) -> (String, usize) {
    let captures = re.captures(line).unwrap();
    (captures[1].parse().unwrap(), captures[2].parse().unwrap())
}


#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
   use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(150, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(900, evaluate_part_2(include_str!("../sample.txt")));
    }
}
