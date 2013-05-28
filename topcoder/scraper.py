#!/usr/bin/env python

from ProblemFolder import *
import argparse
import sys

def get_numbers_from_list(num_text):
    """Given a list of numbers as text (e.g. '1-2,5,7-9'), returns a list of these
    numbers."""
    nums = set()
    num_tokens = num_text.replace(' ', '').replace('\t', '').split(',')
    for token in num_tokens:
        if '-' in token:
            start, end = token.split('-')
            nums.update(range(int(start), int(end) + 1))
        else:
            nums.add(int(token))
    return sorted(nums)

def get_topcoder_problem_ids(opener, n, end = None):
    """Returns the first n TopCoder problem numbers from the problem listing.
    If given two numbers, returns all topcoder problem numbers found between those
    two numbers."""

    # calculate start and end
    if end == None:
        start = 0
        end = n
    else:
        start = n

    # open the problems listing page
    soup = BeautifulSoup(open_page(opener, TOPCODER_LISTING_URL_FORMAT % (start, end)))

    # extract all problem links
    problem_nos = set()
    problem_no_re = re.compile(TOPCODER_LISTING_LINK_RE)
    link_tags = soup.findAll("a", {"class": "statText", "href": problem_no_re})
    for link_tag in link_tags:
        problem_nos.add(int(re.findall(problem_no_re, link_tag['href'])[0]))

    return problem_nos


DEFAULT_PROBLEMS_DIRECTORY = "problems"

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Fetches problems from TopCoder.com. See the README for more information.")
    parser.add_argument('problems', action="store",
                        help="The problems to fetch. Can be a list (e.g. 1-3, 5).")
    parser.add_argument('-o', '--output_dir', action="store", dest="output_dir",
                        help="The output directory (without a trailing slash).",
                        default=DEFAULT_PROBLEMS_DIRECTORY)
    parser.add_argument('-s', '--smart', action="store_true",
                        help="If specified, operates in smart mode, and looks for n new problems from TopCoder. Specify two numbers to find all problems between these two numbers. Does NOT download existing problems.")
    parser.add_argument('-f', '--force', action="store_true",
                        help="If specified, overwrites existing problem Python files, and downloads problems even if they already exist.")
    parser.add_argument('-t', '--test', action="store_true",
                        help="If specified, runs tests for the specified problem numbers instead of running them.")
    parser.add_argument('-c', '--clean', action="store_true",
                        help="If specified, cleans the output directory first.")
    parser.add_argument('-d', '--debug', action="store_true",
                        help="If specified, displays the full traceback when a scraping error occurs.")
    args = parser.parse_args()

    # parse out problem numbers
    problem_input = sorted(get_numbers_from_list(args.problems))

    # scan output directory
    folder = ProblemFolder(args.output_dir)

    if args.clean:
        print "Cleaning problems directory...",
        folder.clean()
        print "OK"

    if args.test:
        # test specified problems
        for n in problem_input:
            folder.test_problems(folder.find_problem(number = n))

    else:
        # connect to TopCoder
        print "Connecting to TopCoder...",
        opener = connect_to_topcoder()
        print "OK"

        # are we in smart mode?
        if args.smart:
            # look for problem numbers first
            print "Looking for problems...",
            if len(problem_input) == 2:
                problem_numbers = get_topcoder_problem_ids(opener, problem_input[0], problem_input[1])
            else:
                problem_numbers = get_topcoder_problem_ids(opener, problem_input[0])
            print "%d problems found." % len(problem_numbers)
        else:
            problem_numbers = problem_input

        # don't re-download existing problems, unless in forced mode
        if not args.force:
            existing_ids = folder.get_problem_numbers()
            problem_numbers = [x for x in problem_numbers if x not in existing_ids]

        print "--- Scraping %d problems ---" % len(problem_numbers)

        # scrape problems
        for n in problem_numbers:
            print " * Scraping problem %d." % n

            if args.debug:
                # display errors
                folder.scrape_and_add_problem(n, opener=opener, force=args.force)
            else:
                # catch errors
                try:
                    folder.scrape_and_add_problem(n, opener=opener, force=args.force)
                except Exception, e:
                    print "ERROR: %s" % e

        print "--- OK ---"

