use std::collections::HashMap;
use std::collections::HashSet;

fn main() {
    println!(
        "Part 1: {}; Part 2: {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    string.lines()
        .map(|l| {
            let splits: Vec<&str> = l.split('|').collect();
            splits[1]
        })
        .map(|l| count_unique(l))
        .sum::<usize>()
}

fn count_unique(output: &str) -> usize {
    output.trim()
        .split_whitespace()
        .filter(|val| val.len() == 2 || val.len() == 3 || val.len() == 4 || val.len() == 7)
        .count()
}

fn evaluate_part_2(string: &str) -> usize {
    string.lines()
        .map(|l| evaluate_line(l))
        .sum::<usize>()
}

fn evaluate_line(line: &str) -> usize {
    let splits: Vec<&str> = l.split('|').collect();
    let input = splits[0].trim();
    let output = splits[1].trim();

    let mapping = infer_wire_mapping(input);
}

fn infer_wire_mapping(input: &str) -> HashMap<char, char> {
    let inputs: Vec<&str> = input.split_whitespace().collect();
    let mut mapping: HashMap<char, char> = HashMap();

    let one: &str = inputs.iter().filter(|i| i.len() == 2).next().unwrap();
    let four: &str = inputs.iter().filter(|i| i.len() == 3).next().unwrap();
    let seven: &str = inputs.iter().filter(|i| i.len() == 4).next().unwrap();
    let eight: &str = inputs.iter().filter(|i| i.len() == 7).next().unwrap();

    let mapped_to_a = char_diff(seven, one)[0];
    mapping.insert('a', mapped_to_a);

    let three: &str = inputs.iter().filter(|i| {
        i.len() == 3 && i.contains(one)
    }).next().unwrap();

    let mapped_to_b = char_diff(four, one)
        .difference(three.chars().collect()).next().unwrap();
    mapping.insert('b', *mapped_to_b);

    let mapped_to_d = char_diff(four, one)
        .difference(&(HashSet::from(mapping['b']))).next().unwrap();
    mapping.insert('d', *mapped_to_b);

    mapping
}

fn char_diff(first: &str, second: &str) -> HashSet<char> {
    first.chars().collect::<HashSet<char>>().difference(
        &(second.chars().collect::<HashSet<char>>())
    ).collect()
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
    use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(26, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(5353, evaluate_part_2(include_str!("../sample.txt")));
    }
}
