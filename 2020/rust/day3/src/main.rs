fn main() {
    println!("{}",
        evaluate_part_1(include_str!("../input.txt")),
    )
}

fn evaluate_part_1(string: &str) -> usize {
    let mut count: usize = 0;
    let mut x: usize = 0;
    for line in string.lines() {
        if line.as_bytes()[x % line.len()] == b'#' {
            count = count + 1;
        }
        x = x + 3;
    }
    return count
}


#[cfg(test)]
mod test {
    use crate::evaluate_part_1;

    #[test]
    fn it_calculates_the_sample_correctly_for_part_1() {
        assert_eq!(7, evaluate_part_1(include_str!("../sample.txt")));
    }

}