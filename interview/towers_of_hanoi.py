def recursive_move_tower(height, from_pole, to_pole, with_pole):
    if height >= 1:
        recursive_move_tower(height - 1, from_pole, with_pole, to_pole)
        recursive_move_disk(from_pole, to_pole)
        recursive_move_tower(height - 1, with_pole, to_pole, from_pole)


def recursive_move_disk(from_pole, to_pole):
    print("moving disk from %s to %s" % (from_pole, to_pole))
