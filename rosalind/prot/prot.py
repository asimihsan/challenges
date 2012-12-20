import os
import sys
import ply.lex as lex

class ProteinLexer(object):
    codon_lookup = {
        'UUU': 'F',      'CUU': 'L',      'AUU': 'I',      'GUU': 'V',
        'UUC': 'F',      'CUC': 'L',      'AUC': 'I',      'GUC': 'V',
        'UUA': 'L',      'CUA': 'L',      'AUA': 'I',      'GUA': 'V',
        'UUG': 'L',      'CUG': 'L',      'AUG': 'M',      'GUG': 'V',
        'UCU': 'S',      'CCU': 'P',      'ACU': 'T',      'GCU': 'A',
        'UCC': 'S',      'CCC': 'P',      'ACC': 'T',      'GCC': 'A',
        'UCA': 'S',      'CCA': 'P',      'ACA': 'T',      'GCA': 'A',
        'UCG': 'S',      'CCG': 'P',      'ACG': 'T',      'GCG': 'A',
        'UAU': 'Y',      'CAU': 'H',      'AAU': 'N',      'GAU': 'D',
        'UAC': 'Y',      'CAC': 'H',      'AAC': 'N',      'GAC': 'D',
        'UAA': 'Stop',   'CAA': 'Q',      'AAA': 'K',      'GAA': 'E',
        'UAG': 'Stop',   'CAG': 'Q',      'AAG': 'K',      'GAG': 'E',
        'UGU': 'C',      'CGU': 'R',      'AGU': 'S',      'GGU': 'G',
        'UGC': 'C',      'CGC': 'R',      'AGC': 'S',      'GGC': 'G',
        'UGA': 'Stop',   'CGA': 'R',      'AGA': 'R',      'GGA': 'G',
        'UGG': 'W',      'CGG': 'R',      'AGG': 'R',      'GGG': 'G',
    }

    tokens = [key for key in codon_lookup]
    for (key, value) in codon_lookup.items():
        string = """def t_%s(self, t):
                        r'%s'
                        t.value = '%s'
                        return t""" % (key, key, value)
        exec(string)

    # Define a rule so we can track line numbers
    def t_newline(self, t):
        r'\n+'
        t.lexer.lineno += len(t.value)

    # Ignored characters
    t_ignore = ' \t\r'

    # Error handling rule
    def t_error(self, t):
        print "Illegal character at %s: '%s'" % (t.lexpos, t.value[0])
        t.lexer.skip(1)

    # Build the lexer
    def build(self, **kwargs):
        self.lexer = lex.lex(module=self, **kwargs)

    def test(self, data, token_callback):
        self.lexer.input(data)
        while True:
            t = self.lexer.token()
            if not t:
                break
            result = token_callback(t)
            if not result:
                break

if __name__ == "__main__":
    lexer = ProteinLexer()
    lexer.build()

    with open(sys.argv[1]) as f:
        data = f.read()
    result = []
    def token_callback(t):
        if t.value == "Stop":
            return False
        result.append(t.value)
        return True
    lexer.test(data, token_callback)
    print ''.join(result)


