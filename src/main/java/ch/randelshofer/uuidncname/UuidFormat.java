/*
 * @(#)UuidFormat.java
 * Copyright © 2023 Werner Randelshofer, Switzerland. MIT License.
 */

package ch.randelshofer.uuidncname;

import java.util.UUID;

/**
 * Specifies a formatting option for an {@link UUID}.
 */
public enum UuidFormat {
    CANONICAL,
    BASE32,
    BASE58,
    BASE64,
    BASE32_LEX,
    BASE58_LEX,
    BASE64_LEX,
}
