use itertools::Itertools;

/**
    this solution was shamefully copied from https://github.com/timvisee/advent-of-code-2020/blob/master/day01a/src/main.rs,
   i get no credit. I fumbled around trying to use the stdlib to read the file then gave up in frustration and went hunting
   for popular alternatives. Itertools looks very powerful.
**/
fn main() {
    println!("{} {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt"))
    )
}

#[cfg(test)]
mod test {
    use crate::evaluate_part_1;
    use crate::evaluate_part_2;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
       assert_eq!(514579, evaluate_part_1(include_str!("../sample.txt")));
   }

    #[test]
    fn it_calculates_the_sample_correctly_for_part_2() {
        assert_eq!(241861950, evaluate_part_2(include_str!("../sample.txt")));
    }
}

fn evaluate_part_1(string: &str) -> usize {
    return
        string
            .lines()
            .map(|i| i.parse::<usize>().unwrap())
            .combinations(2)
            .filter(|i| i.iter().sum::<usize>() == 2020)
            .next()
            .map(|i| i.iter().product::<usize>())
            .unwrap();
}

fn evaluate_part_2(string: &str) -> usize {
    return
        string
            .lines()
            .map(|i| i.parse::<usize>().unwrap())
            .combinations(3)
            .filter(|i| i.iter().sum::<usize>() == 2020)
            .next()
            .map(|i| i.iter().product::<usize>())
            .unwrap();
}