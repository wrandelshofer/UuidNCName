package ch.randelshofer.uuidncname;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static ch.randelshofer.uuidncname.ConstantDivision.computeM_u32;
import static ch.randelshofer.uuidncname.ConstantDivision.fastdiv_u32;
import static ch.randelshofer.uuidncname.ConstantDivision.fastdiv_u32L;
import static ch.randelshofer.uuidncname.ConstantDivision.fastmod_u32;
import static ch.randelshofer.uuidncname.ConstantDivision.fastmod_u32L;

public class FastBase58 {
    public static final byte[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".getBytes(StandardCharsets.ISO_8859_1);
    private static final int[] INVERSE_ALPHABET = new int[128];
    private final static VarHandle readIntLE =
            MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.LITTLE_ENDIAN);

    static {
        Arrays.fill(INVERSE_ALPHABET, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INVERSE_ALPHABET[ALPHABET[i]] = i;
        }
    }

    private static long M58 = computeM_u32(58);
    private static long M58p2 = computeM_u32(58 * 58);
    private static long M58p3 = computeM_u32(58 * 58 * 58);

    /**
     * Encodes an unsigned 120 bit number into 21 characters in base-58
     * without preserving the lexical order, because we pad shorter numbers
     * with '_' characters to the right.
     * <p>
     * Converting to base-58 is in O(N^2). To improve performance,
     * we do the following:
     * <ul>
     *     <li>We store the intermediate number array in the first
     *     16 bytes of the output array.
     *     We therefore do not need to instantiate an array.</li>
     *
     *     <li>We produce 3 digits at once per iteration.
     *     We therefore only need 7 iterations instead of 21.</li>
     *
     *     <li>We implement constant division and constant remainder in Java.
     *     This is because the HotSpot JIT compiles {@code /} and {@code %} into
     *     {@code IDIV} instructions, which are slow.</li>
     * </ul>
     *
     * @param high   the 60 most significant bits of the number
     * @param low    the 60 least significant bits of the number
     * @param out    the output array
     * @param offset the offset in the output array
     */
    public static void encode58(long high, long low, byte[] out, int offset) {
        // Encode with lexical format.
        // Store the number in little-endian order in the first 16 bytes of the output array.
        // We distribute the 120 bits into 4 int values - storing 30 bits in each.
        int mask30 = (1 << 30) - 1;
        readIntLE.set(out, 12, (int) (high >> 30));
        readIntLE.set(out, 8, (int) high & mask30);
        readIntLE.set(out, 4, (int) (low >> 30));
        readIntLE.set(out, 0, (int) low & mask30);

        // Each division by 58^3 (=195112) shortens the number by 17.57 bits.
        // Therefore, we can process 17.5739 fewer bits in each iteration.
        // This means, that we can skip 17.5739/30=0.5857 'digits' in each iteration.
        // We approximate this with 37/64=37>>>6=0.5781.
        int firstBitTimes2 = 0;

        int index = offset + 20;
        int leadingZeros = 0;
        while (index > offset) {
            int remainder = divmod(out, firstBitTimes2 >>> 6, 30, 58 * 58 * 58, M58p3);
            int remainderHigh = fastdiv_u32(remainder, M58p2);
            int remainderLow = fastmod_u32(remainder, M58p2, 58 * 58);
            int d0 = fastmod_u32(remainder, M58, 58);
            int d1 = fastdiv_u32(remainderLow, M58);
            int d2 = remainderHigh;
            if (d0 == 0) leadingZeros++;
            else leadingZeros = 0;
            if (d1 == 0) leadingZeros++;
            else leadingZeros = 0;
            if (d2 == 0) leadingZeros++;
            else leadingZeros = 0;
            out[index--] = ALPHABET[d0];
            out[index--] = ALPHABET[d1];
            out[index--] = ALPHABET[d2];
            firstBitTimes2 += 37;
        }
        // The output of the lexical format is padded to the left with '1'
        // characters. However, we want to have padding the right with '_'.
        int leadingZeroBits = Long.numberOfLeadingZeros(high) - 4;
        if (leadingZeroBits == 60) leadingZeroBits += Long.numberOfLeadingZeros(low) - 4;
        int leadingZeroBytes = leadingZeroBits / 8;

        // Preserve exactly as many leading encoded zeros in output as there were leading zeros in input.
        int padding = Math.max(0, leadingZeros - leadingZeroBytes);

        // A digit in base-58 encodes 5.857980995127572 bits.
        // We need to output at least 15 digits, that is we have to pad by at most 6 characters.
        //int padding = Math.min(6, numberOfLeadingZeroBytes);//Math.min(6,(int)(numberOfLeadingZeros/5.857980995127572));
        if (padding > 0) {
            System.arraycopy(out, offset + padding, out, offset, 21 - padding);
            Arrays.fill(out, offset + 21 - padding, offset + 21, (byte) '_');

        }
    }

