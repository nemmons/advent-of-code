use itertools::Itertools;

fn main() {
    println!("Part 1: {}; Part 2: {}",
     evaluate_part_1(include_str!("../input.txt")),
     evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    let depths: Vec<usize> = string
        .lines()
        .map(|i| i.parse::<usize>().unwrap())
        .collect();

    let mut deeper_count: usize = 0;

    for (index, depth) in depths.iter().enumerate() {
        if index > 0 && depth > depths.get(index-1).unwrap() {
            deeper_count = deeper_count + 1;
        }
    }

    deeper_count
}

fn evaluate_part_2(string: &str) -> usize {
    let depths: Vec<usize> = string
        .lines()
        .map(|i| i.parse::<usize>().unwrap())
        .collect();

    let mut deeper_count: usize = 0;

    for i in 1..(depths.len()-2) {
        let window = depths.get(i-1).unwrap()
            + depths.get(i).unwrap()
            + depths.get(i+1).unwrap();

        let next_window = depths.get(i).unwrap()
            + depths.get(i+1).unwrap()
            + depths.get(i+2).unwrap();

        if next_window > window {
            deeper_count += 1;
        }
    }

    deeper_count
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
   use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(7, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(5, evaluate_part_2(include_str!("../sample.txt")));
    }
}