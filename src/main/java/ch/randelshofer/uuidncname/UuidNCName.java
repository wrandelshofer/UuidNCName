package ch.randelshofer.uuidncname;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class UuidNCName {
    private static final byte[] BASE_32_LOWER_CASE = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            '2', '3', '4', '5', '6', '7',
    };
    private static final byte[] BASE_32_UPPER_CASE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7',
    };
    private static final byte[] VARIANT_LEXICAL_LOWER_CASE = {
            '2', '3', '4', '5', '6', '7',
            'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final byte[] VARIANT_LEXICAL_UPPER_CASE = {
            '2', '3', '4', '5', '6', '7',
            'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final byte[] BASE_32_LEXICAL_LOWER_CASE = {
            '2', '3', '4', '5', '6', '7',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
    };
    private static final byte[] BASE_32_LEXICAL_UPPER_CASE = {
            '2', '3', '4', '5', '6', '7',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    private static final byte[] BASE_64_URL_SAFE = {
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
    private static final byte[] BASE_64_LEXICAL = {
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
    private static final byte[] CHAR_TO_BASE_32_MAP = new byte[128];
    private static final byte[] CHAR_TO_VARIANT_LEXICAL_MAP = new byte[128];
    private static final byte[] CHAR_TO_BASE_32_LEXICAL_MAP = new byte[128];
    private static final byte[] CHAR_TO_BASE_64_MAP = new byte[128];
    private static final byte[] CHAR_TO_BASE_64_LEXICAL_MAP = new byte[128];
    private static final byte OTHER_CLASS = -1;
    private final static VarHandle readLongBE =
            MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);

    static {
        Arrays.fill(CHAR_TO_BASE_64_MAP, OTHER_CLASS);
        for (int i = 0; i < BASE_64_URL_SAFE.length; i++) {
            CHAR_TO_BASE_64_MAP[BASE_64_URL_SAFE[i]] = (byte) i;
        }
    }

    static {
        Arrays.fill(CHAR_TO_BASE_64_LEXICAL_MAP, OTHER_CLASS);
        for (int i = 0; i < BASE_64_LEXICAL.length; i++) {
            CHAR_TO_BASE_64_LEXICAL_MAP[BASE_64_LEXICAL[i]] = (byte) i;
        }
    }

    static {
        Arrays.fill(CHAR_TO_BASE_32_MAP, OTHER_CLASS);
        for (int i = 0; i < BASE_32_LOWER_CASE.length; i++) {
            CHAR_TO_BASE_32_MAP[BASE_32_LOWER_CASE[i]] = (byte) i;
        }
        for (int i = 0; i < BASE_32_UPPER_CASE.length; i++) {
            CHAR_TO_BASE_32_MAP[BASE_32_UPPER_CASE[i]] = (byte) i;
        }
    }

    static {
        Arrays.fill(CHAR_TO_BASE_32_LEXICAL_MAP, OTHER_CLASS);
        for (int i = 0; i < BASE_32_LEXICAL_LOWER_CASE.length; i++) {
            CHAR_TO_BASE_32_LEXICAL_MAP[BASE_32_LEXICAL_LOWER_CASE[i]] = (byte) i;
        }
        for (int i = 0; i < BASE_32_LEXICAL_UPPER_CASE.length; i++) {
            CHAR_TO_BASE_32_LEXICAL_MAP[BASE_32_LEXICAL_UPPER_CASE[i]] = (byte) i;
        }
    }

    static {
        Arrays.fill(CHAR_TO_VARIANT_LEXICAL_MAP, OTHER_CLASS);
        for (int i = 0; i < VARIANT_LEXICAL_LOWER_CASE.length; i++) {
            CHAR_TO_VARIANT_LEXICAL_MAP[VARIANT_LEXICAL_LOWER_CASE[i]] = (byte) i;
        }
        for (int i = 0; i < VARIANT_LEXICAL_UPPER_CASE.length; i++) {
            CHAR_TO_VARIANT_LEXICAL_MAP[VARIANT_LEXICAL_UPPER_CASE[i]] = (byte) i;
        }
    }

    /**
     * Don't let anyone instantiate this class.
     */
    public UuidNCName() {
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

    private static long readBase32(String str, int offset, byte[] charToBaseMap) {
        long bits = 0;
        for (int i = 0; i < 12; i++) {
            char ch = str.charAt(offset + i);
            int value = ch >= 128 ? OTHER_CLASS : charToBaseMap[ch];
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << 5) | value;
        }
        return bits;
    }

    private static byte[] readBase58(String string, int endIndex) {
        byte[] bytes = Base58.decode(string.substring(1, endIndex));
        if (bytes.length < 16) {
            byte[] tmp = bytes;
            bytes = new byte[16];
            System.arraycopy(tmp, 0, bytes, bytes.length - tmp.length, tmp.length);
        }
        for (int i = 0; i < bytes.length - 16; i++) {
            if (bytes[i] != 0) {
                throw new IllegalArgumentException("UUID string has " + (bytes.length * 8 + 8) + " bits instead of 128 bits.");
            }
        }
        return bytes;
    }

    private static long readBase64(String str, int offset, byte[] charToBaseMap) {
        long bits = 0;
        for (int i = 0; i < 10; i++) {
            char ch = str.charAt(offset + i);
            int value = ch >= 128 ? OTHER_CLASS : charToBaseMap[ch];
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << 6) | value;
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
        int version = ch >= 128 ? OTHER_CLASS : CHAR_TO_BASE_32_MAP[ch];
        if (version < 0) throw new IllegalArgumentException("Illegal version character: " + (char) ch);
        return version;
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

    private static void writeUnsignedLong(byte[] str, int offset, long val, byte[] alphabet, int len, int shift, int mask) {
        int i = offset + len;
        do {
            str[--i] = alphabet[(int) val & mask];
            val >>>= shift;
        } while (i > offset);
    }

    private static UUID fromBase32(String str) {
        if (str.length() != 26)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 26.");
        long msb = readBase32(str, 1, CHAR_TO_BASE_32_MAP);
        long lsb = readBase32(str, 13, CHAR_TO_BASE_32_MAP);
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_BASE_32_MAP);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase32Lexical(String str) {
        if (str.length() != 26)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 26.");
        long msb = readBase32(str, 1, CHAR_TO_BASE_32_LEXICAL_MAP);
        long lsb = readBase32(str, 13, CHAR_TO_BASE_32_LEXICAL_MAP);
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    private static UUID fromBase58(String str) {
        if (str.length() != 23)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 23.");
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_BASE_32_MAP);
        int endIndex = 22;
        while (str.charAt(endIndex - 1) == '_' && endIndex > 1) {
            endIndex--;
        }
        byte[] bytes = readBase58(str, endIndex);
        long msb = readMsb(bytes, version);
        long lsb = readLsb(bytes, variant);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase58Lexical(String str) {
        if (str.length() != 23)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 23.");
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        byte[] bytes = readBase58(str, 22);
        long msb = readMsb(bytes, version);
        long lsb = readLsb(bytes, variant);
        return new UUID(msb, lsb);
    }

    public static UUID fromBase64(String str) {
        if (str.length() != 22)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 22.");
        long msb = readBase64(str, 1, CHAR_TO_BASE_64_MAP);
        long lsb = readBase64(str, 11, CHAR_TO_BASE_64_MAP);
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_BASE_32_MAP);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    public static UUID fromBase64Lexical(String str) {
        if (str.length() != 22)
            throw new IllegalArgumentException("UUID string is " + str.length() + " characters long instead of 22.");
        long msb = readBase64(str, 1, CHAR_TO_BASE_64_LEXICAL_MAP);
        long lsb = readBase64(str, 11, CHAR_TO_BASE_64_LEXICAL_MAP);
        int version = readVersion(str);
        int variant = readVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        return new UUID(readMsb(msb, version), readLsb(lsb, variant));
    }

    public static UUID fromString(String str) {
        char variantChar = str.charAt(str.length() - 1);
        int isLexical = (variantChar < 128) && CHAR_TO_VARIANT_LEXICAL_MAP[variantChar] >= 0 ? -22 : 0;
        return switch (str.length() + isLexical) {
            case 0 -> fromBase64Lexical(str);
            case 1 -> fromBase58Lexical(str);
            case 4 -> fromBase32Lexical(str);
            case 22 -> fromBase64(str);
            case 23 -> fromBase58(str);
            case 26 -> fromBase32(str);
            default -> UUID.fromString(str);
        };
    }

    public static String toBase32(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE[uuid.version()];
        str[25] = BASE_32_LOWER_CASE[getVariant(uuid)];
        writeUnsignedLong(str, 1, getMsb(uuid), BASE_32_LOWER_CASE, 12, 5, 31);
        writeUnsignedLong(str, 13, getLsb(uuid), BASE_32_LOWER_CASE, 12, 5, 31);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase32Lexical(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE[uuid.version()];
        str[25] = VARIANT_LEXICAL_LOWER_CASE[getVariant(uuid)];
        writeUnsignedLong(str, 1, getMsb(uuid), BASE_32_LEXICAL_LOWER_CASE, 12, 5, 31);
        writeUnsignedLong(str, 13, getLsb(uuid), BASE_32_LEXICAL_LOWER_CASE, 12, 5, 31);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58(UUID uuid) {
        byte[] b = new byte[23];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        b[22] = BASE_32_UPPER_CASE[getVariant(uuid)];

        byte[] raw = new byte[15];
        long msb = getMsb(uuid);
        readLongBE.set(raw, 0, msb << 4);
        readLongBE.set(raw, 7, msb << 60 | getLsb(uuid));
        byte[] encoded = Base58.encode(raw).getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(encoded, 0, b, 1, encoded.length);
        for (int i = encoded.length; i < 21; i++) {
            b[i + 1] = '_';
        }

        return new String(b, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58Lexical(UUID uuid) {
        byte[] b = new byte[23];
        long msb = getMsb(uuid);
        long lsb = getLsb(uuid);
        FastBase58.encode58x58(msb, lsb, b, 1);
        b[22] = VARIANT_LEXICAL_UPPER_CASE[getVariant(uuid)];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        return new String(b, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58LexicalSlow(UUID uuid) {
        byte[] b = new byte[23];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        b[22] = VARIANT_LEXICAL_UPPER_CASE[getVariant(uuid)];
        byte[] raw = new byte[15];
        long msb = getMsb(uuid);
        readLongBE.set(raw, 0, msb << 4);
        readLongBE.set(raw, 7, msb << 60 | getLsb(uuid));
        byte[] encoded = Base58.encode(raw).getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(encoded, 0, b, 22 - encoded.length, encoded.length);
        for (int i = 1; i < 22 - encoded.length; i++) {
            b[i] = '1';
        }
        return new String(b, StandardCharsets.ISO_8859_1);
    }

    public static String toBase64(UUID uuid) {
        byte[] str = new byte[22];
        str[0] = BASE_32_UPPER_CASE[uuid.version()];
        str[21] = BASE_32_UPPER_CASE[getVariant(uuid)];
        writeUnsignedLong(str, 1, getMsb(uuid), BASE_64_URL_SAFE, 10, 6, 63);
        writeUnsignedLong(str, 11, getLsb(uuid), BASE_64_URL_SAFE, 10, 6, 63);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase64Lexical(UUID uuid) {
        byte[] str = new byte[22];
        str[0] = BASE_32_UPPER_CASE[uuid.version()];
        str[21] = VARIANT_LEXICAL_UPPER_CASE[getVariant(uuid)];
        writeUnsignedLong(str, 1, getMsb(uuid), BASE_64_LEXICAL, 10, 6, 63);
        writeUnsignedLong(str, 11, getLsb(uuid), BASE_64_LEXICAL, 10, 6, 63);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toCanonical(UUID uuid) {
        return uuid.toString();
    }
}
