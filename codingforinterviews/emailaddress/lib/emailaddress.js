/*!
 * emailaddress
 * https://github.com/asimihsan/challenges/codingforinterviews/emailaddress
 *
 * Copyright 2013 Asim Ihsan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * References:
 *
 * -    A good start is the Wikipedia article [1]. But you still need to
 *      read the RFCs.
 * 
 * -    Best general reference is RFC 822 [2].
 * 
 * -    Comments in the local part (e.g. "(comment)user@host.com" are
 *      best explained by RFC 822 sections 3.1.4 and 3.2. In particular
 *      note "comment" is a lexical token, it may contain any character
 *      except ["(", ")", "\", "\n"], and it is ignored when comparing
 *      two email addresses. This is not clear in the Wikipedia article,
 *      but RFC 822's ABNF is very clear.
 * 
 * -    For the local part also see RFC 5322 section 3.4.1 [3]
 *
 * [1] http://en.wikipedia.org/wiki/Email_address
 * 
 * [2] http://tools.ietf.org/html/rfc822
 *
 * [3] http://tools.ietf.org/html/rfc5322#section-3.4.1
 */

emailAddress = (function() {
    'use strict';
    var RE_PATTERN = /^(.+)@(.+)$/,
        RE_IN_DQUOTE_CHAR = /^(?:[A-Za-z0-9!#$%&\'*+\-\/=?_`{|}~^(),:;<>@\[\] \.]|\\["\\])$/,
        RE_OUT_DQUOTE_CHAR = /^(?:[A-Za-z0-9!#$%&\'*+\-\/=?_`{|}~^])$/,
        RE_COMMENT_CHAR = /[^()\\\n]/,
        RE_DQUOTE = /"/,
        RE_DOT = /\./,
        RE_LBRACKET = /\(/,
        RE_RBRACKET = /\)/,
        STATE_START = 0,
        STATE_IN_DQUOTE = 1,
        STATE_OUT_DQUOTE = 2,
        STATE_END_DQUOTE = 3,
        STATE_IN_COMMENT_AT_START = 4,
        STATE_IN_COMMENT_AT_END = 5,
        STATE_END_COMMENT_AT_END = 6,
        RE_DOMAIN = /^(?:[a-z0-9\-]+)(?:\.[a-z0-9\-]+)*$/;

    var isValidLocalPart = function(string) {
        var state = STATE_START;
        //console.log('isValidLocalPart entry. string: ' + string);
        for (var i = 0; i <= string.length; i++) {
            var c;
            if (i < string.length) {
                c = string.charAt(i);
            } else {
                c = null;
            }
            //console.log('state: ' + state + ', c: ' + c);
            switch (state) {

            case STATE_START:
                if (RE_DQUOTE.test(c)) {
                    state = STATE_IN_DQUOTE;
                } else if (RE_OUT_DQUOTE_CHAR.test(c)) {
                    state = STATE_OUT_DQUOTE;
                } else if (RE_LBRACKET.test(c)) {
                    state = STATE_IN_COMMENT_AT_START;
                } else {
                    return false;
                }
                break;

            case STATE_IN_DQUOTE:
                /**
                 * We've assumed advancing by a single char is good
                 * enough, but within double quotes backslashes and
                 * double quotes must be escaped. RegExp lastIndex and
                 * stick is Firefox-only, so instead just here be a
                 * little cheeky and do it manually.
                 */
                var cs = string.slice(i, i+2);
                if (RE_IN_DQUOTE_CHAR.test(cs)) {
                    state = STATE_IN_DQUOTE;
                    i += 1;
                } else if (RE_IN_DQUOTE_CHAR.test(c)) {
                    state = STATE_IN_DQUOTE;
                } else if (RE_DQUOTE.test(c)) {
                    state = STATE_END_DQUOTE;
                } else {
                    return false;
                }
                break;

            case STATE_END_DQUOTE:
                if (RE_DOT.test(c)) {
                    state = STATE_START;
                } else if (RE_LBRACKET.test(c)) {
                    state = STATE_IN_COMMENT_AT_END;
                } else if (c === null) {
                    return true;
                } else {
                    return false;
                }
                break;

            case STATE_OUT_DQUOTE:
                if (RE_OUT_DQUOTE_CHAR.test(c)) {
                    state = STATE_OUT_DQUOTE;
                } else if (RE_DOT.test(c)) {
                    state = STATE_START;
                } else if (RE_LBRACKET.test(c)) {
                    state = STATE_IN_COMMENT_AT_END;
                } else if (c === null) {
                    return true;
                } else {
                    return false;
                }
                break;

            case STATE_IN_COMMENT_AT_START:
                if (RE_RBRACKET.test(c)) {
                    state = STATE_START;
                } else if (RE_COMMENT_CHAR.test(c)) {
                    state = STATE_IN_COMMENT_AT_START;
                } else {
                    return false;
                }
                break;

            case STATE_IN_COMMENT_AT_END:
                if (RE_RBRACKET.test(c)) {
                    state = STATE_END_COMMENT_AT_END;
                } else if (RE_COMMENT_CHAR.test(c)) {
                    state = STATE_IN_COMMENT_AT_END;
                } else {
                    return false;
                }
                break;

            case STATE_END_COMMENT_AT_END:
                if (RE_LBRACKET.test(c)) {
                    state = STATE_IN_COMMENT_AT_END;
                } else if (c === null) {
                    return true;
                } else {
                    return false;
                }
                break;
            }
        }
        return false;
    };

    var isValid = function(string) {
        var match = RE_PATTERN.exec(string);
        if (match === null) {
            return false;
        }
        if (!isValidLocalPart(match[1])) {
            return false;
        }
        var domain = match[2];
        if (!RE_DOMAIN.test(domain)) {
            return false;
        }
        return true;
    };

    return {
        'isValid': isValid,
    };
}());
