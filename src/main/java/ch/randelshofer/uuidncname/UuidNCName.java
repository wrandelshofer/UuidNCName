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
            '2', '3', '4', '5', '6', '7'
    };
    private static final byte[] BASE_32_UPPER_CASE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '2', '3', '4', '5', '6', '7'
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
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v',
    };
    private static final byte[] BASE_32_LEXICAL_UPPER_CASE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V',
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

    private static long compressLsb(UUID uuid) {
        return uuid.getLeastSignificantBits() & 0x0fff_ffffffffffffL;
    }

    private static long compressMsb(UUID uuid) {
        return Long.compress(uuid.getMostSignificantBits(), 0xffffffff_ffff_0fffL);
    }

    private static int compressVariant(UUID uuid) {
        return (int) (uuid.getLeastSignificantBits() >>> 60);
    }

    private static long decodeBase32(byte[] str, int offset, byte[] charToBaseMap) {
        long bits = 0;
        for (int i = 0; i < 12; i++) {
            byte ch = str[offset + i];
            int value = ch < 0 ? OTHER_CLASS : charToBaseMap[ch];
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << 5) | value;
        }
        return bits;
    }

    private static byte[] decodeBase58(String string, int endIndex) {
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

    private static long decodeBase64(byte[] str, int offset, byte[] charToBaseMap) {
        long bits = 0;
        for (int i = 0; i < 10; i++) {
            byte ch = str[offset + i];
            int value = ch < 0 ? OTHER_CLASS : charToBaseMap[ch];
            if (value < 0) throw new IllegalArgumentException("Illegal character " + (char) ch);
            bits = (bits << 6) | value;
        }
        return bits;
    }


    private static int decodeVariant(byte[] str, byte[] charToBaseMap) {
        byte ch = str[str.length - 1];
        int variant = ch < 0 ? OTHER_CLASS : charToBaseMap[ch];
        if (variant < 0) throw new IllegalArgumentException("Illegal variant character: " + (char) ch);
        return variant;
    }

    private static int decodeVersion(byte[] str) {
        byte ch = str[0];
        int version = ch < 0 ? OTHER_CLASS : CHAR_TO_BASE_32_MAP[ch];
        if (version < 0) throw new IllegalArgumentException("Illegal version character: " + (char) ch);
        return version;
    }

    private static long decompressLsb(long bits, int variant) {
        return ((long) variant << 60) | bits;
    }

    private static long decompressLsb(byte[] bytes, int variant) {
        long bits = (long) readLongBE.get(bytes, bytes.length - 8) & 0x0fff_ffffffffffffL;
        return decompressLsb(bits, variant);
    }

    private static long decompressMsb(long bits, int version) {
        return ((long) version << 12) | Long.expand(bits, 0xffffffff_ffff_0fffL);
    }

    private static long decompressMsb(byte[] bytes, int version) {
        long bits = ((long) readLongBE.get(bytes, bytes.length - 16) << 4)
                | ((long) readLongBE.get(bytes, bytes.length - 8) >>> 60);
        return decompressMsb(bits, version);
    }

    private static void encodeBase32(byte[] str, int offset, long bits, byte[] alphabet) {
        for (int i = 11; i >= 0; i--) {
            str[offset + i] = alphabet[(int) bits & 31];
            bits >>>= 5;
        }
    }

    private static void encodeBase64(byte[] str, int offset, long bits, byte[] alphabet) {
        for (int i = 9; i >= 0; i--) {
            str[offset + i] = alphabet[(int) bits & 63];
            bits >>>= 6;
        }
    }

    private static UUID fromBase32(byte[] str) {
        if (str.length != 26)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 26.");
        long msb = decodeBase32(str, 1, CHAR_TO_BASE_32_MAP);
        long lsb = decodeBase32(str, 13, CHAR_TO_BASE_32_MAP);
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_BASE_32_MAP);
        return new UUID(decompressMsb(msb, version), decompressLsb(lsb, variant));
    }

    private static UUID fromBase32Lexical(byte[] str) {
        if (str.length != 26)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 26.");
        long msb = decodeBase32(str, 1, CHAR_TO_BASE_32_LEXICAL_MAP);
        long lsb = decodeBase32(str, 13, CHAR_TO_BASE_32_LEXICAL_MAP);
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        return new UUID(decompressMsb(msb, version), decompressLsb(lsb, variant));
    }

    private static UUID fromBase58(byte[] str) {
        if (str.length != 23)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 23.");
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_BASE_32_MAP);
        int endIndex = 22;
        while (str[endIndex - 1] == '_' && endIndex > 1) {
            endIndex--;
        }
        byte[] bytes = decodeBase58(new String(str, StandardCharsets.ISO_8859_1), endIndex);
        long msb = decompressMsb(bytes, version);
        long lsb = decompressLsb(bytes, variant);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase58Lexical(byte[] str) {
        if (str.length != 23)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 23.");
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        byte[] bytes = decodeBase58(new String(str, StandardCharsets.ISO_8859_1), 22);
        long msb = decompressMsb(bytes, version);
        long lsb = decompressLsb(bytes, variant);
        return new UUID(msb, lsb);
    }

    private static UUID fromBase58LexicalOrNonLexical(byte[] str) {
        if (str.length != 23)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 23.");
        if (str[22] < 0 || CHAR_TO_VARIANT_LEXICAL_MAP[str[22]] < 0) return fromBase58(str);
        else return fromBase58Lexical(str);
    }

    private static UUID fromBase64LexicalOrNonLexical(byte[] str) {
        if (str.length != 22)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 22.");
        if (str[21] < 0 || CHAR_TO_VARIANT_LEXICAL_MAP[str[21]] < 0) return fromBase64(str);
        else return fromBase64Lexical(str);
    }

    private static UUID fromBase32LexicalOrNonLexical(byte[] str) {
        if (str.length != 26)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 26.");
        if (str[25] < 0 || CHAR_TO_VARIANT_LEXICAL_MAP[str[25]] < 0) return fromBase32(str);
        else return fromBase32Lexical(str);
    }

    private static UUID fromBase64(byte[] str) {
        if (str.length != 22)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 22.");
        long msb = decodeBase64(str, 1, CHAR_TO_BASE_64_MAP);
        long lsb = decodeBase64(str, 11, CHAR_TO_BASE_64_MAP);
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_BASE_32_MAP);
        return new UUID(decompressMsb(msb, version), decompressLsb(lsb, variant));
    }


    private static UUID fromBase64Lexical(byte[] str) {
        if (str.length != 22)
            throw new IllegalArgumentException("UUID string is " + str.length + " characters long instead of 22.");
        long msb = decodeBase64(str, 1, CHAR_TO_BASE_64_LEXICAL_MAP);
        long lsb = decodeBase64(str, 11, CHAR_TO_BASE_64_LEXICAL_MAP);
        int version = decodeVersion(str);
        int variant = decodeVariant(str, CHAR_TO_VARIANT_LEXICAL_MAP);
        return new UUID(decompressMsb(msb, version), decompressLsb(lsb, variant));
    }

    public static UUID fromString(String string) {
        byte[] str = string.getBytes(StandardCharsets.ISO_8859_1);
        return switch (str.length) {
            case 26 -> fromBase32LexicalOrNonLexical(str);
            case 23 -> fromBase58LexicalOrNonLexical(str);
            case 22 -> fromBase64LexicalOrNonLexical(str);
            default -> UUID.fromString(string);
        };
    }

    public static String toBase32(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE[uuid.version()];
        str[25] = BASE_32_LOWER_CASE[compressVariant(uuid)];
        encodeBase32(str, 1, compressMsb(uuid), BASE_32_LOWER_CASE);
        encodeBase32(str, 13, compressLsb(uuid), BASE_32_LOWER_CASE);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase32Lexical(UUID uuid) {
        byte[] str = new byte[26];
        str[0] = BASE_32_LOWER_CASE[uuid.version()];
        str[25] = VARIANT_LEXICAL_LOWER_CASE[compressVariant(uuid)];
        encodeBase32(str, 1, compressMsb(uuid), BASE_32_LEXICAL_LOWER_CASE);
        encodeBase32(str, 13, compressLsb(uuid), BASE_32_LEXICAL_LOWER_CASE);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58(UUID uuid) {
        byte[] b = new byte[23];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        b[22] = BASE_32_UPPER_CASE[compressVariant(uuid)];

        byte[] raw = new byte[15];
        long msb = compressMsb(uuid);
        readLongBE.set(raw, 0, msb << 4);
        readLongBE.set(raw, 7, msb << 60 | compressLsb(uuid));
        byte[] encoded = Base58.encode(raw).getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(encoded, 0, b, 1, encoded.length);
        for (int i = encoded.length; i < 21; i++) {
            b[i + 1] = '_';
        }

        return new String(b, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58Fast(UUID uuid) {
        byte[] b = new byte[23];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        b[22] = BASE_32_UPPER_CASE[compressVariant(uuid)];

        byte[] raw = new byte[15];
        long msb = compressMsb(uuid);
        long lsb = compressLsb(uuid);
        FastBase58.encode(msb, lsb, b, 1);
        /*
        readLongBE.set(raw, 0, msb << 4);
        readLongBE.set(raw, 7, msb << 60 | compressLsb(uuid));
        byte[] encoded = Base58.encode(raw).getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(encoded, 0, b, 1, encoded.length);
        for (int i = encoded.length; i < 21; i++) {
            b[i + 1] = '_';
        }
        */
        return new String(b, StandardCharsets.ISO_8859_1);
    }

    public static String toBase58Lexical(UUID uuid) {
        byte[] b = new byte[23];
        b[0] = BASE_32_UPPER_CASE[uuid.version()];
        b[22] = VARIANT_LEXICAL_UPPER_CASE[compressVariant(uuid)];
        byte[] raw = new byte[15];
        long msb = compressMsb(uuid);
        readLongBE.set(raw, 0, msb << 4);
        readLongBE.set(raw, 7, msb << 60 | compressLsb(uuid));
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
        str[21] = BASE_32_UPPER_CASE[compressVariant(uuid)];
        encodeBase64(str, 1, compressMsb(uuid), BASE_64_URL_SAFE);
        encodeBase64(str, 11, compressLsb(uuid), BASE_64_URL_SAFE);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toBase64Lexical(UUID uuid) {
        byte[] str = new byte[22];
        str[0] = BASE_32_UPPER_CASE[uuid.version()];
        str[21] = VARIANT_LEXICAL_UPPER_CASE[compressVariant(uuid)];
        encodeBase64(str, 1, compressMsb(uuid), BASE_64_LEXICAL);
        encodeBase64(str, 11, compressLsb(uuid), BASE_64_LEXICAL);
        return new String(str, StandardCharsets.ISO_8859_1);
    }

    public static String toCanonical(UUID uuid) {
        return uuid.toString();
    }
}
