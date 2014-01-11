emailAddress = (function() {
    'use strict';
    var RE_PATTERN = /^(.+)@(.+)$/;
    var RE_IN_DQUOTE_CHAR = /^(?:[A-Za-z0-9!#$%&\'*+\-\/=?_`{|}~^(),:;<>@\[\] \.]|\\["\\])$/;
    var RE_OUT_DQUOTE_CHAR = /^(?:[A-Za-z0-9!#$%&\'*+\-\/=?_`{|}~^])$/;
    var RE_DQUOTE = /"/;
    var RE_DOT = /\./;
    var STATE_START = 0,
        STATE_IN_DQUOTE = 1,
        STATE_OUT_DQUOTE = 2,
        STATE_END_DQUOTE = 3;
    var RE_DOMAIN = /^(?:[a-z0-9\-]+)(?:\.[a-z0-9\-]+)*$/;

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
        if (RE_DOMAIN.test(domain)) {
            return true;
        }
        return false;
    };

    return {
        'isValid': isValid,
    };
}());
