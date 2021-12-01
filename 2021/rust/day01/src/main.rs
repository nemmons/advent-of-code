use itertools::Itertools;

fn main() {
    println!("Part 1: {}; Part 2: {}",
     evaluate_part_1(include_str!("../input.txt")),
     evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    return
        string
            .lines()
            .map(|i| i.parse::<usize>().unwrap())
            .count()
}

fn evaluate_part_2(string: &str) -> usize {
    return
        string
            .lines()
            .map(|i| i.parse::<usize>().unwrap())
            .sum()
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
    use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(3, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(6, evaluate_part_2(include_str!("../sample.txt")));
    }
}