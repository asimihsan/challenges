emailAddress = (function() {
    'use strict';

    var pattern = /^(?:[A-Za-z0-9!#$%&\'*+\-\/=?^_`{|}~]+\.?)+[^.]@(?:[a-z0-9\-]+(?!\.$)\.?)+$/;

    var isValid = function(string) {
        return pattern.test(string);
    };

    return {
        'isValid': isValid,
    };
}());
