import cdecimal
import sys


def main():
    D_to_x = {}
    x_squared_minus_one_is_square = {}
    for D in xrange(2, 1000 + 1):
        D_decimal = cdecimal.Decimal(D)
        if D_decimal.sqrt().is_integer():
            continue
        x_squared_minus_one = D_decimal
        while True:
            #print x_squared_minus_one
            if x_squared_minus_one not in x_squared_minus_one_is_square:
                x_squared_minus_one_is_square[x_squared_minus_one] = (x_squared_minus_one + 1).sqrt().is_integer()
            if x_squared_minus_one_is_square[x_squared_minus_one] and \
                ((x_squared_minus_one / D_decimal).sqrt().is_integer()):
                break
            x_squared_minus_one += D_decimal

        x = (x_squared_minus_one + 1).sqrt()
        print "D: %s, x: %s" % (D, x)
        D_to_x[D] = x

    import ipdb; ipdb.set_trace()
    pass

if __name__ == "__main__":
    sys.exit(main())
