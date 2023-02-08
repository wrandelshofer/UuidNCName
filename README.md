# UuidNCName

UuidNCName converts [UUID](https://www.rfc-editor.org/rfc/rfc4122)s to/from valid NCName productions for use in (X|HT)
ML.

This is an implementation of the draft specification "Compact UUIDs for Constrained Grammars"
https://www.ietf.org/id/draft-taylor-uuid-ncname-03.html

This implementation supports 3 additional "lexical" formats.
The "lexical" formats have the same lexicographic order like the "canonical" UUID format
(when the Unicode character set and the same UUID version is used). This is useful for ids
that use
the [UUIDv6](https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-6)
or [UUIDv7](https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-6) format.

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

    uuid-canonical     = 8hexDigit "-" 4hexDigit "-" 4hexDigit "-" 12hexDigit
    
    uuid-ncname-32     = bookend 24base32                bookend
    uuid-ncname-58     = bookend 15*21base58 *6padding-u bookend
    uuid-ncname-64     = bookend 20base64-url            bookend

    uuid-ncname-32-lex = bookend 24base32-lex            bookend-lex
    uuid-ncname-58-lex = bookend *6padding-o 15*21base58 bookend-lex
    uuid-ncname-64-lex = bookend 20base64-lex            bookend-lex

    hexDigit       = %x30-39 / %x41-46 / %x61-66 ; [0-9A-Fa-f]

    bookend        = %x41-50 / %x61-70 ; [A-Pa-p]
    bookend-lex    = %x32-37 / %x51-5A / %x71-7A ; [2-7Q-Zq-z]
    padding-u      = "_" ;
    padding-o      = "1" ;
    
    base32         = %x41-5a / %x61-7a / %x32-37 ; [A-Za-z2-7]
    base32-lex     = %x32-37 / %x41-5a / %x61-7a ; [2-7A-Za-z]

    base58         = %x31-39 / %x41-48 / %x4a-4e / %x50-5a / %x61-6c / %x6d-7a ; [1-9A-HJ-NP-Za-km-z]

    base64-url     = %x30-39 / %x41-5a / %x61-7a / %x2d / %x5f ; [A-Z_a-z0-9\-_]
    base64-lex     = %x2d / %x30-39 / %x41-5a / %x5f / %x61-7a ; [-0-9A-Z_a-z]

## Detection Heuristic

All encodings are fixed length:

* `uuid-canonical` is always 36 characters; contains 4 dashes.<br><br>

* `uuid-ncname-32` is always 26 characters; starts and ends with `bookend`.
* `uuid-ncname-58` is always 23 characters; starts and ends with `bookend`;
  the `base58` digits are padded to the right with '_' characters.
* `uuid-ncName-64` is always 22 characters; starts and ends with `bookend`.<br><br>

* `uuid-ncname-32-lex` is always 26 characters; starts with `bookend` and ends with `bookend-lex`.
* `uuid-ncname-58-lex` is always 23 characters; starts with `bookend` and ends with `bookend-lex`;
  the `base58` digits are padded to the left with '1' characters.
* `uuid-ncName-64-lex` is always 22 characters; starts with `bookend` and ends with `bookend-lex`.

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
