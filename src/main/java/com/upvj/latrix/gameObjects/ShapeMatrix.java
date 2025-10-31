package com.upvj.latrix.gameObjects;

public enum ShapeMatrix {
    L(new Integer[][] {
            {1, 0},
            {1, 0},
            {1, 1}
    }),

    rL(new Integer[][] {
            {0, 1},
            {0, 1},
            {1, 1}
    }),

    I(new Integer[][] {
            {1},
            {1},
            {1},
            {1}
    }),

    O(new Integer[][] {
            {1, 1},
            {1, 1}
    }),

    S(new Integer[][] {
            {1, 0},
            {1, 1},
            {0, 1}
    }),

    rS(new Integer[][] {
            {0, 1},
            {1, 1},
            {1, 0}
    }),

    T(new Integer[][] {
            {1, 1, 1},
            {0, 1, 0}
    }),

    MAP(new Integer[][] {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    });

    // --- Instance field ---
    private final Integer[][] matrix;

    // --- Constructor ---
    ShapeMatrix(Integer[][] matrix) {
        this.matrix = matrix;
    }

    // --- Getter ---
    public Integer[][] getMatrix() {
        return matrix;
    }

    public static Integer[][] cloneMatrix(Integer[][] src) {
        Integer[][] copy = new Integer[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, copy[i], 0, src[i].length);
        }
        return copy;
    }

}