    /**
     * Encodes an unsigned 120 bit number into 21 characters in base-58
     * while preserving the lexical order by padding shorter numbers
     * with '1' characters to the left.
     * <p>
     * Converting to base-58 is in O(N^2). To improve performance,
     * we do the following:
     * <ul>
     *     <li>We store the intermediate number array in the first
     *     16 bytes of the output array.
     *     We therefore do not need to instantiate an array.</li>
     *
     *     <li>We produce 3 digits at once per iteration.
     *     We therefore only need 7 iterations instead of 21.</li>
     *
     *     <li>We implement constant division and constant remainder in Java.
     *     This is because the HotSpot JIT compiles {@code /} and {@code %} into
     *     {@code IDIV} instructions, which are slow.</li>
     * </ul>
     *
     * @param high   the 60 most significant bits of the number
     * @param low    the 60 least significant bits of the number
     * @param out    the output array
     * @param offset the offset in the output array
     */
    public static void encode58Lex(long high, long low, byte[] out, int offset) {
        // Store the number in little-endian order in the first 16 bytes of the output array.
        // We distribute the 120 bits into 4 int values - storing 30 bits in each.
        int mask30 = (1 << 30) - 1;
        readIntLE.set(out, 12, (int) (high >> 30));
        readIntLE.set(out, 8, (int) high & mask30);
        readIntLE.set(out, 4, (int) (low >> 30));
        readIntLE.set(out, 0, (int) low & mask30);

        // Each division by 58^3 (=195112) shortens the number by 17.5739 bits.
        // Therefore, we can process 17.5739 fewer bits in each iteration.
        // This means, that we can skip 17.5739/30=0.5857 'digits' in each iteration.
        // We approximate this with 37/64=37>>>6=0.5781.
        int firstBitTimes2 = 0;

        int index = offset + 20;
        while (index > offset) {
            int remainder = divmod(out, firstBitTimes2 >>> 6, 30, 58 * 58 * 58, M58p3);
            int remainderHigh = fastdiv_u32(remainder, M58p2);
            int remainderLow = fastmod_u32(remainder, M58p2, 58 * 58);
            out[index--] = ALPHABET[fastmod_u32(remainder, M58, 58)];
            out[index--] = ALPHABET[fastdiv_u32(remainderLow, M58)];
            out[index--] = ALPHABET[remainderHigh];
            firstBitTimes2 += 37;
        }
    }

    /**
     * Divides a number that is represented by an array of bytes.
     * <p>
     * Each block of 4 bytes contains a 'digit' in the base {@code 1<<baseShift}.
     * The 'digits' are ordered in little endian order. That is, the least significant
     * digit is stored in the block 0, and the most significant digit is stored
     * in block 3.
     *  A 'digit' is treated as an unsigned integer of 32 bits in little-endian order
     *  (uint32le).
     * <p>
     * The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number     the number to divide (in blocks of 4 bytes)
     * @param firstDigit the index of the first non-zero digit in number
     *                   (this is used for optimization by skipping the leading zeros)
     * @param baseShift  the base in which the number's digits are represented
     * @param divisor    the number to divide by
     * @param M          the constant M computed with {@link ConstantDivision#computeM_u32(int)}
     *                   from the divisor
     * @return the remainder of the division operation
     */
    private static int divmod(byte[] number, int firstDigit, int baseShift, int divisor, long M) {
        // this is just long division which accounts for the base of the input digits
        long remainder = 0;
        for (int index = (3 - firstDigit) << 2; index >= 0; index -= 4) {
            int digit = (int) readIntLE.get(number, index);
            long temp = (remainder << baseShift) + digit;
            readIntLE.set(number, index, (int) fastdiv_u32L(temp, M));
            remainder = fastmod_u32L(temp, M, divisor);
        }
        return (int) remainder;
    }

