package ch.randelshofer.uuidncname;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class UuidNCName {
    private static final byte[] BASE_32_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_32_LEXICAL_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_32_LEXICAL_LOWER_CASE_ALPHABET = {
            '2', '3', '4', '5', '6', '7',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
    };
    private static final byte[] BASE_32_LEXICAL_UPPER_CASE_ALPHABET = {
            '2', '3', '4', '5', '6', '7',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    private static final byte[] BASE_32_LOWER_CASE_ALPHABET = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '2', '3', '4', '5', '6', '7',
    };
    private static final byte[] BASE_32_UPPER_CASE_ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7',
    };
    private static final byte[] BASE_64_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_64_LEXICAL_ALPHABET = {
            '-',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '_',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
    };
    private static final byte[] BASE_64_LEXICAL_INVERSE_ALPHABET = new byte[128];
    private static final byte[] BASE_64_URL_SAFE_ALPHABET = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '-',
            '_'
    };
    private static final byte OTHER_CLASS = -1;
    private static final byte[] VARIANT_LEXICAL_INVERSE_ALPHABET = new byte[128];
    private static final byte[] VARIANT_LEXICAL_LOWER_CASE_ALPHABET = {
            '2', '3', '4', '5', '6', '7',
            'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final byte[] VARIANT_LEXICAL_UPPER_CASE_ALPHABET = {
            '2', '3', '4', '5', '6', '7',
            'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private final static VarHandle readLongBE =
            MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);

    static {
        computeInverseAlphabet(BASE_64_URL_SAFE_ALPHABET, BASE_64_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_64_LEXICAL_ALPHABET, BASE_64_LEXICAL_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_LOWER_CASE_ALPHABET, BASE_32_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_UPPER_CASE_ALPHABET, BASE_32_INVERSE_ALPHABET, false);
        computeInverseAlphabet(BASE_32_LEXICAL_LOWER_CASE_ALPHABET, BASE_32_LEXICAL_INVERSE_ALPHABET, true);
        computeInverseAlphabet(BASE_32_LEXICAL_UPPER_CASE_ALPHABET, BASE_32_LEXICAL_INVERSE_ALPHABET, false);
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
        if (str.length() != 26)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 26.");
        long msb = readUInt60(str, 1, 12, BASE_32_INVERSE_ALPHABET, 5);
        long lsb = readUInt60(str, 13, 12, BASE_32_INVERSE_ALPHABET, 5);
        int version = readVersion(str);
        int variant = readVariant(str, BASE_32_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase32Lex(String str) {
        if (str.length() != 26)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 26.");
        long msb = readUInt60(str, 1, 12, BASE_32_LEXICAL_INVERSE_ALPHABET, 5);
        long lsb = readUInt60(str, 13, 12, BASE_32_LEXICAL_INVERSE_ALPHABET, 5);
        int version = readVersion(str);
        int variant = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase58(String str) {
        if (str.length() != 23)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 23.");
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
        if (str.length() != 23)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 23.");
        int version = readVersion(str);
        int variant = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        int[] uint30 = FastBase58.decode58Lex(str, 1, 21);
        long msb = readMsb((((long) uint30[0] << 30) | uint30[1]), version);
        long lsb = readLsb(((long) uint30[2] << 30) | uint30[3], variant);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase64(String str) {
        if (str.length() != 22)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 22.");
        long msb = readUInt60(str, 1, 10, BASE_64_INVERSE_ALPHABET, 6);
        long lsb = readUInt60(str, 11, 10, BASE_64_INVERSE_ALPHABET, 6);
        int version = readVersion(str);
        int variant = readVariant(str, BASE_32_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase64Lex(String str) {
        if (str.length() != 22)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 22.");
        long msb = readUInt60(str, 1, 10, BASE_64_LEXICAL_INVERSE_ALPHABET, 6);
        long lsb = readUInt60(str, 11, 10, BASE_64_LEXICAL_INVERSE_ALPHABET, 6);
        int version = readVersion(str);
        int variant = readVariant(str, VARIANT_LEXICAL_INVERSE_ALPHABET);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    public static String toString(UUID uuid, UuidFormat format) {
        return switch (format) {
            case CANONICAL -> toCanonical(uuid);
            case BASE32 -> toBase32(uuid);
            case BASE58 -> toBase58(uuid);
            case BASE64 -> toBase64(uuid);
            case BASE32_LEX -> toBase32Lex(uuid);
            case BASE58_LEX -> toBase58Lex(uuid);
            case BASE64_LEX -> toBase64Lex(uuid);
        };
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

    private static long getMsb(UUID uuid) {
        return Long.compress(uuid.getMostSignificantBits(), 0xffffffff_ffff_0fffL);
    }

    private static int getVariant(UUID uuid) {
        return (int) (uuid.getLeastSignificantBits() >>> 60);
    }

    private static long readLsb(long bits, int variant) {
        return ((long) variant << 60) | bits;
    }

    private static long readLsb(byte[] bytes, int variant) {
        long bits = (long) readLongBE.get(bytes, bytes.length - 8) & 0x0fff_ffffffffffffL;
        return readLsb(bits, variant);
    }

    private static long readMsb(long bits, int version) {
        return ((long) version << 12) | Long.expand(bits, 0xffffffff_ffff_0fffL);
    }

    private static long readMsb(byte[] bytes, int version) {
        long bits = ((long) readLongBE.get(bytes, bytes.length - 16) << 4)
                | ((long) readLongBE.get(bytes, bytes.length - 8) >>> 60);
        return readMsb(bits, version);
    }

    private static long readMsbNew(byte[] bytes, int version) {
        long bits = ((long) readLongBE.get(bytes, 1) << 4)
                | (bytes[9] >>> 4);
        return readMsb(bits, version);
    }

    private static long readUInt60(String str, int offset, int len, byte[] inverseAlphabet, int baseShift) {
        long bits = 0;
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(offset + i);
            int value = ch >= 128 ? OTHER_CLASS : inverseAlphabet[ch];
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << baseShift) | value;
        }
        return bits;
    }

    private static int readVariant(String str, byte[] charToBaseMap) {
        char ch = str.charAt(str.length() - 1);
        int variant = ch >= 128 ? OTHER_CLASS : charToBaseMap[ch];
        if (variant < 0) throw new IllegalArgumentException("Illegal variant character: " + (char) ch);
        return variant;
    }

    private static int readVersion(String str) {
        char ch = str.charAt(0);
        int version = ch >= 128 ? OTHER_CLASS : BASE_32_INVERSE_ALPHABET[ch];
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
        str[25] = VARIANT_LEXICAL_LOWER_CASE_ALPHABET[getVariant(uuid)];
        writeUInt60(str, 1, 12, getMsb(uuid), BASE_32_LEXICAL_LOWER_CASE_ALPHABET, 5, 31);
        writeUInt60(str, 13, 12, getLsb(uuid), BASE_32_LEXICAL_LOWER_CASE_ALPHABET, 5, 31);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toBase58(UUID uuid) {
        byte[] b = new byte[25];
        long msb = getMsb(uuid);
        long lsb = getLsb(uuid);
        FastBase58.encode58(msb, lsb, b, 3);
        b[24] = BASE_32_UPPER_CASE_ALPHABET[getVariant(uuid)];
        b[2] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        return new String(b, 2, 23, StandardCharsets.ISO_8859_1);
    }

    private static String toBase58Lex(UUID uuid) {
        byte[] b = new byte[25];
        long msb = getMsb(uuid);
        long lsb = getLsb(uuid);
        FastBase58.encode58LexP4(msb, lsb, b, 3);
        b[24] = VARIANT_LEXICAL_UPPER_CASE_ALPHABET[getVariant(uuid)];
        b[2] = BASE_32_UPPER_CASE_ALPHABET[uuid.version()];
        return new String(b, 2, 23, StandardCharsets.ISO_8859_1);
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
        str[21] = VARIANT_LEXICAL_UPPER_CASE_ALPHABET[getVariant(uuid)];
        writeUInt60(str, 1, 10, getMsb(uuid), BASE_64_LEXICAL_ALPHABET, 6, 63);
        writeUInt60(str, 11, 10, getLsb(uuid), BASE_64_LEXICAL_ALPHABET, 6, 63);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    private static String toCanonical(UUID uuid) {
        return uuid.toString();
    }

    private static void writeUInt60(byte[] str, int offset, int len, long val, byte[] alphabet, int baseShift, int mask) {
        int i = offset + len;
        do {
            str[--i] = alphabet[(int) val & mask];
            val >>>= baseShift;
        } while (i > offset);
    }
}
