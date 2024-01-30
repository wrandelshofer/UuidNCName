# UuidNCName

UuidNCName converts [UUID][UUID-specification]s to/from valid NCName productions for use in (X|HT)
ML.

This is an implementation of the draft specification
["Compact UUIDs for Constrained Grammars"][UUID-NCName-specification].

This implementation supports 3 additional "lexical" formats.
The "lexical" formats have the same lexicographic order like the "canonical" UUID format,
when the following conditions are true:

- Unicode or ASCII character set is used.
- All UUIDs have the same version and variant.

This is useful for ids that use the [UUIDv6][UUIDv6-specification] or [UUIDv7][UUIDv7-specification]
format.

The supported formats are:

* `uuid-canonical`
* `uuid-ncname-32`
* `uuid-ncname-58`
* `uuid-ncname-64`
* `uuid-ncname-32-lex`
* `uuid-ncname-58-lex`
* `uuid-ncname-64-lex`

## Examples

| Version      | uuid-canonical                         |
|--------------|----------------------------------------|
| 0, Nil       | `00000000-0000-0000-0000-000000000000` |
| 1, Timestamp | `ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1` |
| 2, DCE       | `000003e8-cbb9-21ea-b201-00045a86c8a1` |
| 3, MD5       | `3d813cbb-47fb-32ba-91df-831e1593ac29` |
| 4, Random    | `01867b2c-a0dd-459c-98d7-89e545538d6c` |
| 5, SHA-1     | `21f7f8de-8051-5b89-8680-0195ef798b6a` |
| 6, Timestamp | `1EC9414C-232A-6B00-B3C8-9E6BDECED846` |
| 7, Timestamp | `017F22E2-79B0-7CC3-98C4-DC0C0C07398F` |
| 8, Custom    | `320C3D4D-CC00-875B-8EC9-32D5F69181C0` |
| 15, Max      | `ffffffff-ffff-ffff-ffff-ffffffffffff` |

| Version      | uuid-ncname-32               | uuid-ncname-58            | uuid-ncname-64           |
|--------------|------------------------------|---------------------------|--------------------------|
| 0, Nil       | `aaaaaaaaaaaaaaaaaaaaaaaaaa` | `A111111111111111______A` | `AAAAAAAAAAAAAAAAAAAAAA` |
| 1, Timestamp | `bzjv6jsglv4pkfkyaarninsfbl` | `B6fTkmTD22KpWbDq1LuiszL` | `BymvkyMuvHqKrAARahsihL` |
| 2, DCE       | `caaaah2glxepkeaiaarninsfbl` | `C11KtP6Y9P3rRkvh2N1e__L` | `CAAAD6Mu5HqIBAARahsihL` |
| 3, MD5       | `dhwatzo2h7mv2dx4ddykzhlbjj` | `D2ioV6oTr9yq6dMojd469nJ` | `DPYE8u0f7K6Hfgx4Vk6wpJ` |
| 4, Random    | `eagdhwlfa3vm4rv4j4vcvhdlmj` | `E3UZ99RxxUJC1v4dWsYtb_J` | `EAYZ7LKDdWcjXieVFU41sJ` |
| 5, SHA-1     | `feh37rxuakg4jnaabsxxxtc3ki` | `Fx7wEJfz9eb1TYzsrT7Zs_I` | `FIff43oBRuJaAAZXveYtqI` |
| 6, Timestamp | `gd3euctbdfkyahse6nppm5wcgl` | `GrxRCnDiX4mxSpdi5LEvR_L` | `GHslBTCMqsAPInmvezthGL` |
| 7, Timestamp | `haf7sfytzwdgdrrg4bqgaoompj` | `H3RrXaX7uTM6qdwrXwpC6_J` | `HAX8i4nmwzDjE3AwMBzmPJ` |
| 8, Custom    | `igigd2tomab235sjs2x3jdaoai` | `I2QDDTZysWZ5jcKS6HJDmHI` | `IMgw9TcwAdb7JMtX2kYHAI` |
| 15, Max      | `p777777777777777777777777p` | `P8AQGAut7N92awznwCnjuQP` | `P____________________P` |

