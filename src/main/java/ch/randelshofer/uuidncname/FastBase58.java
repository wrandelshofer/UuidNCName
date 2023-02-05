package ch.randelshofer.uuidncname;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FastBase58 {
    public static final byte[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte ENCODED_ZERO = ALPHABET[0];
    private static final int[] INDEXES = new int[128];

    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    private static long M58 = ConstantDivision.computeM_u32(58);

    /**
     * Encodes a 120 bit number into 21 bytes.
     *
     * @param out    the output array
     * @param offset the offset in the output array
     */
    public static void encode(long high, long low, byte[] out, int offset) {
        int firstDigit = 0;
        int index = offset + 20;
        int mask30 = (1 << 30) - 1;
        int mask15 = (1 << 15) - 1;
        int mask05 = (1 << 5) - 1;
        int mask10 = (1 << 10) - 1;
        int mask12 = (1 << 12) - 1;
        int mask20 = (1 << 20) - 1;
        int mask25 = (1 << 25) - 1;
        int mask24 = (1 << 24) - 1;
        //int[] number30 = {(int) (high >> 30), (int) high & mask30, (int) (low >> 30), (int) low & mask30};
        //int[] number15 = {
        //        (int) (high >> 45),
        //        (int) (high >> 30)&mask15,
        //        (int) (high >> 15)&mask15,
        //        (int) high & mask15,
        //        (int) (low >> 45),
        //        (int) (low >> 30)&mask15,
        //        (int) (low >> 15)&mask15,
        //        (int) low & mask15};
        //int[] number20 = {
        //        (int) (high >>> 40),
        //        (int) (high >>> 20)&mask20,
        //        (int) high & mask20,
        //        (int) (low >>> 40),
        //        (int) (low >>> 20)&mask20,
        //        (int) low & mask20};
        int[] number24 = {
                (int) (high >>> 36),
                (int) (high >>> 12) & mask24,
                (int) ((low >>> 48) | (high & mask12) << 12),
                (int) (low >>> 24) & mask24,
                (int) low & mask24};
        for (int i = 0; i < 21 && firstDigit < number24.length; i++) {
            out[index--] = ALPHABET[divmod58(number24, firstDigit, 24, 58)];
            if (number24[firstDigit] == 0) firstDigit++;
        }
        for (int i = offset; i <= index; i++) out[i] = '1';
    }

    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number     the number to divide
     * @param firstDigit the index within the array of the first non-zero digit
     *                   (this is used for optimization by skipping the leading zeros)
     * @param baseShift  the base in which the number's digits are represented (up to 60)
     * @param divisor    the number to divide by (up to 256)
     * @return the remainder of the division operation
     */
    private static byte divmodL(int[] number, int firstDigit, int baseShift, int divisor) {
        // this is just long division which accounts for the base of the input digits
        long remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            long digit = number[i];
            long temp = (remainder << baseShift) + digit;
            number[i] = (int) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

    private static byte divmod(int[] number, int firstDigit, int baseShift, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = number[i];
            int temp = (remainder << baseShift) + digit;
            number[i] = (int) (temp / divisor);
            remainder = temp % divisor;
        }
        return (byte) remainder;
    }

    private static byte divmod58(int[] number, int firstDigit, int baseShift, int divisor) {
        // this is just long division which accounts for the base of the input digits
        int remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = number[i];
            int temp = (remainder << baseShift) + digit;
            number[i] = ConstantDivision.fastdiv_u32(temp, M58);
            remainder = ConstantDivision.fastmod_u32(temp, M58, divisor);
        }
        return (byte) remainder;
    }
}
