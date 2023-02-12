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
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "aaaaaaaaaaaaaaaaaaaaaaaaaa", UuidFormat.BASE32)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "bzjv6jsglv4pkfkyaarninsfbl", UuidFormat.BASE32)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "byizkwaeucqpmhse6nppm5wcgl", UuidFormat.BASE32)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "caaaah2glxepkeaiaarninsfbl", UuidFormat.BASE32)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "dhwatzo2h7mv2dx4ddykzhlbjj", UuidFormat.BASE32)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "eagdhwlfa3vm4rv4j4vcvhdlmj", UuidFormat.BASE32)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "feh37rxuakg4jnaabsxxxtc3ki", UuidFormat.BASE32)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "gd3euctbdfkyahse6nppm5wcgl", UuidFormat.BASE32)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "haf7sfytzwdgdrrg4bqgaoompj", UuidFormat.BASE32)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "igigd2tomab235sjs2x3jdaoai", UuidFormat.BASE32)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "p777777777777777777777777p", UuidFormat.BASE32))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base32_lex() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "a2222222222222222222222222", UuidFormat.BASE32_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "btdpydmafpwje7es22lhchm73v", UuidFormat.BASE32_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "bscteq26o4kjgbm6yhjjgxq4av", UuidFormat.BASE32_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "c2222buafr6je62c22lhchm73v", UuidFormat.BASE32_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "dbq2ntiubzgpu5rw55setbf3dt", UuidFormat.BASE32_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "e2a5bqf72vpgwlpwdwp4pb5fgt", UuidFormat.BASE32_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "f6bvzlro2eawdh223mrrrn4ves", UuidFormat.BASE32_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "g5v6o4n357es2bm6yhjjgxq4av", UuidFormat.BASE32_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "h27zm7sntq5a5llaw3ka2iigjt", UuidFormat.BASE32_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "iaca5unig23uvxmdmurvd52i2s", UuidFormat.BASE32_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "pzzzzzzzzzzzzzzzzzzzzzzzzz", UuidFormat.BASE32_LEX))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "A111111111111111______A", UuidFormat.BASE58)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszL", UuidFormat.BASE58)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "B6S7oX73gv2Y1iSjVTT2c5L", UuidFormat.BASE58)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "C11KtP6Y9P3rRkvh2N1e__L", UuidFormat.BASE58)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nJ", UuidFormat.BASE58)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "E3UZ99RxxUJC1v4dWsYtb_J", UuidFormat.BASE58)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "Fx7wEJfz9eb1TYzsrT7Zs_I", UuidFormat.BASE58)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "GrxRCnDiX4mxSpdi5LEvR_L", UuidFormat.BASE58)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H3RrXaX7uTM6qdwrXwpC6_J", UuidFormat.BASE58)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "I2QDDTZysWZ5jcKS6HJDmHI", UuidFormat.BASE58)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "P8AQGAut7N92awznwCnjuQP", UuidFormat.BASE58)),
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testUuidNCName("00000000-0000-f000-f000-00003fffffff", "P111111111112dtD34____P", UuidFormat.BASE58))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58_lex() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "A1111111111111111111112", UuidFormat.BASE58_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszV", UuidFormat.BASE58_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "B6S7oX73gv2Y1iSjVTT2c5V", UuidFormat.BASE58_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "C1111KtP6Y9P3rRkvh2N1eV", UuidFormat.BASE58_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nT", UuidFormat.BASE58_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "E13UZ99RxxUJC1v4dWsYtbT", UuidFormat.BASE58_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "F1x7wEJfz9eb1TYzsrT7ZsS", UuidFormat.BASE58_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G1rxRCnDiX4mxSpdi5LEvRV", UuidFormat.BASE58_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H13RrXaX7uTM6qdwrXwpC6T", UuidFormat.BASE58_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "I2QDDTZysWZ5jcKS6HJDmHS", UuidFormat.BASE58_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "P8AQGAut7N92awznwCnjuQZ", UuidFormat.BASE58_LEX)),
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testUuidNCName("00000000-0000-f000-f000-00003fffffff", "P1111111111111112dtD34Z", UuidFormat.BASE58_LEX))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base64() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "AAAAAAAAAAAAAAAAAAAAAA", UuidFormat.BASE64)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BymvkyMuvHqKrAARahsihL", UuidFormat.BASE64)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "BwjKrAJQUHsPInmvezthGL", UuidFormat.BASE64)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "CAAAD6Mu5HqIBAARahsihL", UuidFormat.BASE64)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "DPYE8u0f7K6Hfgx4Vk6wpJ", UuidFormat.BASE64)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "EAYZ7LKDdWcjXieVFU41sJ", UuidFormat.BASE64)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "FIff43oBRuJaAAZXveYtqI", UuidFormat.BASE64)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "GHslBTCMqsAPInmvezthGL", UuidFormat.BASE64)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "HAX8i4nmwzDjE3AwMBzmPJ", UuidFormat.BASE64)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "IMgw9TcwAdb7JMtX2kYHAI", UuidFormat.BASE64)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "P____________________P", UuidFormat.BASE64))
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
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "A--------------------2", UuidFormat.BASE64_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BmajZmBij6e9f--GPWgXWV", UuidFormat.BASE64_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "BkY9f-8FJ6gE7bajTnhW5V", UuidFormat.BASE64_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "C---2uBit6e70--GPWgXWV", UuidFormat.BASE64_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "DEN3wioUv9u6UVlsKZukdT", UuidFormat.BASE64_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "E-NOvA92SLRYMXTK4JspgT", UuidFormat.BASE64_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "F7UUsrc0Gi8P--OMjTNheS", UuidFormat.BASE64_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G6g_0I1Beg-E7bajTnhW5V", UuidFormat.BASE64_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H-MwXsbakn2Y3r-kB0naET", UuidFormat.BASE64_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "IBVkxIRk-SQv8BhMqZN6-S", UuidFormat.BASE64_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "PzzzzzzzzzzzzzzzzzzzzZ", UuidFormat.BASE64_LEX))
        );
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

    private void testUuidNCName(String canonicalString, String expectedString, UuidFormat format) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toString(expectedUuid, format);
        UUID actualUuid = UuidNCName.fromString(expectedString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, UuidNCName.fromString(toUpperCase(expectedString)));
        assertEquals(expectedUuid, UuidNCName.fromString(toLowerCase(expectedString)));
    }

    private void testIllegalInput(String inputString) {
        assertThrows(IllegalArgumentException.class, () -> UuidNCName.fromString(inputString));
    }
}