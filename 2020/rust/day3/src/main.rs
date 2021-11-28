fn main() {
    println!("{} {}",
        evaluate_part_1(include_str!("../input.txt")),
        evaluate_part_2(include_str!("../input.txt")),
    )
}

fn count_trees(slope_map: &Vec<&str>, velocity: (usize, usize)) -> usize {
    let mut tree_count: usize = 0;
    let mut x: usize = 0;
    let mut y: usize = 0;

    while y < slope_map.len() {
        if slope_map[y].as_bytes()[x % slope_map[y].len()] == b'#' {
            tree_count = tree_count + 1;
        }
        y = y + velocity.0;
        x = x + velocity.1;
    }

    return tree_count
}

fn evaluate_part_1(string: &str) -> usize {
    return count_trees(&(string.lines().collect()), (1, 3));
}

fn evaluate_part_2(string: &str) -> usize {
    let trajectories = [
        (1,1),
        (1,3),
        (1,5),
        (1,7),
        (2,1)
    ];
    let slope_map = string.lines().collect();
    trajectories
        .map(|trajectory| count_trees(&slope_map, trajectory))
        .iter()
        .fold(1, |x,y| x * y)
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
        assert_eq!(336, evaluate_part_2(include_str!("../sample.txt")));
    }
}