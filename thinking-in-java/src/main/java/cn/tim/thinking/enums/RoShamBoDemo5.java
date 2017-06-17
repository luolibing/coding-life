package cn.tim.thinking.enums;

/**
 * Created by LuoLiBing on 16/12/13.
 * 二维数组实现多路分发: 可能是最简洁, 最快速的实现
 *
 * 优雅与清晰共存的解决方案才是最好的解决方案.
 */
public class RoShamBoDemo5 {

    enum RoShamBo6 implements Competitor<RoShamBo6> {
        PAPER, SCISSORS, ROCK;

        private static RoShamBoDemo2.OutCome[][] table = {
                {RoShamBoDemo2.OutCome.DRAW, RoShamBoDemo2.OutCome.LOSE, RoShamBoDemo2.OutCome.WIN},
                {RoShamBoDemo2.OutCome.WIN, RoShamBoDemo2.OutCome.DRAW, RoShamBoDemo2.OutCome.LOSE},
                {RoShamBoDemo2.OutCome.LOSE, RoShamBoDemo2.OutCome.WIN, RoShamBoDemo2.OutCome.DRAW}
        };

        @Override
        public RoShamBoDemo2.OutCome compete(RoShamBo6 roShamBo4) {
            return table[this.ordinal()][roShamBo4.ordinal()];
        }

        public static void main(String[] args) {
            System.out.println(PAPER.compete(SCISSORS));
        }
    }


    interface Competitor<T extends Competitor<T>> {
        RoShamBoDemo2.OutCome compete(RoShamBo6 roShamBo4);
    }
}
