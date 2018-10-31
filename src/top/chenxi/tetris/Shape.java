package top.chenxi.tetris;

public class Shape {
    static boolean [][][] shap = {
            // I
            {
                    { false, true, false, false },
                    { false, true, false, false },
                    { false, true, false, false },
                    { false, true, false, false }
            },
            // J
            {
                    { true, false, false },
                    { true, true, true },
                    { false, false, false }
            },
            // L
            {
                    { false, false, true },
                    { true, true, true },
                    { false, false, false }
            },
            // O
            {
                    { true, true },
                    { true, true }
            },
            // S
            {
                    { false, true, true },
                    { true, true, false },
                    { false, false, false }
            },
            // T
            {
                    { false, true, false },
                    { true, true, true },
                    { false, false, false }
            },
            // Z
            {
                    { true, true, false },
                    { false, true, true },
                    { false, false, false }
            }
    };

}
