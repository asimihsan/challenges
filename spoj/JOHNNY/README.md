## Problem code: JOHNNY

Johnny visited his favourite supermarket to purchase as many sweets as he could afford. Since daddy had left his credit card at home untended, this was not really a problem. Once he had (barely) managed to push the trolley laden with chocolate bars past the cash desk, he began to wonder how to carry all the shopping home without breaking his back.

You must know that Johnny is a perfectly normal child, and has exactly 2 hands. Help him distribute his load between both hands so as to minimise the difference in load between both hands.

### Input

The first line of input contains a single integer n<= 10000 denoting the number of sweet packets Johnny has bought. In each of the next n lines a single positive integer is given, describing the weight of the respective packet (the weight is an integer value never exceeding 1014).

### Output

In separate lines, output the numbers of the packets which Johnny should carry in his left hand. Assume packets are numbered in the input order from 1 to n.

### Scoring

Your program shall receive log10 (s/(|d|+1)) points, where s is the total weight of all parcels, while d denotes the difference in weight between the load carried in Johnny's left and right hand.

### Example

For the sample input:

```
3
5
8
4
```

a program outputting:

```
2
3
```

will score `log10 ((5+8+4)/(|8+4-5|+1)) = 0.327` points.