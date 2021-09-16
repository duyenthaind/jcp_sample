package com.thaind.jcp.fraction_8;

import com.thaind.jcp.volatile_threadsafe.Immutable;

import java.util.LinkedList;
import java.util.List;

/**
 * @author duyenthai
 */
@Immutable
public class PuzzleNode<P, M> {
    final P position;
    final M move;
    final PuzzleNode<P, M> prev;

    public PuzzleNode(P position, M move, PuzzleNode<P, M> prev) {
        this.position = position;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (PuzzleNode<P, M> node = this; node.move != null; node = node.prev) {
            solution.add(0, node.move);
        }
        return solution;
    }
}
