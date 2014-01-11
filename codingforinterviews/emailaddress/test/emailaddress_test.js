'use strict';

test('valid test 1', function() {
    ok(emailAddress.isValid('niceandsimple@example.com'));
});

test('valid test 2', function() {
    ok(emailAddress.isValid('very.common@example.com'));
});

test('valid test 3', function() {
    ok(emailAddress.isValid('a.little.lengthy.but.fine@dept.example.com'));
});

test('valid test 4', function() {
    ok(emailAddress.isValid('disposable.style.email.with+symbol@example.com'));
});

test('invalid test 1', function() {
    ok(!emailAddress.isValid('Abc.example.com'));
});

test('invalid test 2', function() {
    ok(!emailAddress.isValid('A@b@c@example.com'));
});
