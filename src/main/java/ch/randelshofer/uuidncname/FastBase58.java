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
    private static long M58p2 = ConstantDivision.computeM_u32(58 * 58);
    private static long M58p4 = ConstantDivision.computeM_u32(58 * 58 * 58 * 58);
    private static long M58p3 = ConstantDivision.computeM_u32(58 * 58 * 58);

    /**
     * Encodes a 120 bit number into 21 characters.
     *
     * @param high   the 60 most significant bits of the number
     * @param low    the 60 least significant bits of the number
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
            out[index--] = ALPHABET[divmod(number24, firstDigit, 24, 58, M58)];
            if (number24[firstDigit] == 0) firstDigit++;
        }
        for (int i = offset; i <= index; i++) out[i] = '1';
    }

    public static void encode58p2(long high, long low, byte[] out, int offset) {
        int firstDigit = 0;
        int index = offset + 20;
        int mask30 = (1 << 30) - 1;
        int[] number30 = {(int) (high >> 30), (int) high & mask30, (int) (low >> 30), (int) low & mask30};
        for (int i = 0; i < 21 && firstDigit < number30.length; i++) {
            int number58x58 = divmod(number30, firstDigit, 30, 58 * 58, M58p2);
            out[index--] = ALPHABET[ConstantDivision.fastmod_u32(number58x58, M58, 58)];
            out[index--] = ALPHABET[ConstantDivision.fastdiv_u32(number58x58, M58)];
            if (number30[firstDigit] == 0) firstDigit++;
        }
        for (int i = offset; i <= index; i++) out[i] = '1';
    }

    public static void encode58p4(long high, long low, byte[] out, int offset) {
        int firstDigit = 0;
        int index = offset + 20;
        int mask30 = (1 << 30) - 1;
        int[] number30 = {(int) (high >> 30), (int) high & mask30, (int) (low >> 30), (int) low & mask30};
        System.out.println(Arrays.toString(number30));
        int remainder = 0;
        while (index > 1 && firstDigit < 4) {
            remainder = divmod(number30, firstDigit, 30, 58 * 58 * 58 * 58, M58p4);
            int remainderHigh = ConstantDivision.fastdiv_u32(remainder, M58p2);
            int remainderLow = ConstantDivision.fastmod_u32(remainder, M58p2, 58 * 58);
            System.out.println(Arrays.toString(number30) + " -> " + remainder + " high=" + remainderHigh + " low=" + remainderLow);
            out[index--] = ALPHABET[ConstantDivision.fastmod_u32(remainderLow, M58, 58)];
            out[index--] = ALPHABET[ConstantDivision.fastdiv_u32(remainderLow, M58)];
            out[index--] = ALPHABET[ConstantDivision.fastmod_u32(remainderHigh, M58, 58)];
            out[index--] = ALPHABET[ConstantDivision.fastdiv_u32(remainderHigh, M58)];
            if (number30[firstDigit] == 0) firstDigit++;
        }
        out[index--] = ALPHABET[number30[number30.length - 1]];
        for (int i = offset; i <= index; i++) out[i] = '1';
    }

    public static void encode58p3(long high, long low, byte[] out, int offset) {
        int firstDigit = 0;
        int index = offset + 20;
        int mask30 = (1 << 30) - 1;
        int[] number30 = {(int) (high >> 30), (int) high & mask30, (int) (low >> 30), (int) low & mask30};
        while (index > offset) {
            int remainder = divmod(number30, firstDigit >>> 1, 30, 58 * 58 * 58, M58p3);
            int remainderHigh = ConstantDivision.fastdiv_u32(remainder, M58p2);
            int remainderLow = ConstantDivision.fastmod_u32(remainder, M58p2, 58 * 58);
            out[index--] = ALPHABET[ConstantDivision.fastmod_u32(remainder, M58, 58)];
            out[index--] = ALPHABET[ConstantDivision.fastdiv_u32(remainderLow, M58)];
            out[index--] = ALPHABET[remainderHigh];
            firstDigit++;
        }
    }


    private static int divmod(int[] number, int firstDigit, int baseShift, int divisor, long M) {
        // this is just long division which accounts for the base of the input digits
        long remainder = 0;
        for (int i = firstDigit; i < number.length; i++) {
            int digit = number[i];
            long temp = (remainder << baseShift) + digit;
            number[i] = (int) ConstantDivision.fastdiv_u32L(temp, M);
            remainder = ConstantDivision.fastmod_u32L(temp, M, divisor);
        }
        return (int) remainder;
    }

}