    /**
     * Decodes the given base58-lex string into 4 chunks of 30 bits.
     *
     * @param input the base58-encoded string to decode
     * @param from  the index (inclusive) of the first character in input to be decoded
     * @param to    the index (exclusive) of the last character in input to be decoded
     * @return the decoded data bytes, 4 chunks with 30 bits each
     */
    public static int[] decode58Lex(String input, int from, int to) {
        int inputLength = to - from;
        if (inputLength == 0) {
            throw new IllegalArgumentException("Input is empty");
        }
        // Convert the base58-encoded ASCII chars to a base 58^3 byte sequence.
        int[] input58 = new int[21 / 3];
        int index = 0;
        for (int i = from; i < to; i += 3) {
            char c0 = input.charAt(i);
            char c1 = input.charAt(i + 1);
            char c2 = input.charAt(i + 2);
            int digit0 = lookupDigit(c0);
            int digit1 = lookupDigit(c1);
            int digit2 = lookupDigit(c2);
            if ((digit0 | digit1 | digit2) < 0) {
                throw new IllegalArgumentException("InvalidCharacter in base 58");
            }
            input58[index++] = digit0 * 58 * 58 + digit1 * 58 + digit2;
        }
        // Convert base-58^3 digits to base-2^30 digits.
        int[] decoded = new int[4];
        int outputStart = decoded.length;
        for (int inputStart = 0; inputStart < input58.length && outputStart > 0; ) {
            decoded[--outputStart] = divmod(input58, input58.length, inputStart, 58 * 58 * 58, 30, (1 << 30) - 1);
            if (input58[inputStart] == 0) {
                ++inputStart; // optimization - skip leading zeros
            }
        }
        if (input58[input58.length - 1] != 0) {
            throw new IllegalArgumentException("Input has more than 120 data bits.");
        }
        return decoded;
    }

    /**
     * Looks the digit of the specified character up. Returns a value &lt; 0 if the character is not a valid digit.
     *
     * @param ch a character
     * @return the digit or a value &lt; 0 if the character is not a digit.
     */
    private static int lookupDigit(char ch) {
        // The branchy code is faster than the branchless code, because we
        // will almost always have a character that is in the table.
        // Branchless code:  return INDEXES[ch & 127] | (127 - ch) >> 31;
        return ch < 128 ? INVERSE_ALPHABET[ch] : -1;
    }

    /**
     * Decodes the given base58 string into 4 chunks of 30 bits.
     *
     * @param input the base58-encoded string to decode
     * @param from  the index (inclusive) of the first character in input to be decoded
     * @param to    the index (exclusive) of the last character in input to be decoded
     * @return the decoded data bytes, 4 chunks with 30 bits each
     */
    public static int[] decode58(String input, int from, int to) {
        int inputLength = to - from;
        if (inputLength == 0) {
            throw new IllegalArgumentException("Input is empty");
        }
        // Convert the base58-encoded ASCII chars to a base 58 byte sequence.
        int[] input58 = new int[21];
        int index = (21 - inputLength);
        for (int i = from; i < to; i++) {
            char c0 = input.charAt(i);
            int digit0 = lookupDigit(c0);
            if ((digit0) < 0) {
                throw new IllegalArgumentException("InvalidCharacter in base 58");
            }
            input58[index++] = digit0;
        }
        // Convert from base 58 to base 58^3
        index = 0;
        for (int i = 0; i < input58.length; i += 3) {
            input58[index++] = input58[i] * 58 * 58 + input58[i + 1] * 58 + input58[i + 2];
        }

        // Convert base-58^3 digits to base-2^30 digits.
        int[] decoded = new int[4];
        int outputStart = decoded.length;
        for (int inputStart = 0; inputStart < 7 && outputStart > 0; ) {
            decoded[--outputStart] = divmod(input58, 7, inputStart, 58 * 58 * 58, 30, (1 << 30) - 1);
            if (input58[inputStart] == 0) {
                ++inputStart; // optimization - skip leading zeros
            }
        }
        if (input58[6] != 0) {
            throw new IllegalArgumentException("Input has more than 120 data bits.");
        }
        return decoded;
    }


    /**
     * Divides a number, represented as an array of bytes each containing a single digit
     * in the specified base, by the given divisor. The given number is modified in-place
     * to contain the quotient, and the return value is the remainder.
     *
     * @param number       the number to divide
     * @param firstDigit   the index within the array of the first non-zero digit
     *                     (this is used for optimization by skipping the leading zeros)
     * @param base         the base in which the number's digits are represented (up to 256)
     * @param divisorShift the number to divide by (up to 256)
     * @return the remainder of the division operation
     */
    private static int divmod(int[] number, int numberLength, int firstDigit, int base, int divisorShift, int divisorMask) {
        // this is just long division which accounts for the base of the input digits
        long remainder = 0;
        for (int i = firstDigit; i < numberLength; i++) {
            int digit = number[i];
            long temp = remainder * base + digit;
            number[i] = (int) (temp >>> divisorShift);
            remainder = (temp & divisorMask);
        }
        return (int) remainder;
    }
}
