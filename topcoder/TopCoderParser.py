from topcoder_common import *
from Problem import *

# the text that defines a problem is missing
MISSING_PROBLEM_TEXT = u"Problem Statement not available."

class TopCoderParser(object):
    """The class that performs all of the parsing for the TopCoder pages.
    Generates Problem objects from HTML pages."""
    
    ## init ##
    def __init__(self, html):
        """Creates a new parser object, using the HTML given."""
        # save as soup
        self.soup = BeautifulSoup(html)

    ## private object methods ##
    def _is_missing_problem(self):
        """Checks if this page contains a problem that is missing.
        Returns True if it contains the 'problem missing' text, False if not."""
        if self.soup.find("td", {"class": "problemText"}).getText() == MISSING_PROBLEM_TEXT:
            return True
        return False
        
    def _get_header(self, text):
        """Returns a reference to the soup header tag that contains the given text,
        or None if the header was not found.
        Looks for h3 tags, since TopCoder headers use this format."""
        return self.soup.find("h3", text=re.compile(text))

    def _scrape_piece(self, piece):
        """Scrape the HTML for a particular piece, returning the piece on success.
        The pieces correspond to the keys in the object dictionary."""

        if piece == P_PROBLEM_NUMBER:
            # cannot get the problem number from just the HTML
            return None
        
        elif piece == P_PROBLEM_NAME:
            # get problem name (without HTML)
            problem_name_text = self.soup.find("td", {"class": "statTextBig"}).getText()
            return re.findall("Problem statement for (.+)", problem_name_text, re.DOTALL | re.IGNORECASE | re.MULTILINE)[0]

        elif piece == P_PROBLEM_STATEMENT:
            # get problem statement (with HTML)
            problem_statement_tag = self.soup.find("td", {"class": "problemText"}).findAll("td", {"class": "statText"})[2]
            return extract_html(problem_statement_tag)

        elif piece == P_PROBLEM_DEFINITION:
            # get problem definition (without HTML)
            definitions_header = self._get_header('Definition')
            definition = dict(EMPTY_DEFINITIONS_DICT)
            if definitions_header:
                definitions_table = definitions_header.parent.parent.parent.nextSibling.find("table")
                class_row, method_row, params_row, returns_row, signature_row, ensure_public_row = definitions_table.findAll("tr")
                definition['class'] = class_row.findAll("td")[1].text
                definition['method'] = method_row.findAll("td")[1].text
                definition['types']['input'] = params_row.findAll("td")[1].text.split(', ')
                definition['types']['output'] = returns_row.findAll("td")[1].text

                # parse signature
                signature = signature_row.findAll("td")[1].text
                parts = re.findall("(.+?) (.+?)\((.+?)\)", signature)[0]
                definition['names']['input'] = [x.split()[-1] for x in parts[2].split(', ')]

            return definition

        elif piece == P_PROBLEM_CONSTRAINTS:
            # get constraints (with HTML)
            constraints_header = self._get_header('Constraints')
            if constraints_header:
                constraints = []
                constraint_bullets = constraints_header.parent.parent.parent.findAllNext("td", text="-")
                for bullet in constraint_bullets:
                    constraints.append(extract_html(bullet.parent.parent.findAll("td")[1]))
                return constraints
            else:
                return None

        elif piece == P_PROBLEM_EXAMPLES:
            examples_header = self._get_header("Examples")
            examples = []
            if examples_header:
                examples_numbers = examples_header.parent.parent.parent.findAllNext("td", text=re.compile("^\d+\)$"))
                for number in examples_numbers:
                    new_example = dict(EMPTY_EXAMPLE_DICT)
                    example_table = number.parent.parent.nextSibling.find("table")
                    
                    # get input (without HTML)
                    params_table = example_table.findAll("tr")[0].find("table")
                    new_example['input'] = [eval_variable(x.getText().strip()) for x in params_table.findAll("td")]

                    # get output (without HTML)
                    returns_row = example_table.findAll("tr")[1 + len(new_example['input'])]
                    new_example['output'] = eval_variable(re.findall("Returns: (.+)", returns_row.getText(), re.DOTALL | re.IGNORECASE | re.MULTILINE)[0].strip())

                    # get comment (with HTML)
                    comments_row = example_table.findAll("tr")[2 + len(new_example['input'])]
                    new_example['comment'] = extract_html(comments_row.find("td"))

                    # save example
                    examples.append(new_example)
            return examples

        elif piece == P_SUBMISSION_LISTING_LINK:
            contest_link = self.soup.find("a", {"href": re.compile("/tc\?module=ProblemDetail&.+")})['href']
            return 'http://community.topcoder.com' + contest_link

        elif piece == P_SUBMISSION_LINK:
            submission_link = self.soup.find("a", {"href": re.compile("(/stat\?c=problem_solution&.+)|(/tc\?module=HSProblemSolution&.+)")})
            if submission_link:
                return 'http://community.topcoder.com' + submission_link['href']
            return None
            
        elif piece == P_PROBLEM_TESTS:
            # get the system tests (no HTML) - can only happen on a submission page
            tests = []
            test_inputs = self.soup.findAll("td", {"class": "statText", "align": "left"})
            for i in range(len(test_inputs)):
                new_test = {'input': [], 'output': None}

                # parse test input
                test_input_cell = test_inputs[i]
                new_test['input'] = [eval_variable(x.strip()) for x in test_input_cell.getText().split(',\n')]

                # extract test output
                test_output_cell = test_inputs[i].parent.findAll("td")[3]
                new_test['output'] = eval_variable(test_output_cell.getText().strip())

                # save test
                tests.append(new_test)
            return tests

        else:
            # not recognised
            return None

    def _scrape_pieces(self, pieces, problem = None):
        """Scrapes the given pieces and saves them to the given problem object,
        or, if none is given, creates a new problem object.

        Returns the updated problem object on success."""
        
        if problem == None:
            problem = Problem()

        # get each piece
        for piece in pieces:
            result = self._scrape_piece(piece)
            problem[piece] = result
        
        return problem
            
    ## public object methods ##  
    def parse_problem_page(self, problem = None):
        """Parses the problem page, returning the new Problem object generated
        from the process (or updating a given one).

        The following pieces are updated:
        P_PROBLEM_NAME
        P_PROBLEM_STATEMENT
        P_PROBLEM_DEFINITION
        P_PROBLEM_CONSTRAINTS
        P_PROBLEM_EXAMPLES
        P_SUBMISSION_LISTING_LINK
        """

        # first check if the problem is OK
        if self._is_missing_problem():
            return None

        return self._scrape_pieces([P_PROBLEM_NAME,
            P_PROBLEM_STATEMENT,
            P_PROBLEM_DEFINITION,
            P_PROBLEM_CONSTRAINTS,
            P_PROBLEM_EXAMPLES,
            P_SUBMISSION_LISTING_LINK], problem)

    def parse_submission_listing_page(self, problem = None):
        """Parses the problem submission listing page, returning the new Problem
        object generated (or updating a given one).
        
        The following pieces are updated:
        P_SUBMISSION_LINK
        """

        return self._scrape_pieces([P_SUBMISSION_LINK], problem)

    def parse_submission_page(self, problem = None):
        """Parses the problem submission page, returning the new Problem object
        generated (or updating a given one).

        The following pieces are updated:
        P_PROBLEM_TESTS
        """
        
        return self._scrape_pieces([P_PROBLEM_TESTS], problem)
    
