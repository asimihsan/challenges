import os
import sys
import re
import requests
from string import Template

# -----------------------------------------------------------------------------
#   Constants.
# -----------------------------------------------------------------------------
PROTEIN_URI_TEMPLATE = Template("http://www.uniprot.org/uniprot/${protein_id}.txt")
RE_N_GLYCOSYLATION = re.compile("(?=(?:N[^P](?:S|T)[^P]))")
# -----------------------------------------------------------------------------

def get_protein_sequence(protein_id):
    uri = PROTEIN_URI_TEMPLATE.substitute(protein_id = protein_id)
    request = requests.get(uri)
    request.raise_for_status()

    sequence_lines = []
    sequence_found = False
    for line in request.text.splitlines():
        if line.startswith("SQ"):
            sequence_found = True
            continue
        if line.startswith(r'//'):
            break
        if sequence_found:
            sequence_lines.extend(line.strip().split())
    return "".join(sequence_lines)

def get_motif_locations(sequence, motif):
    matches = re.finditer(motif, sequence)
    positions = (match.start() + 1 for match in matches)
    return " ".join([str(elem) for elem in positions])

def process_file(filepath):
    results = []
    with open(filepath) as f:
        for line in f:
            protein_id = line.strip()
            sequence = get_protein_sequence(protein_id)
            positions = get_motif_locations(sequence, RE_N_GLYCOSYLATION)
            if len(positions) > 0:
                print protein_id
                print positions

if __name__ == "__main__":
    #sequence = get_protein_sequence("P20840_SAG1_YEAST")
    #positions = get_motif_locations(sequence, RE_N_GLYCOSYLATION)
    #print positions

    process_file(sys.argv[1])

