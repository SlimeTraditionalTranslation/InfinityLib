package io.github.mooy1.infinitylib.math;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MathUtils {
    
    /**
     * Integer exponents
     */
    public static int pow(int a, int b) {
        int i = 1;
        while (b > 0) {
            i*= a;
            b--;
        }
        return i;
    }
    
    /**
     * Modulo when b is a power of 2
     */
    public static int modPow2(int a, int b) {
        return a & (b - 1);
    }
    
    /**
     * Finds log base 2 of a power of 2 exactly or the ceiling for any other number.
     */
    public static int ceilLog2(int a) {
        return 32 - Integer.numberOfLeadingZeros(a - 1);
    }

    /**
     * Finds log base 2 of a power of 2 exactly or the floor for any other number.
     */
    public static int floorLog2(int a) {
        return 31 - Integer.numberOfLeadingZeros(a);
    }
    
}