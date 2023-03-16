/*
 * @(#)UuidNCName.java
 * Copyright © 2023 Werner Randelshofer, Switzerland. MIT License.
 */

package ch.randelshofer.uuidncname;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

/**
 * Converts {@link UUID}s to/from valid NCName productions for use in (X|HT)ML.
 */
public class UuidNCName {
    private static final byte[] BASE_32_HEX_INVERSE_ALPHABET = new byte[128];
    /**
     * See RFC 4648, Section 7, Table 4: The "Extended Hex" Base 32 Alphabet.
     * <p>
     * <a href="https://www.rfc-editor.org/rfc/rfc4648#section-7">rfc4648, section 7</a>
     */
    private static final byte[] BASE_32_HEX_LOWER_CASE_ALPHABET =
            toLowerCase("0123456789ABCDEFGHIJKLMNOPQRSTUV".getBytes(StandardCharsets.ISO_8859_1));
    /**
     * See RFC 4648, Section 7, Table 4: The "Extended Hex" Base 32 Alphabet.
     * <p>
     * <a href="https://www.rfc-editor.org/rfc/rfc4648#section-7">rfc4648, section 7</a>
     */
    private static final byte[] BASE_32_HEX_UPPER_CASE_ALPHABET =
            "0123456789ABCDEFGHIJKLMNOPQRSTUV".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] BASE_32_INVERSE_ALPHABET = new byte[128];

    ;
    /**
     * See RFC 4648, Section 6, Table 3: The Base 32 Alphabet.
     * <p>
     * <a href="https://www.rfc-editor.org/rfc/rfc4648#section-6">rfc4648, section 6</a>
     */
    private static final byte[] BASE_32_LOWER_CASE_ALPHABET =
            toLowerCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".getBytes(StandardCharsets.ISO_8859_1));
    /**
     * See RFC 4648, Section 6, Table 3: The Base 32 Alphabet.
     * <p>
     * <a href="https://www.rfc-editor.org/rfc/rfc4648#section-6">rfc4648, section 6</a>
     */
    private static final byte[] BASE_32_UPPER_CASE_ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] BASE_64_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_64_LEXICAL_ALPHABET =
            "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte[] BASE_64_LEXICAL_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_64_URL_SAFE_ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".getBytes(StandardCharsets.ISO_8859_1);
    private static final byte OTHER_CLASS = -1;
    private static final byte[] VARIANT_LEXICAL_INVERSE_ALPHABET = new byte[128];
    private static final byte[] VARIANT_LEXICAL_LOWER_CASE_ALPHABET =
            toLowerCase("234567QRSTUVWXYZ".getBytes(StandardCharsets.ISO_8859_1));
    private static final byte[] VARIANT_LEXICAL_UPPER_CASE_ALPHABET =
            "234567QRSTUVWXYZ".getBytes(StandardCharsets.ISO_8859_1);

    static {
        computeInverseAlphabet(BASE_64_URL_SAFE_ALPHABET, BASE_64_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_64_LEXICAL_ALPHABET, BASE_64_LEXICAL_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_LOWER_CASE_ALPHABET, BASE_32_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_UPPER_CASE_ALPHABET, BASE_32_INVERSE_ALPHABET, false);
        computeInverseAlphabet(BASE_32_HEX_LOWER_CASE_ALPHABET, BASE_32_HEX_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_HEX_UPPER_CASE_ALPHABET, BASE_32_HEX_INVERSE_ALPHABET, false);
        computeInverseAlphabet(VARIANT_LEXICAL_LOWER_CASE_ALPHABET, VARIANT_LEXICAL_INVERSE_ALPHABET, true);
        computeInverseAlphabet(VARIANT_LEXICAL_UPPER_CASE_ALPHABET, VARIANT_LEXICAL_INVERSE_ALPHABET, false);
    }


    /**
     * Don't let anyone instantiate this class.
     */
    public UuidNCName() {
    }

    private static void computeInverseAlphabet(byte[] alphabet, byte[] inverseAlphabet, boolean fillOtherClass) {
        if (fillOtherClass) {
            Arrays.fill(inverseAlphabet, OTHER_CLASS);
        }
        for (int i = 0; i < alphabet.length; i++) {
            inverseAlphabet[alphabet[i]] = (byte) i;
        }
    }

    private static UUID fromBase32(String str) {
        long msb = readUInt60(str, 1, 12, BASE_32_INVERSE_ALPHABET, 5);
        long lsb = readUInt60(str, 13, 12, BASE_32_INVERSE_ALPHABET, 5);
        int version = readVersion(str);
        int variant = readVariant(str, BASE_32_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase32Lex(String str) {
        long msb = readUInt60(str, 1, 12, BASE_32_HEX_INVERSE_ALPHABET, 5);
        long lsb = readUInt60(str, 13, 12, BASE_32_HEX_INVERSE_ALPHABET, 5);
        int version = readVersion(str);
        int variantLex = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsbLex(lsb, variantLex));
    }

    private static UUID fromBase58(String str) {
        int version = readVersion(str);
        int variant = readVariant(str, BASE_32_INVERSE_ALPHABET);
        int endIndex = 22;
        while (str.charAt(endIndex - 1) == '_' && endIndex > 0) {
            endIndex--;
        }
        if (endIndex < 16)
            throw new IllegalArgumentException("UUID string has too many '_' characters.");
        int[] uint30 = FastBase58.decode58(str, 1, endIndex);
        long msb = readMsb((((long) uint30[0] << 30) | uint30[1]), version);
        long lsb = readLsb(((long) uint30[2] << 30) | uint30[3], variant);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase58Lex(String str) {
        int version = readVersion(str);
        int variantLex = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        int[] uint30 = FastBase58.decode58Lex(str, 1, 21);
        long msb = readMsb((((long) uint30[0] << 30) | uint30[1]), version);
        long lsb = readLsbLex(((long) uint30[2] << 30) | uint30[3], variantLex);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase64(String str) {
        long msb = readUInt60(str, 1, 10, BASE_64_INVERSE_ALPHABET, 6);
        long lsb = readUInt60(str, 11, 10, BASE_64_INVERSE_ALPHABET, 6);
        int version = readVersion(str);
        int variant = readVariant(str, BASE_32_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase64Lex(String str) {
        long msb = readUInt60(str, 1, 10, BASE_64_LEXICAL_INVERSE_ALPHABET, 6);
        long lsb = readUInt60(str, 11, 10, BASE_64_LEXICAL_INVERSE_ALPHABET, 6);
        int version = readVersion(str);
        int variantLex = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsbLex(lsb, variantLex));
    }

    public static UUID fromString(String str) {
        char variantChar = str.charAt(str.length() - 1);
        int isLexical = (variantChar < 128) && VARIANT_LEXICAL_INVERSE_ALPHABET[variantChar] >= 0 ? -22 : 0;
        return switch (str.length() + isLexical) {
            case 0 -> fromBase64Lex(str);
            case 1 -> fromBase58Lex(str);
            case 4 -> fromBase32Lex(str);
            case 22 -> fromBase64(str);
            case 23 -> fromBase58(str);
            case 26 -> fromBase32(str);
            default -> UUID.fromString(str);
        };
    }

    private static long getLsb(UUID uuid) {
        return uuid.getLeastSignificantBits() & 0x0fff_ffffffffffffL;
    }

    private static long getLsbLex(UUID uuid) {
        // return Long.compress(uuid.getLeastSignificantBits(), 0x3fff_fffffffffffcL);
        return (uuid.getLeastSignificantBits() >>> 2) & 0x0fff_ffff_ffffffffL;
    }

    private static long getMsb(UUID uuid) {
        return Long.compress(uuid.getMostSignificantBits(), 0xffffffff_ffff_0fffL);
    }

    private static int getVariant(UUID uuid) {
        return (int) (uuid.getLeastSignificantBits() >>> 60);
    }

    private static int getVariantLex(UUID uuid) {
        return (int) Long.compress(uuid.getLeastSignificantBits(), 0xc000_000000000003L);
    }

    /**
     * Looks the specified character up in the provided inverse alphabet table,
     * otherwise returns a value &lt; 0.
     *
     * @param inverseAlphabet a table that maps from a character to a decimal value.
     * @param ch              a character
     * @return the decimal value or a value &lt; 0.
     */
    private static int lookupDigit(byte[] inverseAlphabet, char ch) {
        // The branchy code is faster than the branchless code, because we
        // will almost always have a character that is in the table.
        // Branchless code:  return inverseAlphabet[ch & 127] | (127 - ch) >> 31;
        return ch > 127 ? -1 : inverseAlphabet[ch];
    }

    private static long readLsb(long bits, int variant) {
        return ((long) variant << 60) | bits;
    }

    private static long readLsbLex(long bits, int variant) {
        return Long.expand(variant, 0xc000_000000000003L)
                | bits << 2;
    }

    private static long readMsb(long bits, int version) {
        return ((long) version << 12) | Long.expand(bits, 0xffffffff_ffff_0fffL);
    }

    private static long readUInt60(String str, int offset, int len, byte[] inverseAlphabet, int baseShift) {
        long bits = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(offset + i);
            int value = lookupDigit(inverseAlphabet, ch);
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << baseShift) | value;
        }
        return bits;
    }

    private static int readVariant(String str, byte[] charToBaseMap) {
        char ch = str.charAt(str.length() - 1);
        int variant = lookupDigit(charToBaseMap, ch);
        if (variant < 0) throw new IllegalArgumentException("Illegal variant character: " + (char) ch);
        return variant;
    }

    private static int readVersion(String str) {
        char ch = str.charAt(0);
        int version = lookupDigit(BASE_32_INVERSE_ALPHABET, ch);
        if (version < 0) throw new IllegalArgumentException("Illegal version character: " + (char) ch);
        return version;
    }

    private static String toBase32(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE_ALPHABET[uuid.version()];
        str[25] = BASE_32_LOWER_CASE_ALPHABET[getVariant(uuid)];
        writeUInt60(str, 1, 12, getMsb(uuid), BASE_32_LOWER_CASE_ALPHABET, 5, 31);
        writeUInt60(str, 13, 12, getLsb(uuid), BASE_32_LOWER_CASE_ALPHABET, 5, 31);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toBase32Lex(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE_ALPHABET[uuid.version()];
        str[25] = VARIANT_LEXICAL_LOWER_CASE_ALPHABET[getVariantLex(uuid)];
        writeUInt60(str, 1, 12, getMsb(uuid), BASE_32_HEX_LOWER_CASE_ALPHABET, 5, 31);
        writeUInt60(str, 13, 12, getLsbLex(uuid), BASE_32_HEX_LOWER_CASE_ALPHABET, 5, 31);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toBase58(UUID uuid) {
        byte[] b = new byte[24];
        long msb = getMsb(uuid);
        long lsb = getLsb(uuid);
        FastBase58.encode58(msb, lsb, b, 2);
        b[23] = BASE_32_UPPER_CASE_ALPHABET[getVariant(uuid)];
        b[1] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        return new String(b, 1, 23, StandardCharsets.ISO_8859_1);
    }

    private static String toBase58Lex(UUID uuid) {
        byte[] b = new byte[24];
        long msb = getMsb(uuid);
        long lsb = getLsbLex(uuid);
        FastBase58.encode58Lex(msb, lsb, b, 2);
        b[23] = VARIANT_LEXICAL_UPPER_CASE_ALPHABET[getVariantLex(uuid)];
        b[1] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        return new String(b, 1, 23, StandardCharsets.ISO_8859_1);
    }

    private static String toBase64(UUID uuid) {
        byte[] str = new byte[22];
        str[0] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        str[21] = BASE_32_UPPER_CASE_ALPHABET[getVariant(uuid)];
        writeUInt60(str, 1, 10, getMsb(uuid), BASE_64_URL_SAFE_ALPHABET, 6, 63);
        writeUInt60(str, 11, 10, getLsb(uuid), BASE_64_URL_SAFE_ALPHABET, 6, 63);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toBase64Lex(UUID uuid) {
        byte[] str = new byte[22];
        str[0] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        str[21] = VARIANT_LEXICAL_UPPER_CASE_ALPHABET[getVariantLex(uuid)];
        writeUInt60(str, 1, 10, getMsb(uuid), BASE_64_LEXICAL_ALPHABET, 6, 63);
        writeUInt60(str, 11, 10, getLsbLex(uuid), BASE_64_LEXICAL_ALPHABET, 6, 63);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toCanonical(UUID uuid) {
        return uuid.toString();
    }

    private static byte[] toLowerCase(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] |= 0x20;
        }
        return bytes;
    }

    public static String toString(UUID uuid, UuidFormat format) {
        return switch (format) {
            case CANONICAL -> toCanonical(uuid);
            case NCNAME_32 -> toBase32(uuid);
            case NCNAME_58 -> toBase58(uuid);
            case NCNAME_64 -> toBase64(uuid);
            case NCNAME_32_LEX -> toBase32Lex(uuid);
            case NCNAME_58_LEX -> toBase58Lex(uuid);
            case NCNAME_64_LEX -> toBase64Lex(uuid);
        };
    }

    private static void writeUInt60(byte[] str, int offset, int len, long val, byte[] alphabet, int baseShift, int mask) {
        int i = offset + len;
        do {
            str[--i] = alphabet[(int) val & mask];
            val >>>= baseShift;
        } while (i > offset);
    }
}
