use itertools::Itertools;

fn main() {
    println!(
        "Part 1: {}; Part 2: {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

fn evaluate_part_1(string: &str) -> usize {
    let depth_measurements: Vec<usize> = string
        .lines()
        .map(|i| i.parse::<usize>().unwrap())
        .collect();

    let mut increasing_depth_count: usize = 0;

    for i in 1..depth_measurements.len() {
        let measurement = depth_measurements.get(i).unwrap();
        let prev_measurement = depth_measurements.get(i - 1).unwrap();
        if measurement > prev_measurement {
            increasing_depth_count += 1;
        }
    }

    increasing_depth_count
}

fn evaluate_part_2(string: &str) -> usize {
    let depth_measurements: Vec<usize> = string
        .lines()
        .map(|i| i.parse::<usize>().unwrap())
        .collect();

    let mut deeper_count: usize = 0;

    for i in 1..(depth_measurements.len() - 2) {
        let window_1_start = depth_measurements.get(i - 1).unwrap();
        // depths[i] and depths[i+1] are shared between both sliding windows
        let window_2_end = depth_measurements.get(i + 2).unwrap();

        if window_2_end > window_1_start {
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
