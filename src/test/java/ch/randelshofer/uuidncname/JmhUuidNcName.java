/*
 * @(#)JmhUuidNcName.java
 * Copyright © 2023 Werner Randelshofer, Switzerland. MIT License.
 */

package ch.randelshofer.uuidncname;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks for selected floating point strings.
 * <pre>
 * # JMH version: 1.36
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20+36-2344
 * # Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz
 *
 * Benchmark                 Mode  Cnt   Score   Error  Units
 * JmhUuidNcName.rBase32     avgt    2  25.467          ns/op
 * JmhUuidNcName.rBase32Lex  avgt    2  24.088          ns/op
 * JmhUuidNcName.rBase58     avgt    2  43.057          ns/op
 * JmhUuidNcName.rBase58Lex  avgt    2  44.967          ns/op
 * JmhUuidNcName.rBase64     avgt    2  20.480          ns/op
 * JmhUuidNcName.rBase64Lex  avgt    2  20.413          ns/op
 * JmhUuidNcName.rCanonical  avgt    2  15.889          ns/op
 * JmhUuidNcName.wBase32     avgt    2  28.370          ns/op
 * JmhUuidNcName.wBase32Lex  avgt    2  26.557          ns/op
 * JmhUuidNcName.wBase58     avgt    2  86.124          ns/op
 * JmhUuidNcName.wBase58Lex  avgt    2  74.942          ns/op
 * JmhUuidNcName.wBase64     avgt    2  27.435          ns/op
 * JmhUuidNcName.wBase64Lex  avgt    2  24.601          ns/op
 * JmhUuidNcName.wCanonical  avgt    2  18.486          ns/op
 * </pre>
 *
 * <pre>
 * # JMH version: 1.35
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20-ea+33
 * # Intel(R) Xeon(R) Platinum 8171M CPU @ 2.60GHz
 *
 * Benchmark                 Mode  Cnt    Score   Error  Units
 * JmhUuidNcName.rBase32     avgt        39.335          ns/op
 * JmhUuidNcName.rBase32Lex  avgt        39.229          ns/op
 * JmhUuidNcName.rBase58     avgt        65.348          ns/op
 * JmhUuidNcName.rBase58Lex  avgt        67.896          ns/op
 * JmhUuidNcName.rBase64     avgt        34.114          ns/op
 * JmhUuidNcName.rBase64Lex  avgt        33.277          ns/op
 * JmhUuidNcName.rCanonical  avgt        26.208          ns/op
 * JmhUuidNcName.wBase32     avgt        47.999          ns/op
 * JmhUuidNcName.wBase58     avgt       166.375          ns/op
 * JmhUuidNcName.wBase58Lex  avgt       119.919          ns/op
 * JmhUuidNcName.wBase64     avgt        42.292          ns/op
 * JmhUuidNcName.wCanonical  avgt        29.629          ns/op
 * </pre>
 */
@Fork(value = 1, jvmArgsAppend = {
        "-Xmx4g"
})
@Measurement(iterations = 2)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class JmhUuidNcName {


    private UUID uuid = UUID.randomUUID();
    private String canonical = uuid.toString();
    private String base32 = UuidNCName.toString(uuid, UuidFormat.NCNAME_32);
    private String base32Lex = UuidNCName.toString(uuid, UuidFormat.NCNAME_32_LEX);
    private String base58 = UuidNCName.toString(uuid, UuidFormat.NCNAME_58);
    private String base58Lex = UuidNCName.toString(uuid, UuidFormat.NCNAME_58_LEX);
    private String base64 = UuidNCName.toString(uuid, UuidFormat.NCNAME_64);
    private String base64Lex = UuidNCName.toString(uuid, UuidFormat.NCNAME_64_LEX);

    @Benchmark
    public UUID rBase32() {
        return UuidNCName.fromString(base32);
    }

    @Benchmark
    public UUID rBase32Lex() {
        return UuidNCName.fromString(base32Lex);
    }

    @Benchmark
    public UUID rBase58() {
        return UuidNCName.fromString(base58);
    }

    @Benchmark
    public UUID rBase58Lex() {
        return UuidNCName.fromString(base58Lex);
    }

    @Benchmark
    public UUID rBase64() {
        return UuidNCName.fromString(base64);
    }

    @Benchmark
    public UUID rBase64Lex() {
        return UuidNCName.fromString(base64Lex);
    }

    @Benchmark
    public UUID rCanonical() {
        return UUID.fromString(canonical);
    }

    @Benchmark
    public String wBase32() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_32);
    }

    @Benchmark
    public String wBase32Lex() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_32_LEX);
    }

    @Benchmark
    public String wBase58() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_58);
    }

    @Benchmark
    public String wBase58Lex() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_58_LEX);
    }

    @Benchmark
    public String wBase64() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_64);
    }


    @Benchmark
    public String wBase64Lex() {
        return UuidNCName.toString(uuid, UuidFormat.NCNAME_64_LEX);
    }

    @Benchmark
    public String wCanonical() {
        return uuid.toString();
    }
}