"""Some common functions for connecting to and parsing TopCoder pages."""

import cookielib
import re
import urllib, urllib2
import base64
import urlparse
import os
import json
from BeautifulSoup import BeautifulSoup
import string

from htmlentitydefs import name2codepoint

# the topcoder pages of interest
TOPCODER_LOGIN_URL = 'http://community.topcoder.com/tc?&module=Login'
TOPCODER_PROBLEM_URL_FORMAT = "http://community.topcoder.com/stat?c=problem_statement&pm=%d"
TOPCODER_LISTING_URL_FORMAT = 'http://community.topcoder.com/tc?module=ProblemArchive&sr=%d&er=%d'
TOPCODER_LISTING_LINK_RE = "/stat\?c=problem_statement&pm=(.*)"

# by default, use this login and password
# taken from www.bugmenot.com
TOPCODER_DEFAULT_USER = 'a4339410'
TOPCODER_DEFAULT_PASS = 'a4339410'

## helper functions ##
def unescape(s):
    "unescape HTML code refs; c.f. http://wiki.python.org/moin/EscapingHtml"
    return re.sub('&(%s);' % '|'.join(name2codepoint),
              lambda m: unichr(name2codepoint[m.group(1)]), s)

def open_page(opener, url):
    """Opens a page with the given opener, returning the page's HTML.
    The returned HTML is a unicode string, with all HTML code refs unescaped."""
    resp = opener.open(url)
    encoding = resp.headers['content-type'].split('charset=')[-1]
    pagedata = resp.read()
    return unescape(pagedata.decode(encoding))

def remove_empty_tags(soup, tag_name, recursive=False):
    """Given a tag name, removes all tags with that name from the soup if they
    have no text.
    Returns the new soup without modifying the given one."""
    new_soup = BeautifulSoup(str(soup))
    for tag in new_soup.findAll(tag_name, recursive=recursive):
        if not tag.text:
            tag.extract()
    return new_soup

def extract_html(tag):
    """Returns the HTML of a tag, in unicode, without any HTML entities or
    empty tags."""
    s = unescape(remove_all_empty_tags(tag).renderContents())
    if type(s) != unicode:
        return s.decode('utf-8')
    return s

## topcoder-specific functions ##
def connect_to_topcoder(username = TOPCODER_DEFAULT_USER, password = TOPCODER_DEFAULT_PASS):
    """Connect to TopCoder, using the given username and password.
    Returns a connection object that can later be used for other pages that require
    you to be logged in to TopCoder."""
    requestdata = {'username': username,
                    'password': password}

    # open connection
    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    login_data = urllib.urlencode(requestdata)
    
    opener.open(TOPCODER_LOGIN_URL, login_data)
    return opener

def get_topcoder_problem_page(opener, n):
    """Attempts to get the HTML for the nth TopCoder problem, using the given opener.
    On success, returns the page's HTML."""
    return open_page(opener, TOPCODER_PROBLEM_URL_FORMAT % n)

def eval_variable(data):
    """Given some data in code format (as a string), returns the data in equivalent Python format."""
    if data.lower() == "true":
        return True
    if data.lower() == "false":
        return False
    if data[0] == "{":
        # preserve order (don't use sets)
        # replace('`', '') is a fix for problem #8394
        return list(eval(data.replace("{", "[").replace("}", "]").replace('`', '')))
    if data[0] in ['"', "'"]:
        return str(eval(data)).strip("'\"")
    if data[0] in string.ascii_letters:
        # unquoted string
        return str(data.strip())
    if data[0].isdigit():
        return int(eval(data))
    return eval(data)

def remove_empty_tables(soup):
    """Removes all tables from the soup that have no text.
    Returns the new soup without modifying the given one."""
    return remove_empty_tags(soup, 'table')

def remove_all_empty_tags(soup):
    """Removes all tags from the soup that have no text.
    Returns the new soup without modifying the given one.
    
    NOTE: Only works for soup tags. Does not work for the root of the soup."""
    return remove_empty_tags(soup, soup.name, recursive=True)

def get_json(directory, problem_no):
    """Given a problem number and a parent directory, returns the JSON (as a
    Python object) for that problem.
    Returns None if the problem was not found."""

    # find the folder
    folders = [x for x in os.listdir(directory) if os.path.isdir(directory + os.sep + x) and x.split('_')[0] == str(problem_no)]
    if len(folders) == 0:
        return None

    # get problem name
    folder = folders[0]
    problem_name = folder.split('_')[1]
    
    # find the JSON
    json_file = open(directory + os.sep + folder + os.sep + problem_name + '.json', 'rU')
    data = json.load(json_file)
    json_file.close()
    return data


