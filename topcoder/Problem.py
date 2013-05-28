import json
from UserDict import IterableUserDict

## topcoder problem pieces ##
# main pieces
P_PROBLEM_NUMBER = 'number'
P_PROBLEM_NAME = 'name'
P_PROBLEM_STATEMENT = 'statement'
P_PROBLEM_DEFINITION = 'definition'
P_PROBLEM_CONSTRAINTS = 'constraints'
P_PROBLEM_EXAMPLES = 'examples'
P_PROBLEM_TESTS = 'tests'

# internal processing pieces
P_SUBMISSION_LISTING_LINK = 'submission_list_link'
P_SUBMISSION_LINK = 'submission_link'

# HTML-only pieces
P_PAGE_TITLE = 'page_title'

## topcoder problem structure ##
EMPTY_DEFINITIONS_DICT = {
    'class': None,
    'method': None,
    'types': {
        'output': None,
        'input': []
    },
    'names': {
        'input': []
    }
}

EMPTY_EXAMPLE_DICT = {
    'input': [],
    'output': None,
    'comment': None
}

EMPTY_PROBLEM_DICT = {
    P_PROBLEM_NUMBER: None,
    P_PROBLEM_NAME: None,
    P_PROBLEM_STATEMENT: None,
    P_PROBLEM_DEFINITION: dict(EMPTY_DEFINITIONS_DICT),
    P_PROBLEM_CONSTRAINTS: [],
    P_PROBLEM_EXAMPLES: [], # each example is {'input': [], 'output': None, 'comment': None}
    P_PROBLEM_TESTS: [] # each test is {'input': [], 'output': None}
}

## html output parameters ##
MAIN_HEADER_LEVEL = 1
HEADER_LEVEL = 2
SUB_HEADER_LEVEL = 3

## JSON output parameters ##
JSON_INDENT_LEVEL = 4

## python output parameters ##
PYTHON_TEMPLATE = """#!/usr/bin/python

def %s:
    pass

"""

## test icons ##
CHECK_MARK = 'Y' # u'\u2713'
CROSS_MARK = 'N' # u'\u2717'
STOP_MARK = 'X' # u'\u25A0'

