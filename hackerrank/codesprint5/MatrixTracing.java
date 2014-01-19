import java.math.BigInteger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Sieve {
    private BitSet sieve;

    private Sieve() {}

    private Sieve(int size) {
        sieve = new BitSet((size + 1) / 2);
    }

    private boolean isComposite(int k) {
        return sieve.get((k-3)/2);
    }

    private void setComposite(int k) {
        sieve.set((k-3)/2);
    }

    public static List<Integer> getPrimes(int max) {
        Sieve sieve = new Sieve(max + 1);
        for (int i = 3; i*i <= max; i += 2) {
            if (sieve.isComposite(i))
                continue;
            for (int multiple_i = i*i; multiple_i <= max; multiple_i += 2*i)
                sieve.setComposite(multiple_i);
        }
        List<Integer> primes = new ArrayList<Integer>(max / 10);
        primes.add(2);
        for (int i = 3; i <= max; i += 2) {
            if (!sieve.isComposite(i))
                primes.add(i);
        }
        return primes;
    }
}

class MatrixTracing {
    private static List<Integer> primes;
    static {
        primes = Sieve.getPrimes(2000000);
    }

    private static final BigInteger SEVEN = new BigInteger("7");
    private static final BigInteger MOD = BigInteger.TEN.pow(9).add(SEVEN);
        

    /**
     * Return the power of the prime number p in the factorization of
     * n!
     * 
     * @param  n Factorial to factorize.
     * @param  p Prime number
     * @return   int, power of the prime number p in prime factorization
     *                of n!
     */
    public static int multiplicity(int n, int p) {
        if (p > n) return 0;
        if (p > n / 2) return 1;
        int q = n, m = 0;
        while (q >= p) {
            q /= p;
            m += q;
        }
        return m;
    }

    public static Map<Integer, Integer> getPrimeMultiplicities(int n) {
        Map<Integer, Integer> returnValue = new HashMap<>(n);
        for (Integer p : primes) {
            if (p > n)
                break;
            int mult = multiplicity(n, p);
            if (mult > 0)
                returnValue.put(p, mult);
        }
        return returnValue;
    }

    public static BigInteger powproduct(Map<Integer, Integer> ns) {
        if (ns.size() == 0)
            return BigInteger.ONE;
        BigInteger units = BigInteger.ONE;
        Map<Integer, Integer> multi = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : ns.entrySet()) {
            int base = entry.getKey();
            BigInteger baseBigInteger = new BigInteger(new Integer(base).toString());
            int exp = entry.getValue();
            if (exp == 1) {
                units = units.multiply(baseBigInteger).mod(MOD);
            } else {
                if (exp % 2 == 1)
                    units = units.multiply(baseBigInteger).mod(MOD);
                multi.put(base, exp / 2);
            }
        }
        return units.multiply(powproduct(multi).pow(2));
    }

    public static void multiplyPrimeMultiplicities(
        Map<Integer, Integer> first, Map<Integer, Integer> second) {
        for (Map.Entry<Integer, Integer> entry : second.entrySet()) {
            Integer prime = entry.getKey();
            Integer secondMult = entry.getValue();
            Integer firstMult = first.get(prime);
            if (firstMult == null)
                firstMult = 0;
            first.put(prime, firstMult + secondMult);
        }
    }

    public static void dividePrimeMultiplicities(
        Map<Integer, Integer> first, Map<Integer, Integer> second) {
        Map<Integer, Integer> secondCopy = new HashMap<>(second);
        for (Map.Entry<Integer, Integer> entry : secondCopy.entrySet()) {
            Integer prime = entry.getKey();
            Integer secondMult = entry.getValue();
            if (!first.containsKey(prime))
                continue;
            Integer firstMult = first.get(prime);
            if (firstMult.equals(secondMult)) {
                first.remove(prime);
                second.remove(prime);
            } else if (firstMult.compareTo(secondMult) > 0) {
                first.put(prime, firstMult - secondMult);
                second.remove(prime);
            } else {
                second.put(prime, secondMult - firstMult);
                first.remove(prime);
            }
        }
    }

    public static BigInteger factorial(int[] numerators, int[] denomenators) {
        Map<Integer, Integer> ns = new HashMap<>();
        for (int n : numerators) {
            Map<Integer, Integer> pms = getPrimeMultiplicities(n);
            multiplyPrimeMultiplicities(ns, pms);
        }
        Map<Integer, Integer> ds = new HashMap<>();
        for (int d : denomenators) {
            Map<Integer, Integer> pms = getPrimeMultiplicities(d);
            multiplyPrimeMultiplicities(ds, pms);
        }
        dividePrimeMultiplicities(ns, ds);
        BigInteger n = powproduct(ns);
        BigInteger d = powproduct(ds);
        return n.divide(d);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int[] numerators = {r + c - 2};
            int[] denomenators = {r - 1, c - 1};
            BigInteger result = factorial(numerators, denomenators).mod(MOD);
            System.out.println(result);
        }
    }
}