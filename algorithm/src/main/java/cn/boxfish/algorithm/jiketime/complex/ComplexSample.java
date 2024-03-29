package cn.boxfish.algorithm.jiketime.complex;

/**
 * 复杂度计算
 */
public class ComplexSample {

    static class ComplexTest1 {

        // 读取计算写入，作为一个单位时间的话，则下面需要 2 + 2n个时间
        public static void test1(int n) {
            int sum = 0;
            int i = 1;
            for(; i <= n; i++) {
                sum += i;
            }
        }

        /**
         * 需要 2 + 2n^2 + 2n
         * T(n) = O(f(n)); T(n)和每行代码的执行次数成正比，假定每行代码执行的时间相同。
         * 则第一个 T(n) = O（2 + 2n），第二个T(n) = O（2 + 2n^2 + 2n）
         * 时间复杂度并不是指这些代码需要执行的时间长度，而是指执行时间和数据规模之间增长渐进趋势。
         * 当n为一个很大的量时，低阶，常数，系数都可以忽略不计，直接取最大量级即可，则这2个算法的时间复杂度分别为o(n)和o(n2)
         *
         * 时间复杂度计算方法：
         * 1 关注循环次数最大的一段代码，忽略常数次，系数，低阶
         * 2 +法原则，列出所有的片段的和，关注最大代码片段
         * 3 *法原则，嵌套的时候，等于内外嵌套的乘积
         *
         */
        public static void test2(int n) {
            int sum = 0;
            int i = 1;
            for(; i <= n; i++) {
                int j = 0;
                for(; j <=n; j++) {
                    sum += i + j;
                }
            }
        }
    }


    static class ComplexTest2 {

        /**
         * o(1)是指执行次数不随n增长而变大，而不是指执行多少次，只要不随n增长，
         * 即使成千上万行代码也是o(1)，下面的时间复杂度为o(1)而不是
         */
        public static void o1() {
            int i = 1;
            int j = 3;
            int sum = i + j;
        }

        /**
         * i以2的倍数增长，实际上就是一个等比数列，2^k = n；次数k = log2n。那如果不是*2，而是*3呢，次数k = log3n，*10 = log10n
         * 但是在记录的时候只记做logn，因为log3n = log32 * log2n，而因为系数是可以忽略的，所以不管logmn，都记为o(logn)
         * @param n
         */
        public static void logn(int n) {
            int i = 1;
            while (i <= n) {
                i = i * 2;

            }
        }

        public static void nlogn(int n) {
            for(int j = 0; j < n; j++) {
                int i = 1;
                while (i <= n) {
                    i = i * 2;
                }
            }
        }

        /**
         * 还有log(m+n) log(mn)
         */
    }

}
