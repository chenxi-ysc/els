package top.chenxi.tetris;

public class Shape {
    static boolean [][][] T = {
            {
                    {false, true, false},
                    {true, true, true}
            },
            {
                    {true, false},
                    {true, true},
                    {true, false}
            },
            {
                    {true, true, true},
                    {false, true, false}
            },
            {
                    {false, true},
                    {true, true},
                    {false, true}
            }
    };
    static boolean [][][] I = {
            {
                    {false,true},
                    {false,true},
                    {false,true},
                    {false,true}
            },
            {
                    {false,false,false,false},
                    {true,true,true,true}
            }
    };
}
