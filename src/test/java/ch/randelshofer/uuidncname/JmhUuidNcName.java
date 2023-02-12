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
 * JmhUuidNcName.wBase58     avgt    2  91.435          ns/op
 * JmhUuidNcName.wBase58Lex  avgt    2  76.601          ns/op
 * JmhUuidNcName.wCanonical  avgt    2  22.511          ns/op
 *
 * Benchmark                 Mode  Cnt   Score   Error  Units
 * JmhUuidNcName.rBase32     avgt   10  22.533 ± 0.089  ns/op
 * JmhUuidNcName.rBase32Lex  avgt   10  22.413 ± 0.265  ns/op
 * JmhUuidNcName.rBase58     avgt   10  43.839 ± 2.853  ns/op
 * JmhUuidNcName.rBase58Lex  avgt   10  42.334 ± 0.205  ns/op
 * JmhUuidNcName.rBase64     avgt   10  20.340 ± 0.185  ns/op
 * JmhUuidNcName.rBase64Lex  avgt   10  20.723 ± 2.754  ns/op
 * JmhUuidNcName.rCanonical  avgt   10  15.704 ± 0.056  ns/op
 * JmhUuidNcName.wBase32     avgt   10  27.041 ± 0.631  ns/op
 * JmhUuidNcName.wBase58     avgt   10  82.925 ± 1.782  ns/op
 * JmhUuidNcName.wBase58Lex  avgt   10  74.217 ± 0.409  ns/op
 * JmhUuidNcName.wBase64     avgt   10  25.869 ± 1.949  ns/op
 * JmhUuidNcName.wCanonical  avgt   10  18.006 ± 0.089  ns/op
 * [
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
    private String base32 = UuidNCName.toString(uuid, UuidFormat.BASE32);
    private String base32Lex = UuidNCName.toString(uuid, UuidFormat.BASE32_LEX);
    private String base58 = UuidNCName.toString(uuid, UuidFormat.BASE58);
    private String base58Lex = UuidNCName.toString(uuid, UuidFormat.BASE58_LEX);
    private String base64 = UuidNCName.toString(uuid, UuidFormat.BASE64);
    private String base64Lex = UuidNCName.toString(uuid, UuidFormat.BASE64_LEX);

    /*
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
            return UuidNCName.toString(uuid, UuidFormat.BASE32);
        }
    @Benchmark
    public String wBase58() {
        return UuidNCName.toString(uuid, UuidFormat.BASE58);
    }
    */

    @Benchmark
    public String wBase58Lex() {
        return UuidNCName.toString(uuid, UuidFormat.BASE58_LEX);
    }

    /*
        @Benchmark
        public String wBase64() {
            return UuidNCName.toString(uuid, UuidFormat.BASE64);
        }
    */
    @Benchmark
    public String wCanonical() {
        return uuid.toString();
    }

}