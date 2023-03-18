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
 * Benchmark   Mode  Cnt   Score   Error  Units
 * rBase32     avgt   16  24.631 ± 0.323  ns/op
 * rBase32Lex  avgt   16  23.828 ± 0.273  ns/op
 * rBase58     avgt   16  49.164 ± 0.304  ns/op
 * rBase58Lex  avgt   16  41.630 ± 0.227  ns/op
 * rBase64     avgt   16  20.150 ± 0.080  ns/op
 * rBase64Lex  avgt   16  20.062 ± 0.083  ns/op
 * rCanonical  avgt   16  15.616 ± 0.043  ns/op
 * wBase32     avgt   16  26.896 ± 0.582  ns/op
 * wBase32Lex  avgt   16  27.758 ± 0.678  ns/op
 * wBase58     avgt   16  81.483 ± 4.661  ns/op
 * wBase58Lex  avgt   16  72.292 ± 0.518  ns/op
 * wBase64     avgt   16  25.976 ± 0.911  ns/op
 * wBase64Lex  avgt   16  24.841 ± 0.443  ns/op
 * wCanonical  avgt   16  18.074 ± 0.165  ns/op
 * </pre>
 *
 * <pre>
 * # JMH version: 1.36
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20-ea+36
 * # Intel(R) Xeon(R) Platinum 8370C CPU @ 2.80GHz
 *
 * Benchmark                 Mode  Cnt    Score   Error  Units
 * JmhUuidNcName.rBase32     avgt        33.748          ns/op
 * JmhUuidNcName.rBase32Lex  avgt        33.758          ns/op
 * JmhUuidNcName.rBase58     avgt        67.572          ns/op
 * JmhUuidNcName.rBase58Lex  avgt        54.718          ns/op
 * JmhUuidNcName.rBase64     avgt        27.180          ns/op
 * JmhUuidNcName.rBase64Lex  avgt        27.761          ns/op
 * JmhUuidNcName.rCanonical  avgt        25.018          ns/op
 * JmhUuidNcName.wBase32     avgt        31.318          ns/op
 * JmhUuidNcName.wBase32Lex  avgt        31.853          ns/op
 * JmhUuidNcName.wBase58     avgt       113.022          ns/op
 * JmhUuidNcName.wBase58Lex  avgt       101.783          ns/op
 * JmhUuidNcName.wBase64     avgt        28.605          ns/op
 * JmhUuidNcName.wBase64Lex  avgt        27.785          ns/op
 * JmhUuidNcName.wCanonical  avgt        26.324          ns/op
 * [
 * </pre>
 */
@Fork(value = 1, jvmArgsAppend = {
        "-Xmx4g"
})
@Warmup(iterations = 2)
@Measurement(iterations = 2)
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
*/
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