## helper functions ##
def scrape_problem(n, opener = None):
    """Attempts to scrape the TopCoder problem with ID n from the website.
    If given an opener, attempts to use it, otherwise connects to TopCoder.
    On success, returns a new Problem object."""

    if opener == None:
        print "Connecting to TopCoder...",
        opener = connect_to_topcoder()
        print "OK"

    # load problem page and scrape problem
    print "Loading problem page...",
    problem_page_html = get_topcoder_problem_page(opener, n)
    problem = TopCoderParser(problem_page_html).parse_problem_page()

    if problem == None:
        print "Problem does not exist."
        return None
    print "OK"

    # load submission listing page and scrape submission link
    print "Loading submission listing page...",
    if problem[P_SUBMISSION_LISTING_LINK]:
        submission_listing_page_html = open_page(opener, problem[P_SUBMISSION_LISTING_LINK])
        problem = TopCoderParser(submission_listing_page_html).parse_submission_listing_page(problem)
        print "OK"

        print "Loading submission page...",
        if problem[P_SUBMISSION_LINK]:
            # load submission page and scrape tests
            submission_page_html = open_page(opener, problem[P_SUBMISSION_LINK])
            problem = TopCoderParser(submission_page_html).parse_submission_page(problem)
            print "OK"
        else:
            print "WARNING: Problem has no submissions."
    else:
        print "WARNING: Problem was not used in any competitions."

    # remove the links
    del problem[P_SUBMISSION_LISTING_LINK]
    del problem[P_SUBMISSION_LINK]

    # save the problem number
    problem[P_PROBLEM_NUMBER] = n

    # done!
    return problem