class Problem(object, IterableUserDict):
    """The class for all TopCoder problems.
    Inherits from IterableUserDict, and supports all regular dictionary
    access."""

    ## init ##
    def __init__(self, json_filename = None):
        """Creates a blank problem object.

        If given a JSON filename, loads the data from that file."""
        if json_filename != None:
            json_file = open(json_filename, 'rU')
            self.data = json.load(json_file)
            json_file.close()
        else:
            self.data = dict(EMPTY_PROBLEM_DICT)

    ## private object methods ##
    def _generate_signature(self):
        """Returns the method signature for the problem, in the form
            <return type> <name>(<type> <name>, <type> <name>, ...)
        e.g. long MyFunc(int A, int B)
        """
        signature = "%s %s(" % (self['definition']['types']['output'], self['definition']['method'])
        
        for i in range(len(self['definition']['types']['input'])):
            signature += str(self['definition']['types']['input'][i])
            signature += ' '
            signature += str(self['definition']['names']['input'][i])

            # add a comma, if its not the last one
            if i != len(self['definition']['types']['input']) - 1:
                signature += ', '

        signature += ")"
        return signature

    def _generate_mini_signature(self):
        """Returns the method signature for the problem, in the form
            <name>(<name>, <name>, ...)
        e.g. MyFunc(A, B)
        """
        signature = "%s(" % self['definition']['method']
        signature += ", ".join([str(x) for x in self['definition']['names']['input']])
        signature += ")"
        return signature

    def _generate_filled_signature(self, inputs = None, output = None):
        """Returns the method signature for the problem with the given inputs
        and output, in the form
            <name>(<name>, <name>, ...) = <output>
        e.g. MyFunc("A", 1) = 12
        If no output is given, this does not add the equals sign.
        Similarly, if no input is given, only adds the equals sign and the part
        after it."""
        signature = ""
        
        # add inputs
        if inputs != None:
            signature += "%s(" % self['definition']['method']
            signature += ", ".join([repr(x) for x in inputs])
            signature += ")"

        # separate inputs and output (if adding both)
        if inputs != None and output != None:
            signature += " "

        # add output
        if output != None:
             signature += "= %r" % output
             
        return signature
        
    def _piece_to_html(self, piece):
        """Converts the given piece to HTML, returning it as a string.
        Does NOT return the HTML header for the piece, just the content."""

        if piece == P_PROBLEM_NUMBER:
            return html_text(self[piece])
            
        elif piece == P_PROBLEM_NAME:
            return html_text(self[piece])
            
        elif piece == P_PROBLEM_STATEMENT:
            return html_text(self[piece])

        elif piece == P_PROBLEM_DEFINITION:
            html = ""
            html += html_header(SUB_HEADER_LEVEL, "Filename")
            html += html_text("%s.py" % self[piece]['class'])
            
            html += html_header(SUB_HEADER_LEVEL, "Signature")
            html += html_text(self._generate_signature())

            return html

        elif piece == P_PROBLEM_CONSTRAINTS:
            return "<ul><li>" + "</li><li>".join(self[piece]) + "</li></ul>"

        elif piece == P_PROBLEM_EXAMPLES:
            html = ""
            html += "<ul>"
            for example in self[piece]:
                html += "<li>"
                html += self._generate_filled_signature(example['input'], example['output'])

                if example['comment']:
                    html += example['comment']
                
                html += "</li>"

            html += "</ul>"
            return html

        elif piece == P_SUBMISSION_LISTING_LINK:
            # not needed for HTML
            return None
            
        elif piece == P_SUBMISSION_LINK:
            # not needed for HTML
            return None
            
        elif piece == P_PROBLEM_TESTS:
            # not shown in HTML
            return None

        elif piece == P_PAGE_TITLE:
            return "%s. %s" % (self[P_PROBLEM_NUMBER], self[P_PROBLEM_NAME])

        else:
            # not recognised
            return None


    def _pieces_to_html(self, pieces):
        """Converts the given pieces to HTML, returning them as a list."""
        return [_piece_to_html(x) for x in pieces]

    def _run_test_list(self, tests, method):
        """Runs a given list of tests against the given method, returning True
        if they all passed, False if not."""
        for test in tests:
            print self._generate_filled_signature(test['input']),

            try:
                result = method(*test['input'])
                if result == None:
                    raise Exception("Function did not return anything.")
            
                print self._generate_filled_signature(output=result),
                if result != test['output']:
                    print CROSS_MARK
                    return False
                print CHECK_MARK

            except Exception, e:
                print "%s (%s)" % (STOP_MARK, str(e))
                return False
                
        return True

    ## public object methods ##
    def run_examples(self, method):
        """Runs all the examples on the given method, returning True if they
        all passed, False if not.
        """
        return self._run_test_list(self[P_PROBLEM_EXAMPLES], method)

    def run_tests(self, method):
        """Runs all the tests on the given method, returning True if they
        all passed, False if not.
        """
        return self._run_test_list(self[P_PROBLEM_TESTS], method)

    def test_method(self, method):
        """Runs all examples and tests on the given method, returning True if
        they all passed, False if not."""

        if self[P_PROBLEM_EXAMPLES]:
            print "-- Running examples --"
            result = self.run_examples(method)
            if result == False:
                print "-- Failed --"
                return False
            print "-- All passed! --"

        if self[P_PROBLEM_TESTS]:
            print "-- Running tests --"
            result = self.run_tests(method)
            if result == False:
                print "-- Failed --"
                return False
            print "-- All passed! --"

        return True
        
    # python output #
    def to_python(self, template = PYTHON_TEMPLATE):
        """Returns a Python file, with the method header, according to the
        specified python template."""
        return PYTHON_TEMPLATE % self._generate_mini_signature()
        
    def to_python_file(self, filename, template = PYTHON_TEMPLATE):
        """Saves Python text to a file, with the method header, according to the
        specified python template."""
        python_file = open(filename, 'w')
        python_file.write(self.to_python())
        python_file.close()

    # json output #
    def to_json(self):
        """Returns the problem, as a JSON string."""
        return json.dumps(self.data, indent=JSON_INDENT_LEVEL)

    def to_json_file(self, filename):
        """Saves the problem in JSON format to the given filename."""
        json_file = open(filename, 'w')
        json.dump(self.data, json_file, indent=JSON_INDENT_LEVEL)
        json_file.close()

    # html output #
    def to_html(self):
        """Returns the problem, as an HTML string."""

        html = u""

        # add title
        html += "<html><head><title>%s</title></head><body>" % self._piece_to_html(P_PAGE_TITLE)
        html += html_header(1, self._piece_to_html(P_PAGE_TITLE))

        # add each piece
        html += html_header(2, "Problem")
        html += self._piece_to_html(P_PROBLEM_STATEMENT)

        html += html_header(2, "Definition")
        html += self._piece_to_html(P_PROBLEM_DEFINITION)

        if self[P_PROBLEM_CONSTRAINTS]:
            html += html_header(2, "Constraints")
            html += self._piece_to_html(P_PROBLEM_CONSTRAINTS)

        if self[P_PROBLEM_EXAMPLES]:
            html += html_header(2, "Examples")
            html += self._piece_to_html(P_PROBLEM_EXAMPLES)

        # escape non-ascii characters with HTML
        return html.encode('ascii', 'xmlcharrefreplace')

    def to_html_file(self, filename):
        """Saves the problem in HTML format to the given filename.
        Writes in the given html encoding."""
        html_file = open(unicode(filename), 'w')
        html_file.write(self.to_html())
        html_file.close()

## helper functions ##
def html_header(level, text):
    """Returns an HTML header, containing the specified text.
    The level indicates the size of the header (1 for the largest header, larger
    numbers for deeper headers)."""
    return "<h%d>%s</h%d>" % (level, text, level)

def html_text(text):
    """Returns some text in HTML format."""
    return "<p>%s</p>" % text
