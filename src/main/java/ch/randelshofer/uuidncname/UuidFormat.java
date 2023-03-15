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
    NCNAME_32,
    NCNAME_58,
    NCNAME_64,
    NCNAME_32_LEX,
    NCNAME_58_LEX,
    NCNAME_64_LEX,
}
