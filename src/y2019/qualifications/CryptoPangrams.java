package y2019.qualifications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;

public class CryptoPangrams {

    // Limit should not be too big
    private static List<Integer> getPrimesSmallerThan(int limit) {
        boolean[] ar = new boolean[limit+1];
        ar[0] = false;
        ar[1] = false;
        for (int i = 2; i < ar.length; i++) {
            ar[i] = true;
        }
        for (int i = 2; i < ar.length; i++) {
            if (ar[i]) {
                int j = i + i;
                while (j < ar.length) {
                    if (ar[j]) {
                        ar[j] = false;
                    }
                    j += i;
                }
            }
        }
        List<Integer> results = new ArrayList<>();
        for (int k = 3; k < ar.length; k++) {
            if (ar[k]) {
                results.add(k);
            }
        }
        return results;
    }

    private static String cryptoPangramsForSmallNumber(int limit, long[] cipher) {
        long[] root = new long[cipher.length + 1];
        List<Integer> possiblePrimes = getPrimesSmallerThan(limit);
        Set<Long> targetPrimes = new HashSet<>();
        int pos = -1;
        for (int i = 0; i < cipher.length-1; i++) {
            if (cipher[i] == cipher[i+1]) {
                continue;
            }
            pos = i + 1;
            long cip1 = cipher[i];
            long cip2 = cipher[i+1];
            long f1 = 0, f2 = 0, f3 = 0, f4 = 0;
            for (long prime : possiblePrimes) {
                if (cip1 % prime == 0) {
                    f1 = prime;
                    f2 = cip1 / prime;
                    break;
                }
            }
            for (long prime : possiblePrimes) {
                if (cip2 % prime == 0) {
                    f3 = prime;
                    f4 = cip2 / prime;
                    break;
                }
            }
            if (f1 == f3 || f1 == f4) {
                root[pos] = f1;
            } else {
                root[pos] = f2;
            }
        }
        targetPrimes.add(root[pos]);
        for (int i = pos - 1; i >= 0; i--) {
            root[i] = cipher[i] / root[i+1];
            targetPrimes.add(root[i]);
        }
        for (int i = pos + 1; i < root.length; i++) {
            root[i] = cipher[i-1] / root[i-1];
            targetPrimes.add(root[i]);
        }
        List<Long> actualPrimes = new ArrayList<>(targetPrimes);
        Collections.sort(actualPrimes);
        Map<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            map.put(actualPrimes.get(i), 'A' + i);
        }
        StringBuilder sb = new StringBuilder();
        for (long i : root) {
            sb.append((char)map.get(i).intValue());
        }
        return sb.toString();
    }

    private static String cryptoPangrams(BigInteger[] cipher) {
        BigInteger[] root = new BigInteger[cipher.length + 1];
        Set<BigInteger> primes = new HashSet<>();
        int pos = -1;
        for (int i = 0; i < cipher.length - 1; i++) {
            if (cipher[i].compareTo(cipher[i+1]) != 0) {
                BigInteger ba = cipher[i];
                BigInteger bb = cipher[i+1];
                while (bb.compareTo(BigInteger.ZERO) != 0) {
                    BigInteger temp = ba.mod(bb);
                    ba = bb;
                    bb = temp;
                }
                root[i + 1] = ba;
                pos = i+1;
            }
        }
        primes.add(root[pos]);
        for (int i = pos - 1; i >= 0; i--) {
            root[i] = cipher[i].divide(root[i+1]);
            primes.add(root[i]);
        }
        for (int i = pos + 1; i <= cipher.length; i++) {
            root[i] = cipher[i-1].divide(root[i-1]);
            primes.add(root[i]);
        }
        List<BigInteger> orderedPrimes = new ArrayList<>(primes);
        Collections.sort(orderedPrimes);
        Map<BigInteger, Integer> map = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            map.put(orderedPrimes.get(i), 'A' + i);
        }
        StringBuilder sb = new StringBuilder();
        for (BigInteger bi : root) {
            sb.append((char)map.get(bi).intValue());
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            BigInteger limit = new BigInteger(scanner.next()); // Upper limit of a prime is also could be very big
            int length = scanner.nextInt();
            BigInteger[] cipher = new BigInteger[length]; // Data of hidden test cases seems to always be very big
            for (int j = 0; j < length; j++) {
                cipher[j] = new BigInteger(scanner.next());
            }
            String result = cryptoPangrams(cipher);
            System.out.printf("Case #%d: %s\n", i + 1, result);
        }
    }

}
