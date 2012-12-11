import itertools
from pprint import pprint

MATCH_LENGTH = 3

def get_dna_strings_from_fasta_file(lines):
    dna_strings = {}
    with open("rosalind_grph.txt") as f:
        current_dna_name = None
        current_dna_string = []
        for line in f:
            if line.startswith(">"):
                if current_dna_name is not None:
                    dna_strings[current_dna_name] = "".join(current_dna_string)
                current_dna_name = line.strip().strip(">")
                current_dna_string = []
                continue
            current_dna_string.append(line.strip())
        dna_strings[current_dna_name] = "".join(current_dna_string)
    return dna_strings

def get_adjacency_list(dna_strings):
    adjacency_list = []
    for (dna_name, dna_string) in dna_strings.items():
        for (prospective_dna_name, prospective_dna_string) in dna_strings.items():
            if dna_string == prospective_dna_string:
                continue
            if dna_string[-MATCH_LENGTH:] != prospective_dna_string[:MATCH_LENGTH]:
                continue
            adjacency_list.append((dna_name, prospective_dna_name))
    return adjacency_list

if __name__ == "__main__":
    dna_strings = get_dna_strings_from_fasta_file("rosalind_grph.txt")
    adjacency_list = get_adjacency_list(dna_strings)
    for (key, value) in adjacency_list:
        print "%s %s" % (key, value)

