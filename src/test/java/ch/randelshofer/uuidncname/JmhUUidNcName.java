package ch.randelshofer.uuidncname;

import org.openjdk.jmh.annotations.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Benchmarks for selected floating point strings.
 * <pre>
 * # JMH version: 1.35
 * # VM version: JDK 20-ea, OpenJDK 64-Bit Server VM, 20-ea+33-2334
 * # Intel(R) Core(TM) i7-8700B CPU @ 3.20GHz
 *
 * Benchmark                     Mode  Cnt    Score   Error  Units
 * JmhUUidNcName.rBase32         avgt    2   22.327          ns/op
 * JmhUUidNcName.rBase32Lex      avgt    2   22.007          ns/op
 * JmhUUidNcName.rBase58         avgt    2  495.275          ns/op
 * JmhUUidNcName.rBase58Lex      avgt    2  476.413          ns/op
 * JmhUUidNcName.rBase64         avgt    2   20.027          ns/op
 * JmhUUidNcName.rBase64Lex      avgt    2   18.607          ns/op
 * JmhUUidNcName.rCanonical      avgt    2   15.444          ns/op
 * JmhUUidNcName.wBase32         avgt    2   25.861          ns/op
 * JmhUUidNcName.wBase58         avgt    2  541.338          ns/op
 * JmhUUidNcName.wBase58Lex      avgt    2  543.664          ns/op
 * JmhUUidNcName.wBase58LexFast  avgt    2  120.433          ns/op
 * JmhUUidNcName.wBase64         avgt    2   25.606          ns/op
 * JmhUUidNcName.wCanonical      avgt    2   17.813          ns/op
 * </pre>
 */
@Fork(value = 1, jvmArgsAppend = {
        "-Xmx16g"
})
@Measurement(iterations = 2)
@Warmup(iterations = 2)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@State(Scope.Benchmark)
public class JmhUUidNcName {


    private UUID uuid = UUID.randomUUID();
    private String canonical = uuid.toString();
    private String base32 = UuidNCName.toBase32(uuid);
    private String base32Lex = UuidNCName.toBase32Lexical(uuid);
    private String base58 = UuidNCName.toBase58(uuid);
    private String base58Lex = UuidNCName.toBase58Lexical(uuid);
    private String base64 = UuidNCName.toBase64(uuid);
    private String base64Lex = UuidNCName.toBase64Lexical(uuid);

/*
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
        return UuidNCName.toBase32(uuid);
    }

    @Benchmark
    public String wBase64() {
        return UuidNCName.toBase64(uuid);
    }

    @Benchmark
    public String wBase58Lex() {
        return UuidNCName.toBase58(uuid);
    }
    @Benchmark
    public String wBase58() {
        return UuidNCName.toBase58(uuid);
    }
    */

    @Benchmark
    public String wBase58LexFast() {
        return UuidNCName.toBase58Lexical(uuid);
    }


}