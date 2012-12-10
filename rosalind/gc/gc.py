import sys
from decimal import Decimal
import operator

LETTERS = set(["A", "C", "T", "G"])

def calculate_gc_content(string):
    count = {}
    for letter in string:
        if letter in LETTERS:
            count[letter] = count.get(letter, 0) + 1
    numerator = Decimal(count["G"]) + Decimal(count["C"])
    denomenator = sum(Decimal(elem) for elem in count.values())
    return numerator / denomenator * 100

def parse_file(filepath):
    return_value = {}
    current_string = []
    current_block_id = ""
    with open(filepath) as f:
        for line in f:
            if line.startswith(">"):
                if current_string != []:
                    return_value[current_block_id] = "".join(current_string)
                    current_string = []
                current_block_id = line.strip()[1:]
                continue
            current_string.append(line.strip())
        return_value[current_block_id] = "".join(current_string)
    return return_value

if __name__ == "__main__":
    input_data = parse_file(sys.argv[1])
    gc_data = [(identifier, calculate_gc_content(string)) for (identifier, string) in input_data.iteritems()]
    gc_data.sort(key=operator.itemgetter(1), reverse=True)
    print gc_data[0][0]
    print "%.3f%%" % gc_data[0][1]

