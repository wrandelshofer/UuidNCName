package ch.randelshofer.uuidncname;

/**
 * This is a Java port of fastmod.h.
 * <p>
 * References:
 * <dl>
 *     <dt>constantdivisionbenchmarks. Copyright Daniel Lemire.</dt>
 *     <dd><a href="https://github.com/lemire/constantdivisionbenchmarks/blob/master/collatzbenches/fastmod.h">fastmod.h</a></dd>
 * </dl>
 */
public class ConstantDivision {
    /**
     * Computes {@code M} for unsigned integer division.
     * <p>
     * Usage:
     * <pre>
     *  uint32_t d = ... ; // divisor, should be non-zero
     *  uint64_t M = computeM_u32(d); // do once
     *  fastmod_u32(a,M,d) is a % d for all 32-bit a.
     * </pre>
     *
     * @param d the divisor (is treated as an unsigned int)
     * @return M
     */
    public static long computeM_u32(int d) {
        // M = ceil( (1<<64) / d ), d > 0
        return Long.divideUnsigned(0xFFFFFFFFFFFFFFFFL, d) + 1;
    }

    /**
     * Computes (a % d) given precomputed M.
     */
    public static int fastmod_u32(int a, long M, int d) {
        long lowbits = M * a;
        return (int) Math.unsignedMultiplyHigh(lowbits, d);
    }

    /**
     * Computes (a / d) given precomputed M for d>1.
     */
    public static int fastdiv_u32(int a, long M) {
        return (int) Math.unsignedMultiplyHigh(M, a);
    }

    /**
     * Computes {@code M} for signed integers.
     * <p>
     * Usage:
     * <pre>
     *  int32_t d = ... ; // should be non-zero and between [-2147483647,2147483647]
     *  int32_t positive_d = d < 0 ? -d : d; // absolute value
     *  uint64_t M = computeM_s32(d); // do once
     *  fastmod_s32(a,M,positive_d) is a % d for all 32-bit a.
     * </pre>
     *
     * @param d the divisor (is treated as an signed int)
     * @return M
     **/

    public static long computeM_s32(int d) {
        // M = floor( (1<<64) / d ) + 1
        // you must have that d is different from 0 and -2147483648
        // if d = -1 and a = -2147483648, the result is undefined
        if (d < 0) {
            d = -d;
        }
        return Long.divideUnsigned(0xFFFFFFFFFFFFFFFFL, d) + 1 + ((d & (d - 1)) == 0 ? 1 : 0);
    }

    /**
     * Computes (a % d) given precomputed M,
     * you should pass the absolute value of d.
     */
    public static int fastmod_s32(int a, long M, int positive_d) {
        long lowbits = M * a;
        int highbits = (int) Math.unsignedMultiplyHigh(lowbits, positive_d);
        return highbits - ((positive_d - 1) & (a >> 31));
    }

    /**
     * Computes (a / d) given precomputed M, assumes that d must not
     * be one of -1, 1, or -2147483648.
     */
    public static int fastdiv_s32(int a, long M, int d) {
        int highbits = (int) Math.multiplyHigh(M, a);
        highbits += (a < 0 ? 1 : 0);
        return (d < 0 ? -highbits : highbits);
    }

}
