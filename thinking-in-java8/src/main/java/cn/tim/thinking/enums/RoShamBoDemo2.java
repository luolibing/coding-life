package cn.tim.thinking.enums;

/**
 * Created by LuoLiBing on 16/12/13.
 * 枚举多路分发
 */
public class RoShamBoDemo2 {

    public enum OutCome { WIN, LOSE, DRAW }

    interface Competitor<T extends Competitor<T>> {
        OutCome compete(T competitor);
    }

    // 通过固定位置: 布/剪刀/石头来事先将对比结果记录到对应的结果字段
    enum RoShaBo2 implements Competitor<RoShaBo2> {
        // 根据位置记录记录对比的结果
        PAPER(OutCome.DRAW, OutCome.LOSE, OutCome.WIN),
        SCISSORS(OutCome.WIN, OutCome.DRAW, OutCome.LOSE),
        ROCK(OutCome.LOSE, OutCome.WIN, OutCome.DRAW);

        private OutCome vPaper, vScissors, vRock;

        // 每个结果对应着与对应枚举对比的结果  LOSE还是 WIN
        RoShaBo2(OutCome paper, OutCome scissors, OutCome rock) {
            this.vPaper = paper;
            this.vScissors = scissors;
            this.vRock = rock;
        }

        public OutCome compete(RoShaBo2 it) {
            switch (it) {
                default:
                case PAPER: return vPaper;
                case SCISSORS: return vScissors;
                case ROCK: return vRock;
            }
        }

        public static void main(String[] args) {
            // 第一次分发是通过PAPER判断具体调用的方法, 第二次分发是在switch()中
            OutCome compete = PAPER.compete(SCISSORS);
            System.out.println(compete);
        }
    }
}
