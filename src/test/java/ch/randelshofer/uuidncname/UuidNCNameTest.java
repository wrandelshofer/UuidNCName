package ch.randelshofer.uuidncname;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class UuidNCNameTest {
    @TestFactory
    public List<DynamicTest> dynamicTests_base32() {
        return List.of(
                dynamicTest("00000000-0000-0000-0000-000000000000", () -> testBase32("00000000-0000-0000-0000-000000000000", "aaaaaaaaaaaaaaaaaaaaaaaaaa")),
                dynamicTest("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase32("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "bzjv6jsglv4pkfkyaarninsfbl")),
                dynamicTest("000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase32("000003e8-cbb9-21ea-b201-00045a86c8a1", "caaaah2glxepkeaiaarninsfbl")),
                dynamicTest("3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase32("3d813cbb-47fb-32ba-91df-831e1593ac29", "dhwatzo2h7mv2dx4ddykzhlbjj")),
                dynamicTest("01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase32("01867b2c-a0dd-459c-98d7-89e545538d6c", "eagdhwlfa3vm4rv4j4vcvhdlmj")),
                dynamicTest("21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase32("21f7f8de-8051-5b89-8680-0195ef798b6a", "feh37rxuakg4jnaabsxxxtc3ki"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58() {
        return List.of(
                dynamicTest("00000000-0000-0000-0000-000000000000", () -> testBase58("00000000-0000-0000-0000-000000000000", "A111111111111111______A")),
                dynamicTest("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase58("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszL")),
                dynamicTest("000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase58("000003e8-cbb9-21ea-b201-00045a86c8a1", "C11KtP6Y9P3rRkvh2N1e__L")),
                dynamicTest("3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase58("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nJ")),
                dynamicTest("01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase58("01867b2c-a0dd-459c-98d7-89e545538d6c", "E3UZ99RxxUJC1v4dWsYtb_J")),
                dynamicTest("21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase58("21f7f8de-8051-5b89-8680-0195ef798b6a", "Fx7wEJfz9eb1TYzsrT7Zs_I"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58Lexical() {
        return List.of(
                dynamicTest("00000000-0000-0000-0000-000000000000", () -> testBase58New("00000000-0000-0000-0000-000000000000", "A111111111111111111111A")),
                dynamicTest("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase58New("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszL")),
                dynamicTest("000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase58New("000003e8-cbb9-21ea-b201-00045a86c8a1", "C1111KtP6Y9P3rRkvh2N1eL")),
                dynamicTest("3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase58New("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nJ")),
                dynamicTest("01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase58New("01867b2c-a0dd-459c-98d7-89e545538d6c", "E13UZ99RxxUJC1v4dWsYtbJ")),
                dynamicTest("21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase58New("21f7f8de-8051-5b89-8680-0195ef798b6a", "F1x7wEJfz9eb1TYzsrT7ZsI"))
        );
    }

    @TestFactory
    public List<DynamicTest> dynamicTests_base58_lexical() {
        return List.of(
                dynamicTest("00000000-0000-0000-0000-000000000000", () -> testBase58Lexical("00000000-0000-0000-0000-000000000000", "A111111111111111111111A")),
                dynamicTest("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase58Lexical("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "B6fTkmTD22KpWbDq1LuiszL")),
                dynamicTest("000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase58Lexical("000003e8-cbb9-21ea-b201-00045a86c8a1", "C1111KtP6Y9P3rRkvh2N1eL")),
                dynamicTest("3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase58Lexical("3d813cbb-47fb-32ba-91df-831e1593ac29", "D2ioV6oTr9yq6dMojd469nJ")),
                dynamicTest("01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase58Lexical("01867b2c-a0dd-459c-98d7-89e545538d6c", "E13UZ99RxxUJC1v4dWsYtbJ")),
                dynamicTest("21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase58Lexical("21f7f8de-8051-5b89-8680-0195ef798b6a", "F1x7wEJfz9eb1TYzsrT7ZsI"))
        );
    }
    @TestFactory
    public List<DynamicTest> dynamicTests_base64() {
        return List.of(
                dynamicTest("00000000-0000-0000-0000-000000000000", () -> testBase64("00000000-0000-0000-0000-000000000000", "AAAAAAAAAAAAAAAAAAAAAA")),
                dynamicTest("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", () -> testBase64("ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1", "BymvkyMuvHqKrAARahsihL")),
                dynamicTest("000003e8-cbb9-21ea-b201-00045a86c8a1", () -> testBase64("000003e8-cbb9-21ea-b201-00045a86c8a1", "CAAAD6Mu5HqIBAARahsihL")),
                dynamicTest("3d813cbb-47fb-32ba-91df-831e1593ac29", () -> testBase64("3d813cbb-47fb-32ba-91df-831e1593ac29", "DPYE8u0f7K6Hfgx4Vk6wpJ")),
                dynamicTest("01867b2c-a0dd-459c-98d7-89e545538d6c", () -> testBase64("01867b2c-a0dd-459c-98d7-89e545538d6c", "EAYZ7LKDdWcjXieVFU41sJ")),
                dynamicTest("21f7f8de-8051-5b89-8680-0195ef798b6a", () -> testBase64("21f7f8de-8051-5b89-8680-0195ef798b6a", "FIff43oBRuJaAAZXveYtqI"))
        );
    }






    private void testBase32(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actualString = UuidNCName.toBase32(expectedUuid);
        UUID actualUuid =    UuidNCName.fromBase32(expectedString);
        UUID actualUuid2 =    UuidNCName.fromString(expectedString);
        UUID actualUuid3 =    UuidNCName.fromString(canonicalString);
        assertEquals(expectedString,actualString);
        assertEquals(expectedUuid,actualUuid);
        assertEquals(expectedUuid,actualUuid2);
        assertEquals(expectedUuid, actualUuid3);
    }

    private void testBase58(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase58(expectedUuid);
        UUID actualUuid = UuidNCName.fromBase58(expectedString);
        UUID actualUuid2 = UuidNCName.fromString(expectedString);
        UUID actualUuid3 = UuidNCName.fromString(canonicalString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, actualUuid2);
        assertEquals(expectedUuid, actualUuid3);
    }

    private void testBase58New(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase58Fast(expectedUuid);
        UUID actualUuid = UuidNCName.fromBase58(expectedString);
        UUID actualUuid2 = UuidNCName.fromString(expectedString);
        UUID actualUuid3 = UuidNCName.fromString(canonicalString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid, actualUuid);
        assertEquals(expectedUuid, actualUuid2);
        assertEquals(expectedUuid, actualUuid3);
    }

    private void testBase58Lexical(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase58Lexical(expectedUuid);
        UUID actualUuid = UuidNCName.fromBase58Lexical(expectedString);
        UUID actualUuid2 = UuidNCName.fromString(expectedString);
        UUID actualUuid3 = UuidNCName.fromString(canonicalString);
        assertEquals(expectedString, actual);
        assertEquals(expectedUuid,actualUuid);
        assertEquals(expectedUuid,actualUuid2);
        assertEquals(expectedUuid,actualUuid3);
    }
    private void testBase64(String canonicalString, String expectedString) {
        UUID expectedUuid = UUID.fromString(canonicalString);
        String actual = UuidNCName.toBase64(expectedUuid);
        UUID actualUuid =    UuidNCName.fromBase64(expectedString);
        UUID actualUuid2 =    UuidNCName.fromString(expectedString);
        UUID actualUuid3 =    UuidNCName.fromString(canonicalString);
        assertEquals(expectedString,actual);
        assertEquals(expectedUuid,actualUuid);
        assertEquals(expectedUuid,actualUuid2);
        assertEquals(expectedUuid,actualUuid3);
    }
}