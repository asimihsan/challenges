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

test('valid test 7', function() {
    ok(emailAddress.isValid('"much.more unusual"@example.com'));
});

test('valid test 8', function() {
    ok(emailAddress.isValid('"very.unusual.@.unusual.com"@example.com'));
});

test('valid test 9', function() {
    ok(emailAddress.isValid('"john".adams.doe@example.com'));
});

test('valid test 10', function() {
    ok(emailAddress.isValid('john."adams".doe@example.com'));
});

test('valid test 11', function() {
    ok(emailAddress.isValid('john.adams."doe"@example.com'));
});

test('valid test 12', function() {
    ok(emailAddress.isValid('"john"."adams"."doe"@example.com'));
});

test('valid test 13', function() {
    ok(emailAddress.isValid('"john\\".\\"adams\\".\\"doe"@example.com'));
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

test('invalid test 6', function() {
    ok(!emailAddress.isValid('.user@host.com'));
});

test('invalid test 7', function() {
    ok(!emailAddress.isValid('@host.com'));
});

test('invalid test 8', function() {
    ok(!emailAddress.isValid('user@'));
});

test('invalid test 9', function() {
    ok(!emailAddress.isValid('a"b(c)d,e:f;g<h>i[j\\k]l@example.com'));
});

test('invalid test 10', function() {
    ok(!emailAddress.isValid('just"not"right@example.com'));
});

test('invalid test 11', function() {
    ok(!emailAddress.isValid('this is"not\\allowed@example.com'));
});

test('invalid test 12', function() {
    ok(!emailAddress.isValid('this\\ still\\"not\\\\allowed@example.com'));
});

test('invalid test 13', function() {
    ok(!emailAddress.isValid('john.adams"."doe@example.com'));
});

test('invalid test 14', function() {
    ok(!emailAddress.isValid('"john.adams."doe@example.com'));
});