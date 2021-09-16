package com.thaind.jcp.fraction_8;

import java.util.Set;

/**
 * @author duyenthai
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);

}