| Version      | uuid-ncname-32-lex           | uuid-ncname-58-lex        | uuid-ncname-64-lex       |
|--------------|------------------------------|---------------------------|--------------------------|
| 0, Nil       | `a0000000000000000000000002` | `A1111111111111111111112` | `A--------------------2` |
| 1, Timestamp | `bp9lu9i6blsfapam004ba3ch8t` | `B6fTkmTD22KrBbQ6F3diU7T` | `BmajZmBij6emek-3LcQ7cT` |
| 2, DCE       | `c00007q6bn4fap02004ba3ch8t` | `C1111KtP6Y9QogqndZahf5T` | `C---2uBit6em-F-3LcQ7cT` |
| 3, MD5       | `d7m0jpeq7vclq8tv0ou2m9qoat` | `D2ioV6oTr9yqXobWp5RXvqT` | `DEN3wioUv9uGrsBT4ODg9T` |
| 4, Random    | `e0637mb50rlcscdf2f58l9oqrs` | `E13UZ99RxxUHkVGJNDLJV8S` | `E-NOvA92SLRNpsb_GKDCQS` |
| 5, SHA-1     | `f47rvhnk0a6s93800clttsomqu` | `F1x7wEJfz9eaCAWiLtSNd7U` | `F7UUsrc0Gi85V-5KvraAPU` |
| 6, Timestamp | `g3r4k2j135ao0psh7jbrr7dghu` | `G1rxRCnDiX4oVLAmr79G6LU` | `G6g_0I1Beg-nm8tfrgvNGU` |
| 7, Timestamp | `h05vi5ojpm363cc9n0c1g3jj3v` | `H13RrXaX7uTLfqn6haaYcaV` | `H-MwXsbakn2NlCkB2-RtYV` |
| 8, Custom    | `i6863qjec01qr7cicmluq8o3gs` | `I2QDDTZysWZ3t2b7UGbWyDS` | `IBVkxIRk-SQDmIAKxd50kS` |
| 15, Max      | `pvvvvvvvvvvvvvvvvvvvvvvvvz` | `P8AQGAut7N92awznwCnjuQZ` | `PzzzzzzzzzzzzzzzzzzzzZ` |

## Usage

```java
module MyModule {
   requires ch.randelshofer.uuidncname;
}
```

```java
import ch.randelshofer.uuidncname.UuidFormat;
import ch.randelshofer.uuidncname.UuidNCName;

import java.util.UUID;

public class MyMain {
   public void main(String... args) {
      UUID uu = UUID.randomUUID();
      // => df521e0a-9d57-4f04-9a95-fc2888decc5a

      String nc64 = UuidNCName.toString(uu, UuidFormat.NCNAME_64);
      // => "E31IeCp1X8EqV_CiI3sxaJ"

      String nc32Lex = UuidNCName.toString(uu, UuidFormat.NCNAME_32_LEX);
      // => "evxd3w4oxezs6p7jw746hxn4ut"

      UUID orig = UuidNCName.fromString(nc64);
      // => df521e0a-9d57-4f04-9a95-fc2888decc5a

      boolean b = orig.equals(UuidNCName.fromString(nc32Lex));
      // => true

      b = orig.equals(uu);
      // => true
   }
}

```

## Syntax

