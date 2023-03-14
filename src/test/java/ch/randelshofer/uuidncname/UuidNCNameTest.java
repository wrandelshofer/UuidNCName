/*
 * @(#)UuidNCNameTest.java
 * Copyright © 2023 Werner Randelshofer, Switzerland. MIT License.
 */

package ch.randelshofer.uuidncname;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class UuidNCNameTest {
    private static String toLowerCase(String uuidNcName) {
        // Changing the case of the first and the last characters must yield the same uuid
        StringBuffer b = new StringBuffer(uuidNcName);
        b.setCharAt(0, Character.toLowerCase(b.charAt(0)));
        b.setCharAt(b.length() - 1, Character.toLowerCase(b.charAt(b.length() - 1)));
        return b.toString();
    }

    private static String toUpperCase(String uuidNcName) {
        // Changing the case of the first and the last characters must yield the same uuid
        StringBuffer b = new StringBuffer(uuidNcName);
        b.setCharAt(0, Character.toUpperCase(b.charAt(0)));
        b.setCharAt(b.length() - 1, Character.toUpperCase(b.charAt(b.length() - 1)));
        return b.toString();
    }

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
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "p777777777777777777777777p", UuidFormat.BASE32)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("p77777777777777777777777äp"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base32_lex() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "a0000000000000000000000002", UuidFormat.BASE32_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "bp9lu9i6blsfapam004ba3ch8t", UuidFormat.BASE32_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "bo8pam04k2gfcpsh7jbrr7dghu", UuidFormat.BASE32_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "c00007q6bn4fap02004ba3ch8t", UuidFormat.BASE32_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "d7m0jpeq7vclq8tv0ou2m9qoat", UuidFormat.BASE32_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "e0637mb50rlcscdf2f58l9oqrs", UuidFormat.BASE32_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "f47rvhnk0a6s93800clttsomqu", UuidFormat.BASE32_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "g3r4k2j135ao0psh7jbrr7dghu", UuidFormat.BASE32_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "h05vi5ojpm363cc9n0c1g3jj3v", UuidFormat.BASE32_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "i6863qjec01qr7cicmluq8o3gs", UuidFormat.BASE32_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "pvvvvvvvvvvvvvvvvvvvvvvvvz", UuidFormat.BASE32_LEX)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("pzzzzzzzzzzzzzzzzzzzzzzzäz"))
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
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testUuidNCName("00000000-0000-f000-f000-00003fffffff", "P111111111112dtD34____P", UuidFormat.BASE58)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("P111111111112dtD3ä____P"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58_lex() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "A1111111111111111111112", UuidFormat.BASE58_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KrBbQ6F3diU7T", UuidFormat.BASE58_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "B6S7oX73gv2ZYbnsdVG3mzU", UuidFormat.BASE58_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "C1111KtP6Y9QogqndZahf5T", UuidFormat.BASE58_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yqXobWp5RXvqT", UuidFormat.BASE58_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "E13UZ99RxxUHkVGJNDLJV8S", UuidFormat.BASE58_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "F1x7wEJfz9eaCAWiLtSNd7U", UuidFormat.BASE58_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G1rxRCnDiX4oVLAmr79G6LU", UuidFormat.BASE58_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H13RrXaX7uTLfqn6haaYcaV", UuidFormat.BASE58_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "I2QDDTZysWZ3t2b7UGbWyDS", UuidFormat.BASE58_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "P8AQGAut7N92awznwCnjuQZ", UuidFormat.BASE58_LEX)),
                dynamicTest("15 XXX,      00000000-0000-f000-f000-00003fffffff", () -> testUuidNCName("00000000-0000-f000-f000-00003fffffff", "P111111111131R5AhEe6iiZ", UuidFormat.BASE58_LEX)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("P111111111131R5AhEe6iäZ"))
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
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "P____________________P", UuidFormat.BASE64)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("P___________________äP"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base64_lexical() {
        return List.of(
                dynamicTest("0 Nil,       00000000-0000-0000-0000-000000000000", () -> testUuidNCName("00000000-0000-0000-0000-000000000000", "A--------------------2", UuidFormat.BASE64_LEX)),
                dynamicTest("1 Timestamp, ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testUuidNCName("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BmajZmBij6emek-3LcQ7cT", UuidFormat.BASE64_LEX)),
                dynamicTest("1 Timestamp, C232AB00-9414-11EC-B3C8-9E6BDECED846", () -> testUuidNCName("C232AB00-9414-11EC-B3C8-9E6BDECED846", "BkY9f-8FJ6gnm8tfrgvNGU", UuidFormat.BASE64_LEX)),
                dynamicTest("2 DCE,       000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testUuidNCName("000003e8-cbb9-21ea-b201-00045a86c8a1", "C---2uBit6em-F-3LcQ7cT", UuidFormat.BASE64_LEX)),
                dynamicTest("3 MD5,       3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testUuidNCName("3d813cbb-47fb-32ba-91df-831e1593ac29", "DEN3wioUv9uGrsBT4ODg9T", UuidFormat.BASE64_LEX)),
                dynamicTest("4 Random,    01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testUuidNCName("01867b2c-a0dd-459c-98d7-89e545538d6c", "E-NOvA92SLRNpsb_GKDCQS", UuidFormat.BASE64_LEX)),
                dynamicTest("5 SHA-1,     21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testUuidNCName("21f7f8de-8051-5b89-8680-0195ef798b6a", "F7UUsrc0Gi85V-5KvraAPU", UuidFormat.BASE64_LEX)),
                dynamicTest("6 Timestamp, 1EC9414C-232A-6B00-B3C8-9E6BDECED846", () -> testUuidNCName("1EC9414C-232A-6B00-B3C8-9E6BDECED846", "G6g_0I1Beg-nm8tfrgvNGU", UuidFormat.BASE64_LEX)),
                dynamicTest("7 Timestamp, 017F22E2-79B0-7CC3-98C4-DC0C0C07398F", () -> testUuidNCName("017F22E2-79B0-7CC3-98C4-DC0C0C07398F", "H-MwXsbakn2NlCkB2-RtYV", UuidFormat.BASE64_LEX)),
                dynamicTest("8 Custom,    320C3D4D-CC00-875B-8EC9-32D5F69181C0", () -> testUuidNCName("320C3D4D-CC00-875B-8EC9-32D5F69181C0", "IBVkxIRk-SQDmIAKxd50kS", UuidFormat.BASE64_LEX)),
                dynamicTest("15 Max,      ffffffff-ffff-ffff-ffff-ffffffffffff", () -> testUuidNCName("ffffffff-ffff-ffff-ffff-ffffffffffff", "PzzzzzzzzzzzzzzzzzzzzZ", UuidFormat.BASE64_LEX)),
                dynamicTest("Illegal", () -> testIllegalUuidNCName("PzzzzzzzzzzzzzzzzzzzäZ"))
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

    private void testIllegalInput(String inputString) {
        assertThrows(IllegalArgumentException.class, () -> UuidNCName.fromString(inputString));
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

    private void testIllegalUuidNCName(String illegalInputString) {
        assertThrows(IllegalArgumentException.class, () -> UuidNCName.fromString(illegalInputString));
    }
}