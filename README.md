# UuidNCName

This is an implementation of the draft specification "Compact UUIDs for Constrained Grammars"
https://www.ietf.org/id/draft-taylor-uuid-ncname-03.html#name-encoding-algorithm

UuidNCName converts UUID strings to/from valid NCName productions for use in (X|HT)ML.

UuidNCName supports the following formats:

* uuid-ncname-32
* uuid-ncname-58
* uuid-ncname-58-lexical
* uuid-ncname-64
* uuid-canonical (only for reading)

The format uuid-ncname-58-lexical is similar to uuid-ncname-58,
instead of padding numbers to the right with '_' characters,
it pads numbers to the left with '1' characters.

This has the following advantages:

* Removes the need for the '_' character in the string representation. This makes a uuid-ncname-58 visually appear more
  like a single word.

* Sorting an uuid-ncname-58 lexicographically yields the same sequence like sorting the binary representation of an
  UUID. (This is already the case with the canonical-uuid-representation, uuid-ncname-32, and uuid-ncname-64).

Example:

| Version      | uuid-canonical                     | uuid-ncname-32             |
|--------------|--------------------------------------|----------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | aaaaaaaaaaaaaaaaaaaaaaaaaa |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | bzjv6jsglv4pkfkyaarninsfbl |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | caaaah2glxepkeaiaarninsfbl |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | dhwatzo2h7mv2dx4ddykzhlbjj |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | eagdhwlfa3vm4rv4j4vcvhdlmj |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | feh37rxuakg4jnaabsxxxtc3ki |

| Version      | uuid-canonical                     | uuid-ncname-58          |
|--------------|--------------------------------------|-------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | A111111111111111______A |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | B6fTkmTD22KpWbDq1LuiszL |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | C11KtP6Y9P3rRkvh2N1e__L |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | D2ioV6oTr9yq6dMojd469nJ |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | E3UZ99RxxUJC1v4dWsYtb_J |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | Fx7wEJfz9eb1TYzsrT7Zs_I |

| Version      | uuid-canonical                     | uuid-ncname-58-lexical  |
|--------------|--------------------------------------|-------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | A111111111111111111111A |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | B6fTkmTD22KpWbDq1LuiszL |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | C1111KtP6Y9P3rRkvh2N1eL |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | D2ioV6oTr9yq6dMojd469nJ |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | E13UZ99RxxUJC1v4dWsYtbJ |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | F1x7wEJfz9eb1TYzsrT7ZsI |

| Version      | uuid-canonical                     | uuid-ncname-64         |
|--------------|--------------------------------------|------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | AAAAAAAAAAAAAAAAAAAAAA |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | BymvkyMuvHqKrAARahsihL |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | CAAAD6Mu5HqIBAARahsihL |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | DPYE8u0f7K6Hfgx4Vk6wpJ |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | EAYZ7LKDdWcjXieVFU41sJ |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | FIff43oBRuJaAAZXveYtqI |