Here is the ABNF grammar of the supported formats:

    uuid-canonical     = 8hexDigit "-" 4hexDigit "-" 4hexDigit "-" 4hexDigit "-" 12hexDigit ;
    
    uuid-ncname-32     = bookend 24base32              bookend ;
    uuid-ncname-58     = bookend 15*21base58 *6padding bookend ;
    uuid-ncname-64     = bookend 20base64-url          bookend ;

    uuid-ncname-32-lex = bookend 24base32hex  bookend-lex ;
    uuid-ncname-58-lex = bookend 21base58     bookend-lex ;
    uuid-ncname-64-lex = bookend 20base64-lex bookend-lex ;

    hexDigit       = %x30-39 / %x41-46 / %x61-66 ; [0-9A-Fa-f]

    bookend        = %x41-50 / %x61-70 ; [A-Pa-p]
    bookend-lex    = %x32-37 / %x51-5A / %x71-7A ; [2-7Q-Zq-z]
    padding        = "_" ;
    
    base32         = %x41-5a / %x61-7a / %x32-37 ; [A-Za-z2-7]
    base32hex      = %x32-37 / %x41-5a / %x61-7a ; [0-9A-Va-v]

    base58         = %x31-39 / %x41-48 / %x4a-4e / %x50-5a / %x61-6c / %x6d-7a ; [1-9A-HJ-NP-Za-km-z]

    base64-url     = %x30-39 / %x41-5a / %x61-7a / %x2d / %x5f ; [A-Z_a-z0-9\-_]
    base64-lex     = %x2d / %x30-39 / %x41-5a / %x5f / %x61-7a ; [-0-9A-Z_a-z]

## Detection Heuristic

All formats are fixed length:

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

### extractNonLexicalFields(UUID):(version,variant,data)

An `UUID` is a 128-bit unsigned integer in big-endian order.
It contains variable-length fields. The field layout depends on the
version and variant of a `UUID`.

For the purpose of extraction, we overlay an `UUID` with the following fields:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |                         data[88-119]                          |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |          data[72-87]          |version|      data[60-71]      |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |variant|                  data[32-59]                          |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |                          data[0-31]                           |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:

1. `version` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_f000_0000_000000000000 from the UUID,
   and compress them into 4 bits.
2. `variant` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_0000_f000_000000000000 from the UUID,
   and compress them into 4 bits.
3. `data` is a 120-bit unsigned integer in big-endian order.<br>
   Extract bits 0xffffffff_ffff_0fff_0fff_ffffffffffff from the UUID,
   and compress them into 120 bits.

Note that the field `variant` is only 2 bits long in [UUIDv6][UUIDv6-specification]
and [UUIDv7][UUIDv7-specification].

By moving a 4-bit variant field to the end of the String in the formatting algorithms
`uuid-ncname-32`, `uuid-ncname-58` and  `uuid-ncname-64`, we effectively reorder data bits
of the UUID, and therefore we do not retain the ordering of the UUIDs in the lexical
representation.

### extractLexicalFields(UUID):(version,variant-lex,data-lex)

For the purpose of extraction, we overlay an `UUID` with the following fields:

       0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |                       data-lex[88-119]                        |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |        data-lex[72-87]        |version|    data-lex[60-71]    |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |v1 |                   data-lex[30-59]                         |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:
      |                       data-lex[0-29]                      |v0 |
      :-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:-+-+-+-:

1. `version` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_f000_0000_000000000000 from the UUID,
   and compress them into 4 bits.
2. `variant-lex` is a 4-bit unsigned integer in big-endian order.<br>
   Extract bits 0x00000000_0000_0000_c000_000000000003 from the UUID,
   and compress them into 4 bits. (Shown as v0 and v1 in the diagram above).
3. `data-lex` is a 120-bit unsigned integer in big-endian order.<br>
   Extract bits 0xffffffff_ffff_0fff_3fff_fffffffffffc from the UUID,
   and compress them into 120 bits.

Note that the field `variant` is only 2 bits long in [UUIDv6][UUIDv6-specification]
and [UUIDv7][UUIDv7-specification].

Although we move the variant field to the
end of the String in the formatting algorithms `uuid-ncname-32-lex`,
`uuid-ncname-58-lex` and `uuid-ncname-64-lex`, we **retain** the ordering
in the lexical representation, because we do not reorder data bits of the UUID.

