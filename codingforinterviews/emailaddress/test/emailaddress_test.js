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

test('valid test 5', function() {
    ok(emailAddress.isValid('!#$%&\'*+-/=?^_`{}|~@example.org'));
});

test('valid test 6', function() {
    ok(emailAddress.isValid('other.email-with-dash@example.com'));
});

test('invalid test 1', function() {
    ok(!emailAddress.isValid('Abc.example.com'));
});

test('invalid test 2', function() {
    ok(!emailAddress.isValid('A@b@c@example.com'));
});

test('invalid test 3', function() {
    ok(!emailAddress.isValid('user.@host.com'));
});

test('invalid test 4', function() {
    ok(!emailAddress.isValid('user@host.com.'));
});

test('invalid test 5', function() {
    ok(!emailAddress.isValid('john..doe@host.com'));
});