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
 * # JMH version: 1.35
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20-ea+35-2342
 * # Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz
 *
 * Benchmark                 Mode  Cnt   Score   Error  Units
 * JmhUUidNcName.rBase32     avgt    2  27.279          ns/op
 * JmhUUidNcName.rBase32Lex  avgt    2  24.868          ns/op
 * JmhUUidNcName.rBase58     avgt    2  53.427          ns/op
 * JmhUUidNcName.rBase58Lex  avgt    2  51.031          ns/op
 * JmhUUidNcName.rBase64     avgt    2  23.102          ns/op
 * JmhUUidNcName.rBase64Lex  avgt    2  21.348          ns/op
 * JmhUUidNcName.rCanonical  avgt    2  17.930          ns/op
 * JmhUUidNcName.wBase32     avgt    2  34.002          ns/op
 * JmhUUidNcName.wBase58     avgt    2  89.181          ns/op
 * JmhUUidNcName.wBase58Lex  avgt    2  83.707          ns/op
 * JmhUUidNcName.wBase64     avgt    2  30.583          ns/op
 * JmhUUidNcName.wCanonical  avgt    2  22.545          ns/op
 * </pre>
 *
 * <pre>
 * # JMH version: 1.35
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20-ea+33
 *
 *
 * Benchmark                 Mode  Cnt    Score   Error  Units
 * JmhUuidNcName.rBase32     avgt        40.185          ns/op
 * JmhUuidNcName.rBase32Lex  avgt        39.808          ns/op
 * JmhUuidNcName.rBase58     avgt        72.018          ns/op
 * JmhUuidNcName.rBase58Lex  avgt        68.786          ns/op
 * JmhUuidNcName.rBase64     avgt        34.299          ns/op
 * JmhUuidNcName.rBase64Lex  avgt        34.293          ns/op
 * JmhUuidNcName.rCanonical  avgt        26.789          ns/op
 * JmhUuidNcName.wBase32     avgt        48.996          ns/op
 * JmhUuidNcName.wBase58     avgt       153.432          ns/op
 * JmhUuidNcName.wBase58Lex  avgt       123.279          ns/op
 * JmhUuidNcName.wBase64     avgt        43.433          ns/op
 * JmhUuidNcName.wCanonical  avgt        30.187          ns/op
 * </pre>
 */
@Fork(value = 1, jvmArgsAppend = {
        "-Xmx4g"
})
@Measurement(iterations = 1)
@Warmup(iterations = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class JmhUuidNcName {


    private UUID uuid = UUID.randomUUID();
    private String canonical = uuid.toString();
    private String base32 = UuidNCName.toString(uuid, UuidFormat.BASE32);
    private String base32Lex = UuidNCName.toString(uuid, UuidFormat.BASE32_LEX);
    private String base58 = UuidNCName.toString(uuid, UuidFormat.BASE58);
    private String base58Lex = UuidNCName.toString(uuid, UuidFormat.BASE58_LEX);
    private String base64 = UuidNCName.toString(uuid, UuidFormat.BASE64);
    private String base64Lex = UuidNCName.toString(uuid, UuidFormat.BASE64_LEX);


    @Benchmark
    public UUID rCanonical() {
        return UUID.fromString(canonical);
    }

    @Benchmark
    public UUID rBase32() {
        return UuidNCName.fromString(base32);
    }

    @Benchmark
    public UUID rBase32Lex() {
        return UuidNCName.fromString(base32Lex);
    }

    @Benchmark
    public UUID rBase64Lex() {
        return UuidNCName.fromString(base64Lex);
    }

    @Benchmark
    public UUID rBase64() {
        return UuidNCName.fromString(base64);
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
    public String wCanonical() {
        return uuid.toString();
    }

    @Benchmark
    public String wBase32() {
        return UuidNCName.toString(uuid, UuidFormat.BASE32);
    }

    @Benchmark
    public String wBase64() {
        return UuidNCName.toString(uuid, UuidFormat.BASE64);
    }


    @Benchmark
    public String wBase58() {
        return UuidNCName.toString(uuid, UuidFormat.BASE58);
    }


    @Benchmark
    public String wBase58Lex() {
        return UuidNCName.toString(uuid, UuidFormat.BASE58_LEX);
    }


}