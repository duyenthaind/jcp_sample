package com.thaind.jcp.enhance_threadsafed_object.threadsafe;

import com.thaind.jcp.enhance_threadsafed_object.collections.*;
import java.math.BigInteger;
import com.thaind.race_condition.lazyinit.ThreadSafe;

@ThreadSafe
public class Factorizer {
    private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
        public BigInteger[] compute(BigInteger arg) {
            return factor(arg);
        }
    };
    private final Computable<BigInteger, BigInteger[]> cache = new Memorizer<BigInteger, BigInteger[]>(c);

    public void service(ServletRequest req, ServletResponse resp) {
        try {
            BigInteger i = extractFromRequest(req);
            encodeIntoResponse(resp, cache.compute(i));
        } catch (InterruptedException e) {
            encodeError(resp, "factorization interrupted");
        }
    }

    void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

    void encodeError(ServletResponse resp, String errorString) {
    }

    BigInteger extractFromRequest(ServletRequest req) {
        return new BigInteger("7");
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }

    class ServletRequest {

    }

    class ServletResponse {

    }
}
