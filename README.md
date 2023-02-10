# UuidNCName

UuidNCName converts [UUID][UUID-specification]s to/from valid NCName productions for use in (X|HT)
ML.

This is an implementation of the draft specification
["Compact UUIDs for Constrained Grammars"][UUID-NCName-specification]

This implementation supports 3 additional "lexical" formats.
The "lexical" formats have the same lexicographic order like the "canonical" UUID format
(when the Unicode character set and the same UUID version is used). This is useful for ids
that use
the [UUIDv6][UUIDv6-specification]
or [UUIDv7][UUIDv7-specification] format.

The supported formats are:

* `uuid-canonical`
* `uuid-ncname-32`
* `uuid-ncname-58`
* `uuid-ncname-64`
* `uuid-ncname-32-lex`
* `uuid-ncname-58-lex`
* `uuid-ncname-64-lex`

## Syntax

Here is the ABNF grammar for the supported productions:

<pre>
    <a id="uuid-canonical"/>uuid-canonical     = 8hexDigit "-" 4hexDigit "-" 4hexDigit "-" 4hexDigit "-" 12hexDigit ;
    
    <a id="uuid-ncname-32"/>uuid-ncname-32     = version 24base32                variant ;
    <a id="uuid-ncname-58"/>uuid-ncname-58     = version 15*21base58 *6padding-u variant ;
    <a id="uuid-ncname-64"/>uuid-ncname-64     = version 20base64-url            variant ;

    <a id="uuid-ncname-32-lex"/>uuid-ncname-32-lex = version 24base32-lex            variant-lex ;
    <a id="uuid-ncname-58-lex"/>uuid-ncname-58-lex = version *6padding-o 15*21base58 variant-lex ;
    <a id="uuid-ncname-64-lex"/>uuid-ncname-64-lex = version 20base64-lex            variant-lex ;

    <a id="hexDigit"/>hexDigit       = %x30-39 / %x41-46 / %x61-66 ; [0-9A-Fa-f]

    version        = bookend ;
    variant        = bookend ;
    variant-lex    = bookend-lex ;
    <a id="bookend"/>bookend        = %x41-50 / %x61-70 ; [A-Pa-p]
    <a id="bookend-lex"/>bookend-lex    = %x32-37 / %x51-5A / %x71-7A ; [2-7Q-Zq-z]
    padding-u      = "_" ;
    padding-o      = "1" ;
    
    <a id="base32"/>base32         = %x41-5a / %x61-7a / %x32-37 ; [A-Za-z2-7]
    <a id="base32-lex"/>base32-lex     = %x32-37 / %x41-5a / %x61-7a ; [2-7A-Za-z]

    <a id="base58"/>base58         = %x31-39 / %x41-48 / %x4a-4e / %x50-5a / %x61-6c / %x6d-7a ; [1-9A-HJ-NP-Za-km-z]

    <a id="base64-url"/>base64-url     = %x30-39 / %x41-5a / %x61-7a / %x2d / %x5f ; [A-Z_a-z0-9\-_]
    <a id="base64-lex"/>base64-lex     = %x2d / %x30-39 / %x41-5a / %x5f / %x61-7a ; [-0-9A-Z_a-z]
</pre>

## Detection Heuristic

All encodings are fixed length:

* [`uuid-canonical`](#uuid-canonical) is 36 characters; contains 4 dashes.<br><br>

* [`uuid-ncname-32`](#uuid-ncname-32) is 26 characters; starts and ends with [`bookend`](#bookend).
* [`uuid-ncname-58`](#uuid-ncname-58) is 23 characters; starts and ends with [`bookend`](#bookend).
* [`uuid-ncName-64`](#uuid-ncname-64) is 22 characters; starts and ends with [`bookend`](#bookend).<br><br>

* [`uuid-ncname-32-lex`](#uuid-ncname-32-lex) is 26 characters; starts with [`bookend`](#bookend); ends
  with [`bookend-lex`](#bookend-lex).
* [`uuid-ncname-58-lex`](#uuid-ncname-58-lex) is 23 characters; starts with [`bookend`](#bookend); ends
  with [`bookend-lex`](#bookend-lex).
* [`uuid-ncName-64-lex`](#uuid-ncname-64-lex) is 22 characters; starts with [`bookend`](#bookend); ends
  with [`bookend-lex`](#bookend-lex).

## Algorithms

### Extract Fields from an UUID

An <a id="UUID"/>`UUID` is a 128-bit unsigned integer in big-endian order.<br>

It consists of the fields `version`, `variant` and `data`.<br>
The `version` and `variant` fields are interspersed in the `data` field.

1. `version` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_f000_0000_000000000000 from the UUID,
   and compress them into 4 bits.
2. `variant` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_0000_f000_000000000000 from the UUID,
   and compress them into 4 bits.
3. `data` is a 120-bit unsigned integer in big-endian order.<br>
   Extract bits 0xffffffff_ffff_0fff_0fff_ffffffffffff from the UUID,
   and compress them into 120 bits.

### Format uuid-canonical

1. `uuid-digits :=` Convert the 128-bit `UUID` from binary to base-16.<br>
   This yields 32 digits (padded with leading zeroes).
2. `uuid-chars :=` Encode each `uuid-digits` digit with a [`hexDigit`](#hexDigit) character.
3. `uuid-canonical :=` Insert a '-' character after `uuid-chars` at 8, 12, 16 and 24.

### Format uuid-ncname-32

1. `version,variant,data :=` Extract fields from the 128-bit `UUID`.
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend`](#bookend) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-32.<br>
   This yields 24 digits (padded with leading zeroes).
5. `data-chars :=` Encode each `data-digits` digit with a [`base32`](#base32) character.
6. `uuid-ncname-32 :=` Concat `version-char`, `data-chars`, `variant-char`.

### Format uuid-ncname-58

1. `version,variant,data :=` Extract fields from the 128-bit `UUID`.
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend`](#bookend) character.
4. `data-chars :=` Encode the 120-bit `data` field with the
   [Base58 encoding algorithm][base58-encoding-algorithm].<br>
   This yields 15 to 21 characters.
5. `data-chars-padded :=` Pad the `data-chars` with trailing '_' chars
   to reach a total length of 21 characters.
6. `uuid-ncname-58 :=` Concat `version-char`, `data-chars-padded`, `variant-char`.

### Format uuid-ncname-64

1. `version,variant,data :=` Extract fields from the 128-bit `UUID`.
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend`](#bookend) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-64.<br>
   This yields 20 digits (padded with leading zeroes).
5. `data-chars :=` Encode each `data-digits` digit with a [`base64-url`](#base64-url) character.
6. `uuid-ncname-32 :=` Concat `version-char`, `data-chars`, `variant-char`.

### Format uuid-ncname-32-lex

1. `version,variant,data :=` Extract fields from the `UUID`
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend-lex`](#bookend-lex) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-32.<br>
   This yields 24 digits (padded with leading zeroes).
5. `data-chars :=` Encode each digit with a [`base32-lex`](#base32-lex) character.
6. `uuid-ncname-32 :=` Concat `version-char`, `data-chars`, `variant-char`.

### Format uuid-ncname-58-lex

1. `version,variant,data :=` Extract fields from the `UUID`
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend-lex`](#bookend-lex) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-58.<br>
   This yields 21 digits (padded with leading zeroes).
5. `data-chars :=` Encode each digit with a [`base58`](#base58) character.
6. `uuid-ncname-58 :=` Concat `version-char`, `data-chars`, `variant-char`.

### Format uuid-ncname-64-lex

1. `version,variant,data :=` Extract fields from the `UUID`
2. `version-char :=` Encode `version` with a [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a [`bookend-lex`](#bookend-lex) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-64.<br>
   This yields 20 digits (padded with leading zeroes).
5. `data-chars :=` Encode each digit with a [`base64-lex`](#base64-lex) character.
6. `uuid-ncname-32 :=` Concat `version-char`, `data-chars`, `variant-char`.

## Examples

| Version      | uuid-canonical                       |
|--------------|--------------------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a |
| 6 Timestamp  | 1EC9414C-232A-6B00-B3C8-9E6BDECED846 |
| 7 Timestamp  | 017F22E2-79B0-7CC3-98C4-DC0C0C07398F |
| 8 Vendor     | 320C3D4D-CC00-875B-8EC9-32D5F69181C0 |
| 15, Max      | ffffffff-ffff-ffff-ffff-ffffffffffff |

| Version      | uuid-ncname-32             | uuid-ncname-58          | uuid-ncname-64         |
|--------------|----------------------------|-------------------------|------------------------|
| 0, Nil       | aaaaaaaaaaaaaaaaaaaaaaaaaa | A111111111111111______A | AAAAAAAAAAAAAAAAAAAAAA |
| 1, Timestamp | bzjv6jsglv4pkfkyaarninsfbl | B6fTkmTD22KpWbDq1LuiszL | BymvkyMuvHqKrAARahsihL |
| 2, DCE       | caaaah2glxepkeaiaarninsfbl | C11KtP6Y9P3rRkvh2N1e__L | CAAAD6Mu5HqIBAARahsihL |
| 3, MD5       | dhwatzo2h7mv2dx4ddykzhlbjj | D2ioV6oTr9yq6dMojd469nJ | DPYE8u0f7K6Hfgx4Vk6wpJ |
| 4, Random    | eagdhwlfa3vm4rv4j4vcvhdlmj | E3UZ99RxxUJC1v4dWsYtb_J | EAYZ7LKDdWcjXieVFU41sJ |
| 5, SHA-1     | feh37rxuakg4jnaabsxxxtc3ki | Fx7wEJfz9eb1TYzsrT7Zs_I | FIff43oBRuJaAAZXveYtqI |
| 6 Timestamp  | gd3euctbdfkyahse6nppm5wcgl | GrxRCnDiX4mxSpdi5LEvR_L | GHslBTCMqsAPInmvezthGL |
| 7 Timestamp  | haf7sfytzwdgdrrg4bqgaoompj | H3RrXaX7uTM6qdwrXwpC6_J | HAX8i4nmwzDjE3AwMBzmPJ |
| 8 Vendor     | igigd2tomab235sjs2x3jdaoai | I2QDDTZysWZ5jcKS6HJDmHI | IMgw9TcwAdb7JMtX2kYHAI |
| 15, Max      | p777777777777777777777777p | P8AQGAut7N92awznwCnjuQP | P____________________P |

| Version      | uuid-ncname-32-lex         | uuid-ncname-58-lex      | uuid-ncname-64-lex     |
|--------------|----------------------------|-------------------------|------------------------|
| 0, Nil       | a2222222222222222222222222 | A1111111111111111111112 | A--------------------2 |
| 1, Timestamp | btdpydmafpwje7es22lhchm73v | B6fTkmTD22KpWbDq1LuiszV | BmajZmBij6e9f--GPWgXWV |
| 2, DCE       | c2222buafr6je62c22lhchm73v | C1111KtP6Y9P3rRkvh2N1eV | C---2uBit6e70--GPWgXWV |
| 3, MD5       | dbq2ntiubzgpu5rw55setbf3dt | D2ioV6oTr9yq6dMojd469nT | DEN3wioUv9u6UVlsKZukdT |
| 4, Random    | e2a5bqf72vpgwlpwdwp4pb5fgt | E13UZ99RxxUJC1v4dWsYtbT | E-NOvA92SLRYMXTK4JspgT |
| 5, SHA-1     | f6bvzlro2eawdh223mrrrn4ves | F1x7wEJfz9eb1TYzsrT7ZsS | F7UUsrc0Gi8P--OMjTNheS |
| 6 Timestamp  | g5v6o4n357es2bm6yhjjgxq4av | G1rxRCnDiX4mxSpdi5LEvRV | G6g_0I1Beg-E7bajTnhW5V |
| 7 Timestamp  | h27zm7sntq5a5llaw3ka2iigjt | H13RrXaX7uTM6qdwrXwpC6T | H-MwXsbakn2Y3r-kB0naET |
| 8 Vendor     | iaca5unig23uvxmdmurvd52i2s | I2QDDTZysWZ5jcKS6HJDmHS | IBVkxIRk-SQv8BhMqZN6-S |
| 15, Max      | pzzzzzzzzzzzzzzzzzzzzzzzzz | P8AQGAut7N92awznwCnjuQZ | PzzzzzzzzzzzzzzzzzzzzZ |

[UUID-specification]: https://www.rfc-editor.org/rfc/rfc4122

[UUIDv6-specification]: https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-6

[UUIDv7-specification]: https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-7

[UUID-NCName-specification]: https://www.ietf.org/id/draft-taylor-uuid-ncname-03.html

[base58-encoding-algorithm]: https://datatracker.ietf.org/doc/html/draft-msporny-base58-02#section-3
