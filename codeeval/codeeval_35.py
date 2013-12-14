import re
import sys


RE_EMAIL = re.compile(r"""
    ^
    (?P<local_part>
        (?:
            (?:
                [A-Za-z\-_0-9+][\.]?
            )+
            (?<=[A-Za-z0-9])
            |
            \"
            (?:
                [A-Za-z\-_0-9+\"\(\),:;<>@\[\\\]][\.]?
            )+
            (?<=[A-Za-z0-9])
            \"
        )
    )
    @
    (?=[a-z0-9])
    (?P<domain_part>
        [a-z\-0-9]+[\.]
        (?:[a-z\-0-9][\.]?)*
        (?:[a-z0-9]|[*])
    )
    $
    """, re.X)


def validate_email(line):
    m = RE_EMAIL.search(line)
    if not m:
        return 'false'
    if len(m.groupdict()['local_part']) > 64:
        return 'false'
    if len(m.groupdict()['domain_part']) > 255:
        return 'false'
    return 'true'


def tests():
    assert(validate_email('foo@bar.com') == 'true')
    assert(validate_email('this is not an email id') == 'false')
    assert(validate_email('admin#codeeval.com') == 'false')
    assert(validate_email('foo.@bar.com') == 'false')
    assert(validate_email('foo.bar@bar.com') == 'true')
    assert(validate_email('foo.bar.@bar.com') == 'false')
    assert(validate_email('foo.bar@bar.com.') == 'false')
    assert(validate_email('foo..bar@bar.com.') == 'false')
    assert(validate_email('foo.bar@@bar.com') == 'false')
    assert(validate_email('.foo.bar@bar.com') == 'false')
    assert(validate_email('foo.bar+baz@bar.com') == 'true')
    assert(validate_email('foo.bar++baz@bar.com') == 'true')

    # local-part max length is 64 bytes
    assert(validate_email("01234567890123456789012345678901234567890123456789"
                          "012345678901234@bar.com") == 'false')

    # domain-part max length is 255 bytes
    assert(validate_email("foo@"
                          "0123456789012345678901234567890123456789"
                          "0123456789012345678901234567890123456789"
                          "0123456789012345678901234567890123456789"
                          "0123456789012345678901234567890123456789"
                          "0123456789012345678901234567890123456789"
                          "0123456789012345678901234567890123456789"
                          "0123456789bar.com") == 'false')

    assert(validate_email('foo@bar') == 'false')
    assert(validate_email('foo@bar-baz.com') == 'true')
    assert(validate_email('foo@bar+baz.com') == 'false')
    assert(validate_email('foo@bar_baz.com') == 'false')
    assert(validate_email('foo@bar.com-') == 'false')
    assert(validate_email('foo@-bar.com') == 'false')
    assert(validate_email('foo@0bar.com') == 'true')

    assert(validate_email('1@d.net') == 'true')
    assert(validate_email('this is"not\allowed@example.com') == 'false')
    assert(validate_email('just"not"right@example.com') == 'false')
    assert(validate_email('b@d.net') == 'true')
    assert(validate_email('<invalid>@foo.com') == 'false')
    assert(validate_email('asterisk_domain@foo.*') == 'true')
    assert(validate_email('"very.unusual.@.unusual.com"@example.com') ==
           'true')
    assert(validate_email('this\ still\"not\\allowed@example.com') == 'false')
    assert(validate_email('a"b(c)d,e:f;g<h>i[j\k]l@example.com') == 'false')


if __name__ == '__main__':
    with open(sys.argv[1]) as f_in:
        lines = (line.strip() for line in f_in if len(line.strip()) > 0)
        for line in f_in:
            print validate_email(line)
