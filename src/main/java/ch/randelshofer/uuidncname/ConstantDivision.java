/*
 * @(#)ConstantDivision.java
 * Copyright © 2023 Werner Randelshofer, Switzerland. MIT License.
 */

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
     * <p>
     * This method computes the correct result if {@code a} is less or
     * equal {@link Integer#MAX_VALUE}.
     *
     * @param a an unsigned value
     * @param M the precomputed M
     * @param d a strictly positive divisor
     * @return the modulo
     */
    public static int fastmod_u32(int a, long M, int d) {
        long lowbits = M * a;
        return (int) Math.unsignedMultiplyHigh(lowbits, d);
    }

    /**
     * Computes (a % d) given precomputed M.
     * <p>
     * This method computes the correct result if {@code a} is less or
     * equal {@link Integer#MAX_VALUE}.
     * <p>
     * It is possible to get the correct result for {@code a} that
     * are larger than that with some divisors. If {@code a} is too
     * large, you will get rounding errors.
     *
     * @param a an unsigned value
     * @param M the precomputed M
     * @param d a strictly positive divisor
     * @return the modulo
     */
    public static int fastmod_u32L(long a, long M, int d) {
        long lowbits = M * a;
        return (int) Math.unsignedMultiplyHigh(lowbits, d);
    }

    /**
     * Computes (a / d) given precomputed M for d>1.
     */
    public static int fastdiv_u32(int a, long M) {
        return (int) Math.unsignedMultiplyHigh(M, a);
    }

    public static long fastdiv_u32L(long a, long M) {
        return Math.unsignedMultiplyHigh(M, a);
    }
}
