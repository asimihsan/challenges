# FIBO

http://rosalind.info/problems/fibo/

Given n return the nth Fibonacci number.

## Continuous integration

    watchmedo shell-command -p "*.gradle;*.java" -R -w -c "clear; date; gradle test --info"