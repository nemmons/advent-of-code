fn main() {
    println!(
        "Part 1: {}; Part 2: {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> isize {
    let crabs: Vec<isize> = string
        .split(',')
        .map(|s| s.parse::<isize>().unwrap())
        .collect();

    crabs
        .iter()
        .map(|c| calculate_total_fuel_for_constant_travel_cost(&crabs, c))
        .min()
        .unwrap()
}

fn calculate_total_fuel_for_constant_travel_cost(crabs: &Vec<isize>, target_pos: &isize) -> isize {
    crabs
        .iter()
        .map(|c| isize::abs(c - target_pos))
        .sum::<isize>()
}

fn evaluate_part_2(string: &str) -> isize {
    let crabs: Vec<isize> = string
        .split(',')
        .map(|s| s.parse::<isize>().unwrap())
        .collect();

    let max_pos = crabs.iter().max().unwrap();
    (0..(max_pos + 1))
        .map(|c| calculate_total_fuel_for_increasing_travel_cost(&crabs, c))
        .min()
        .unwrap()
}

fn calculate_total_fuel_for_increasing_travel_cost(crabs: &Vec<isize>, target_pos: isize) -> isize {
    (*crabs)
        .iter()
        .map(|c| {
            let distance = isize::abs(c - target_pos);
            distance * (distance + 1) / 2 //1 + 2 + 3 + ... + n = n * (n+1)/2
        })
        .sum::<isize>()
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
    use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(37, evaluate_part_1(include_str!("../sample.txt")));
    }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(168, evaluate_part_2(include_str!("../sample.txt")));
    }
}