### formatUuidCanonical(UUID):String

1. `uuid-digits :=` Convert the 128-bit `UUID` from binary to base-16.<br>
   This yields 32 digits (padded with leading zeroes).
2. `uuid-chars :=` Encode each `uuid-digits` digit with a lower-case [`hexDigit`](#hexDigit) character.
3. `uuid-canonical :=` Insert a '-' character after `uuid-chars` at 8, 12, 16 and 24.

### formatUuidNcName32(UUID):String

1. `version,variant,data :=` Extract fields from a `UUID` with algorithm
   `extractNonLexicalFields`.
2. `version-char :=` Encode `version` with a lower-case [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a lower-case [`bookend`](#bookend) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base32.<br>
   This yields 24 digits (padded with leading zeroes).
5. `data-chars :=` Encode each `data-digits` digit with a lower-case [`base32`](#base32) character.
6. `uuid-ncname-32 :=` Concat `version-char`, `data-chars`, `variant-char`.

### formatUuidNcName58(UUID):String

1. `version,variant,data :=` Extract fields from a `UUID` with algorithm
   `extractNonLexicalFields`.
2. `version-char :=` Encode `version` with a upper-case [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a upper-case [`bookend`](#bookend) character.
4. `data-chars :=` Encode the 120-bit `data` field with the
   [Base58 encoding algorithm][base58-encoding-algorithm].<br>
   This yields 15 to 21 characters.
5. `data-chars-padded :=` Pad the `data-chars` with trailing '_' chars
   to reach a total length of 21 characters.
6. `uuid-ncname-58 :=` Concat `version-char`, `data-chars-padded`, `variant-char`.

### formatUuidNcName64(UUID):String

1. `version,variant,data :=` Extract fields from a `UUID` with algorithm
   `extractNonLexicalFields`.
2. `version-char :=` Encode `version` with a upper-case [`bookend`](#bookend) character.
3. `variant-char :=` Encode `variant` with a upper-case [`bookend`](#bookend) character.
4. `data-digits :=` Convert the 120-bit `data` field from binary to base-64.<br>
   This yields 20 digits (padded with leading zeroes).
5. `data-chars :=` Encode each `data-digits` digit with a [`base64-url`](#base64-url) character.
6. `uuid-ncname-64 :=` Concat `version-char`, `data-chars`, `variant-char`.

### formatUuidNcName32Lex(UUID):String

1. `version,variant-lex,data-lex :=` Extract fields from a `UUID` with algorithm
   `extractLexicalFields`.
2. `version-char :=` Encode `version` with a lower-case [`bookend`](#bookend) character.
3. `data-lex-digits :=` Convert the 120-bit `data-lex` field from binary to base32.<br>
   This yields 24 digits (padded with leading zeroes).
4. `data-lex-chars :=` Encode each data-lex digit with a [`base32hex`](#base32hex) character.
5. `variant-lex-char :=` Encode `variant-lex` with a lower-case [`bookend-lex`](#bookend-lex) character.
6. `uuid-ncname-32-lex :=` Concat `version-char`, `data-lex-chars`, `variant-lex-char`.

### formatUuidNcName58Lex(UUID):String

1. `version,variant-lex,data-lex :=` Extract fields from a `UUID` with algorithm
   `extractLexicalFields`.
2. `version-char :=` Encode `version` with a lower-case [`bookend`](#bookend) character.
3. `data-lex-digits :=` Convert the 120-bit `data-lex` field from binary to base-58.<br>
   This yields 21 digits (padded with leading zeroes).
4. `data-lex-chars :=` Encode each data-lex digit with a [`base58`](#base58) character.
5. `variant-lex-char :=` Encode `variant-lex` with a lower-case [`bookend-lex`](#bookend-lex) character.
6. `uuid-ncname-58-lex :=` Concat `version-char`, `data-lex-chars`, `variant-lex-char`.

### formatUuidNcName64Lex(UUID):String

1. `version,variant-lex,data-lex :=` Extract fields from a `UUID` with algorithm
   `extractLexicalFields`.
2. `version-char :=` Encode `version` with a lower-case [`bookend`](#bookend) character.
3. `data-lex-digits :=` Convert the 120-bit `data-lex` field from binary to base-64.<br>
   This yields 20 digits (padded with leading zeroes).
4. `data-lex-chars :=` Encode each data-lex digit with a [`base64-lex`](#base64-lex) character.
5. `variant-lex-char :=` Encode `variant-lex` with a lower-case [`bookend-lex`](#bookend-lex) character.
6. `uuid-ncname-32-lex :=` Concat `version-char`, `data-lex-chars`, `variant-lex-char`.

## Encoding Alphabets

### The `base32` Alphabet

This is the alphabet specified in
[RFC-4648, Table 3: The Base 32 Alphabet][base32-encoding-table].

The alphabet is not case sensitive.

| Dec | Char | Dec | Char | Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|:---:|:----:|:---:|:----:|
|  0  | 'A'  |  8  | 'I'  | 16  | 'Q'  | 24  | 'Y'  |
|  1  | 'B'  |  9  | 'J'  | 17  | 'R'  | 25  | 'Z'  |
|  2  | 'C'  | 10  | 'K'  | 18  | 'S'  | 26  | '2'  |
|  3  | 'D'  | 11  | 'L'  | 19  | 'T'  | 27  | '3'  |
|  4  | 'E'  | 12  | 'M'  | 20  | 'U'  | 28  | '4'  |
|  5  | 'F'  | 13  | 'N'  | 21  | 'V'  | 29  | '5'  |
|  6  | 'G'  | 14  | 'O'  | 22  | 'W'  | 30  | '6'  |
|  7  | 'H'  | 15  | 'P'  | 23  | 'X'  | 31  | '7'  |

### The `base32hex` Alphabet

This is the alphabet specified in
[RFC-4648, Table 4: The "Extended Hex" Base 32 Alphabet][base32hex-encoding-table].

The alphabet is not case sensitive.

| Dec | Char | Dec | Char | Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|:---:|:----:|:---:|:----:|
|  0  | '0'  |  8  | '8'  | 16  | 'G'  | 24  | 'O'  |
|  1  | '1'  |  9  | '9'  | 17  | 'H'  | 25  | 'P'  |
|  2  | '2'  | 10  | 'A'  | 18  | 'I'  | 26  | 'Q'  |
|  3  | '3'  | 11  | 'B'  | 19  | 'J'  | 27  | 'R'  |
|  4  | '4'  | 12  | 'C'  | 20  | 'K'  | 28  | 'S'  |
|  5  | '5'  | 13  | 'D'  | 21  | 'L'  | 29  | 'T'  |
|  6  | '6'  | 14  | 'E'  | 22  | 'M'  | 30  | 'U'  |
|  7  | '7'  | 15  | 'F'  | 23  | 'N'  | 31  | 'V'  |

### The `base58` Alphabet

This is the alphabet specified in
[draft-msporny-base58-02, Table 1: Base58 Mapping Table][base-58-encoding-table].

The alphabet is case sensitive.

| Dec | Char | Dec | Char | Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|:---:|:----:|:---:|:----:|
|  0  | '1'  | 16  | 'H'  | 32  | 'Z'  | 48  | 'q'  |
|  1  | '2'  | 17  | 'J'  | 33  | 'a'  | 49  | 'r'  |
|  2  | '3'  | 18  | 'K'  | 34  | 'b'  | 50  | 's'  |
|  3  | '4'  | 19  | 'L'  | 35  | 'c'  | 51  | 't'  |
|  4  | '5'  | 20  | 'M'  | 36  | 'd'  | 52  | 'u'  |
|  5  | '6'  | 21  | 'N'  | 37  | 'e'  | 53  | 'v'  |
|  6  | '7'  | 22  | 'P'  | 38  | 'f'  | 54  | 'w'  |
|  7  | '8'  | 23  | 'Q'  | 39  | 'g'  | 55  | 'x'  |
|  8  | '9'  | 24  | 'R'  | 40  | 'h'  | 56  | 'y'  |
|  9  | 'A'  | 25  | 'S'  | 41  | 'i'  | 57  | 'z'  |
| 10  | 'B'  | 26  | 'T'  | 42  | 'j'  |     |      |
| 11  | 'C'  | 27  | 'U'  | 43  | 'k'  |     |      |
| 12  | 'D'  | 28  | 'V'  | 44  | 'm'  |     |      |
| 13  | 'E'  | 29  | 'W'  | 45  | 'n'  |     |      |
| 14  | 'F'  | 30  | 'X'  | 46  | 'o'  |     |      |
| 15  | 'G'  | 31  | 'Y'  | 47  | 'p'  |     |      |

### The `base64-url` Alphabet

This is the alphabet specified in
[RFC-4648, Table 2: The "URL and Filename safe" Base 64 Alphabet][base-64-url-encoding-table].

The alphabet is case sensitive.

| Dec | Char | Dec | Char | Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|:---:|:----:|:---:|:----:|
|  0  | 'A'  | 16  | 'Q'  | 32  | 'g'  | 48  | 'w'  |
|  1  | 'B'  | 17  | 'R'  | 33  | 'h'  | 49  | 'x'  |
|  2  | 'C'  | 18  | 'S'  | 34  | 'i'  | 50  | 'y'  |
|  3  | 'D'  | 19  | 'T'  | 35  | 'j'  | 51  | 'z'  |
|  4  | 'E'  | 20  | 'U'  | 36  | 'k'  | 52  | '0'  |
|  5  | 'F'  | 21  | 'V'  | 37  | 'l'  | 53  | '1'  |
|  6  | 'G'  | 22  | 'W'  | 38  | 'm'  | 54  | '2'  |
|  7  | 'H'  | 23  | 'X'  | 39  | 'n'  | 55  | '3'  |
|  8  | 'I'  | 24  | 'Y'  | 40  | 'o'  | 56  | '4'  |
|  9  | 'J'  | 25  | 'Z'  | 41  | 'p'  | 57  | '5'  |
| 10  | 'K'  | 26  | 'a'  | 42  | 'q'  | 58  | '6'  |
| 11  | 'L'  | 27  | 'b'  | 43  | 'r'  | 59  | '7'  |
| 12  | 'M'  | 28  | 'c'  | 44  | 's'  | 60  | '8'  |
| 13  | 'N'  | 29  | 'd'  | 45  | 't'  | 61  | '9'  |
| 14  | 'O'  | 30  | 'e'  | 46  | 'u'  | 62  | '-'  |
| 15  | 'P'  | 31  | 'f'  | 47  | 'v'  | 63  | '_'  |

### The `base64-lex` Alphabet

This is a lexically reordered variant of the alphabet specified in
[RFC-4648, Table 2: The "URL and Filename safe" Base 64 Alphabet][base-64-url-encoding-table].

The alphabet is case sensitive.

| Dec | Char | Dec | Char | Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|:---:|:----:|:---:|:----:|
|  0  | '-'  | 16  | 'F'  | 32  | 'V'  | 48  | 'k'  |
|  1  | '0'  | 17  | 'G'  | 33  | 'W'  | 49  | 'l'  |
|  2  | '1'  | 18  | 'H'  | 34  | 'X'  | 50  | 'm'  |
|  3  | '2'  | 19  | 'I'  | 35  | 'Y'  | 51  | 'n'  |
|  4  | '3'  | 20  | 'J'  | 36  | 'Z'  | 52  | 'o'  |
|  5  | '4'  | 21  | 'K'  | 37  | '_'  | 53  | 'p'  |
|  6  | '5'  | 22  | 'L'  | 38  | 'a'  | 54  | 'q'  |
|  7  | '6'  | 23  | 'M'  | 39  | 'b'  | 55  | 'r'  |
|  8  | '7'  | 24  | 'N'  | 40  | 'c'  | 56  | 's'  |
|  9  | '8'  | 25  | 'O'  | 41  | 'd'  | 57  | 't'  |
| 10  | '9'  | 26  | 'P'  | 42  | 'e'  | 58  | 'u'  |
| 11  | 'A'  | 27  | 'Q'  | 43  | 'f'  | 59  | 'v'  |
| 12  | 'B'  | 28  | 'R'  | 44  | 'g'  | 60  | 'w'  |
| 13  | 'C'  | 29  | 'S'  | 45  | 'h'  | 61  | 'x'  |
| 14  | 'D'  | 30  | 'T'  | 46  | 'i'  | 62  | 'y'  |
| 15  | 'E'  | 31  | 'U'  | 47  | 'j'  | 63  | 'z'  |

### The `bookend` Alphabet

The alphabet is not case sensitive.

| Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|
|  0  | 'A'  |  8  | 'I'  |
|  1  | 'B'  |  9  | 'J'  |
|  2  | 'C'  | 10  | 'K'  |
|  3  | 'D'  | 11  | 'L'  |
|  4  | 'E'  | 12  | 'M'  |
|  5  | 'F'  | 13  | 'N'  |
|  6  | 'G'  | 14  | 'O'  |
|  7  | 'H'  | 15  | 'P'  |

### The `bookend-lex` Alphabet

This alphabet is intentionally disjoint from the `bookend` alphabet.
This means, that we can use it to distinguish UUID-NCNames that can
be sorted lexicographically from UUID-NCNames that can not be sorted lexicographically.

The alphabet is not case sensitive.

| Dec | Char | Dec | Char |
|:---:|:----:|:---:|:----:|
|  0  | '2'  |  8  | 'S'  |
|  1  | '3'  |  9  | 'T'  |
|  2  | '4'  | 10  | 'U'  |
|  3  | '5'  | 11  | 'V'  |
|  4  | '6'  | 12  | 'W'  |
|  5  | '7'  | 13  | 'X'  |
|  6  | 'Q'  | 14  | 'Y'  |
|  7  | 'R'  | 15  | 'Z'  |

## Other Implementations

<dl>
<dt>Perl</dt>
<dd>

[doriantaylor/p5-data-uuid-ncname](https://github.com/doriantaylor/p5-data-uuid-ncname)
</dd>

<dt>Ruby</dt>
<dd>

[doriantaylor/rb-uuid-ncname](https://github.com/doriantaylor/rb-uuid-ncname)
</dd>
</dl>


[UUID-specification]: https://www.rfc-editor.org/rfc/rfc4122

[UUIDv6-specification]: https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-6

[UUIDv7-specification]: https://www.ietf.org/archive/id/draft-peabody-dispatch-new-uuid-format-04.html#name-uuid-version-7

[UUID-NCName-specification]: https://www.ietf.org/id/draft-taylor-uuid-ncname-03.html

[base32-encoding-table]: https://www.rfc-editor.org/rfc/rfc4648.html#section-6

[base32hex-encoding-table]: https://www.rfc-editor.org/rfc/rfc4648.html#section-7

[base-64-url-encoding-table]: https://www.rfc-editor.org/rfc/rfc4648.html#section-5

[base58-encoding-algorithm]: https://datatracker.ietf.org/doc/html/draft-msporny-base58-02#section-3

[base-58-encoding-table]: https://datatracker.ietf.org/doc/html/draft-msporny-base58-02#section-2
