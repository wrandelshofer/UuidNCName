# UuidNCName

This is an implementation of the draft specification "Compact UUIDs for Constrained Grammars"
https://www.ietf.org/id/draft-taylor-uuid-ncname-03.html#name-encoding-algorithm

UuidNCName converts UUID strings to/from valid NCName productions for use in (X|HT)ML.

UuidNCName supports the following formats:

* uuid-canonical
* uuid-ncname-32
* uuid-ncname-58
* uuid-ncname-64
* uuid-ncname-32-lexical
* uuid-ncname-58-lexical
* uuid-ncname-64-lexical

The uuid-canonical and the uuid-ncname-…-lexical formats have the same lexicographic order as the
bit sequence of the UUID. This is only true for the same version and variant of an UUID.

The last character of the uuid-ncname-…-lexical formats is in `[2-7][Q-Z][q-z]`.
The last character of the uuid-ncname-…  (non-lexical) formats is in `[A-P][a-p]`.

The format uuid-ncname-58-lexical is similar to uuid-ncname-58,
instead of padding numbers to the right with '_' characters,
it pads numbers to the left with '1' characters.

This has the following advantages:

* Removes the need for the '_' character in the string representation. This makes a uuid-ncname-58 visually appear more
  like a single word.

* Sorting an uuid-ncname-58 lexicographically yields the same sequence like sorting the binary representation of an
  UUID. (This is the case with uuid-canonical).

Example:

| Version      | uuid-canonical                       | uuid-ncname-32             |
|--------------|--------------------------------------|----------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | aaaaaaaaaaaaaaaaaaaaaaaaaa |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | bzjv6jsglv4pkfkyaarninsfbl |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | caaaah2glxepkeaiaarninsfbl |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | dhwatzo2h7mv2dx4ddykzhlbjj |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | eagdhwlfa3vm4rv4j4vcvhdlmj |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | feh37rxuakg4jnaabsxxxtc3ki |

| Version      | uuid-canonical                       | uuid-ncname-32-lexical     |
|--------------|--------------------------------------|----------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | a2222222222222222222222222 |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | btdpydmafpwje7es22lhchm73v |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | c2222buafr6je62c22lhchm73v |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | dbq2ntiubzgpu5rw55setbf3dt |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | e2a5bqf72vpgwlpwdwp4pb5fgt |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | f6bvzlro2eawdh223mrrrn4ves |
| 6, Timestamp | 1EC9414C-232A-6B00-B3C8-9E6BDECED846 | g5v6o4n357es2bm6yhjjgxq4av |
| 7, Timestamp | 017F22E2-79B0-7CC3-98C4-DC0C0C07398F | h27zm7sntq5a5llaw3ka2iigjt |
| 8, Vendor    | 320C3D4D-CC00-875B-8EC9-32D5F69181C0 | iaca5unig23uvxmdmurvd52i2s |
| 15, Max      | ffffffff-ffff-ffff-ffff-ffffffffffff | pzzzzzzzzzzzzzzzzzzzzzzzzz |

| Version      | uuid-canonical                       | uuid-ncname-58          |
|--------------|--------------------------------------|-------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | A111111111111111______A |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | B6fTkmTD22KpWbDq1LuiszL |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | C11KtP6Y9P3rRkvh2N1e__L |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | D2ioV6oTr9yq6dMojd469nJ |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | E3UZ99RxxUJC1v4dWsYtb_J |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | Fx7wEJfz9eb1TYzsrT7Zs_I |

| Version      | uuid-canonical                       | uuid-ncname-58-lexical  |
|--------------|--------------------------------------|-------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | A1111111111111111111112 |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | B6fTkmTD22KpWbDq1LuiszV |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | C1111KtP6Y9P3rRkvh2N1eV |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | D2ioV6oTr9yq6dMojd469nT |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | E13UZ99RxxUJC1v4dWsYtbT |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | F1x7wEJfz9eb1TYzsrT7ZsS |

| Version      | uuid-canonical                     | uuid-ncname-64         |
|--------------|--------------------------------------|------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | AAAAAAAAAAAAAAAAAAAAAA |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | BymvkyMuvHqKrAARahsihL |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | CAAAD6Mu5HqIBAARahsihL |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | DPYE8u0f7K6Hfgx4Vk6wpJ |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | EAYZ7LKDdWcjXieVFU41sJ |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | FIff43oBRuJaAAZXveYtqI |

| Version      | uuid-canonical                     | uuid-ncname-64-lexical |
|--------------|--------------------------------------|------------------------|
| 0, Nil       | 00000000-0000-0000-0000-000000000000 | A--------------------2 |
| 1, Timestamp | ca6be4c8-cbaf-11ea-b2ab-00045a86c8a1 | BmajZmBij6e9f--GPWgXWV |
| 2, DCE       | 000003e8-cbb9-21ea-b201-00045a86c8a1 | C---2uBit6e70--GPWgXWV |
| 3, MD5       | 3d813cbb-47fb-32ba-91df-831e1593ac29 | DEN3wioUv9u6UVlsKZukdT |
| 4, Random    | 01867b2c-a0dd-459c-98d7-89e545538d6c | E-NOvA92SLRYMXTK4JspgT |
| 5, SHA-1     | 21f7f8de-8051-5b89-8680-0195ef798b6a | F7UUsrc0Gi8P--OMjTNheS |