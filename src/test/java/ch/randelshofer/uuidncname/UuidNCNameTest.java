package ch.randelshofer.uuidncname;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class UuidNCNameTest {
    @TestFactory
    public List<DynamicTest> dynamicTests_base32() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase32("00000000-0000-0000-0000-000000000000", "aaaaaaaaaaaaaaaaaaaaaaaaaa")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase32("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "bzjv6jsglv4pkfkyaarninsfbl")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase32("C232AB00-9414-11EC-B3C8-9E6BDECED846", "byizkwaeucqpmhse6nppm5wcgl")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase32("000003e8-cbb9-21ea-b201-00045a86c8a1", "caaaah2glxepkeaiaarninsfbl")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase32("3d813cbb-47fb-32ba-91df-831e1593ac29", "dhwatzo2h7mv2dx4ddykzhlbjj")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase32("01867b2c-a0dd-459c-98d7-89e545538d6c", "eagdhwlfa3vm4rv4j4vcvhdlmj")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase32("21f7f8de-8051-5b89-8680-0195ef798b6a", "feh37rxuakg4jnaabsxxxtc3ki")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase32("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "gd3euctbdfkyahse6nppm5wcgl")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase32("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "haf7sfytzwdgdrrg4bqgaoompj")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase32("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "igigd2tomab235sjs2x3jdaoai")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase32("ffffffff-ffff-ffff-ffff-ffffffffffff", "p777777777777777777777777p"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base32_lexical() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase32Lexical("00000000-0000-0000-0000-000000000000", "a2222222222222222222222222")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase32Lexical("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "btdpydmafpwje7es22lhchm73v")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase32Lexical("C232AB00-9414-11EC-B3C8-9E6BDECED846", "bscteq26o4kjgbm6yhjjgxq4av")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase32Lexical("000003e8-cbb9-21ea-b201-00045a86c8a1", "c2222buafr6je62c22lhchm73v")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase32Lexical("3d813cbb-47fb-32ba-91df-831e1593ac29", "dbq2ntiubzgpu5rw55setbf3dt")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase32Lexical("01867b2c-a0dd-459c-98d7-89e545538d6c", "e2a5bqf72vpgwlpwdwp4pb5fgt")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase32Lexical("21f7f8de-8051-5b89-8680-0195ef798b6a", "f6bvzlro2eawdh223mrrrn4ves")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase32Lexical("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "g5v6o4n357es2bm6yhjjgxq4av")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase32Lexical("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "h27zm7sntq5a5llaw3ka2iigjt")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase32Lexical("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "iaca5unig23uvxmdmurvd52i2s")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase32Lexical("ffffffff-ffff-ffff-ffff-ffffffffffff", "pzzzzzzzzzzzzzzzzzzzzzzzzz"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase58("00000000-0000-0000-0000-000000000000", "A111111111111111______A")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase58("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszL")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase58("C232AB00-9414-11EC-B3C8-9E6BDECED846", "B6S7oX73gv2Y1iSjVTT2c5L")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase58("000003e8-cbb9-21ea-b201-00045a86c8a1", "C11KtP6Y9P3rRkvh2N1e__L")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase58("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nJ")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase58("01867b2c-a0dd-459c-98d7-89e545538d6c", "E3UZ99RxxUJC1v4dWsYtb_J")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase58("21f7f8de-8051-5b89-8680-0195ef798b6a", "Fx7wEJfz9eb1TYzsrT7Zs_I")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase58("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "GrxRCnDiX4mxSpdi5LEvR_L")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase58("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H3RrXaX7uTM6qdwrXwpC6_J")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase58("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "I2QDDTZysWZ5jcKS6HJDmHI")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase58("ffffffff-ffff-ffff-ffff-ffffffffffff", "P8AQGAut7N92awznwCnjuQP")),
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testBase58("00000000-0000-f000-f000-00003fffffff", "P111111111112dtD34____P"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58_lexical() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase58Lexical("00000000-0000-0000-0000-000000000000", "A1111111111111111111112")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase58Lexical("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszV")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase58Lexical("C232AB00-9414-11EC-B3C8-9E6BDECED846", "B6S7oX73gv2Y1iSjVTT2c5V")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase58Lexical("000003e8-cbb9-21ea-b201-00045a86c8a1", "C1111KtP6Y9P3rRkvh2N1eV")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase58Lexical("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nT")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase58Lexical("01867b2c-a0dd-459c-98d7-89e545538d6c", "E13UZ99RxxUJC1v4dWsYtbT")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase58Lexical("21f7f8de-8051-5b89-8680-0195ef798b6a", "F1x7wEJfz9eb1TYzsrT7ZsS")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase58Lexical("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G1rxRCnDiX4mxSpdi5LEvRV")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase58Lexical("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H13RrXaX7uTM6qdwrXwpC6T")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase58Lexical("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "I2QDDTZysWZ5jcKS6HJDmHS")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase58Lexical("ffffffff-ffff-ffff-ffff-ffffffffffff", "P8AQGAut7N92awznwCnjuQZ")),
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testBase58Lexical("00000000-0000-f000-f000-00003fffffff", "P1111111111111112dtD34Z"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base64() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase64("00000000-0000-0000-0000-000000000000", "AAAAAAAAAAAAAAAAAAAAAA")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase64("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BymvkyMuvHqKrAARahsihL")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase64("C232AB00-9414-11EC-B3C8-9E6BDECED846", "BwjKrAJQUHsPInmvezthGL")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase64("000003e8-cbb9-21ea-b201-00045a86c8a1", "CAAAD6Mu5HqIBAARahsihL")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase64("3d813cbb-47fb-32ba-91df-831e1593ac29", "DPYE8u0f7K6Hfgx4Vk6wpJ")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase64("01867b2c-a0dd-459c-98d7-89e545538d6c", "EAYZ7LKDdWcjXieVFU41sJ")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase64("21f7f8de-8051-5b89-8680-0195ef798b6a", "FIff43oBRuJaAAZXveYtqI")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase64("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "GHslBTCMqsAPInmvezthGL")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase64("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "HAX8i4nmwzDjE3AwMBzmPJ")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase64("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "IMgw9TcwAdb7JMtX2kYHAI")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase64("ffffffff-ffff-ffff-ffff-ffffffffffff", "P____________________P"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_illegalInput() {
        return List.of(
                dynamicTest("base-64 one less than Nil", () -> testIllegalInput("AAAAAAAAAAAAAAAAAAAA@A")),
                dynamicTest("base-58 one less than Nil", () -> testIllegalInput("A11111111111111_______A")),

                dynamicTest("base-58 one more than Max", () -> testIllegalInput("P8AQGAut7N92awznwCnjuRP")),
                dynamicTest("base-58-lex one more than Max", () -> testIllegalInput("P8AQGAut7N92awznwCnjuRZ")),

                dynamicTest("base-58 many more than Max", () -> testIllegalInput("PZZZZZZZZZZZZZZZZZZZZZP")),
                dynamicTest("base-58-lex many more than Max", () -> testIllegalInput("PZZZZZZZZZZZZZZZZZZZZZP")),

                dynamicTest("base-64 one more than Max", () -> testIllegalInput("P___________________`P"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base64_lexical() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testBase64Lexical("00000000-0000-0000-0000-000000000000", "A--------------------2")),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase64Lexical("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BmajZmBij6e9f--GPWgXWV")),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testBase64Lexical("C232AB00-9414-11EC-B3C8-9E6BDECED846", "BkY9f-8FJ6gE7bajTnhW5V")),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase64Lexical("000003e8-cbb9-21ea-b201-00045a86c8a1", "C---2uBit6e70--GPWgXWV")),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase64Lexical("3d813cbb-47fb-32ba-91df-831e1593ac29", "DEN3wioUv9u6UVlsKZukdT")),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase64Lexical("01867b2c-a0dd-459c-98d7-89e545538d6c", "E-NOvA92SLRYMXTK4JspgT")),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase64Lexical("21f7f8de-8051-5b89-8680-0195ef798b6a", "F7UUsrc0Gi8P--OMjTNheS")),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testBase64Lexical("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G6g_0I1Beg-E7bajTnhW5V")),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testBase64Lexical("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H-MwXsbakn2Y3r-kB0naET")),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testBase64Lexical("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "IBVkxIRk-SQv8BhMqZN6-S")),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testBase64Lexical("ffffffff-ffff-ffff-ffff-ffffffffffff", "PzzzzzzzzzzzzzzzzzzzzZ"))
        );
    }


    private void testBase32(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actualString = UuidNCName.toBase32(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actualString);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }

    private void testBase32Lexical(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actualString = UuidNCName.toBase32Lex(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actualString);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }

    private static String toUpperCase(String uuidNcName) {
        // Changing the case of the first and the last characters must yield the same uuid
        StringBuffer b = new StringBuffer(uuidNcName);
        b.setCharAt(0, Character.toUpperCase(b.charAt(0)));
        b.setCharAt(b.length() - 1, Character.toUpperCase(b.charAt(b.length() - 1)));
        return b.toString();
    }

    private static String toLowerCase(String uuidNcName) {
        // Changing the case of the first and the last characters must yield the same uuid
        StringBuffer b = new StringBuffer(uuidNcName);
        b.setCharAt(0, Character.toLowerCase(b.charAt(0)));
        b.setCharAt(b.length() - 1, Character.toLowerCase(b.charAt(b.length() - 1)));
        return b.toString();
    }

    private void testBase58(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase58(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }

    private void testBase58Lexical(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase58Lex(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }


    private void testBase64(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase64(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }

    private void testIllegalInput(String inputString) {
        assertThrows(IllegalArgumentException.class, () -> UuidNCName.fromString(inputString));
    }

    private void testBase64Lexical(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase64Lexical(expectedUuid);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        UUID actualUuid3 = UuidNCName.fromString(canonicalString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }
}