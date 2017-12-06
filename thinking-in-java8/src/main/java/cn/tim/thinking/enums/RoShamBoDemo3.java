package cn.tim.thinking.enums;

/**
 * Created by LuoLiBing on 16/12/13.
 * 常量相关方法的多路分发实现
 */
public class RoShamBoDemo3 {

    enum RoShamBo3 implements RoShamBoDemo2.Competitor<RoShamBo3> {
        // 通过各自实现compete方法来实现第一次分发,  然后通过switch()来实现第二次分发
        PAPER {
            @Override
            public RoShamBoDemo2.OutCome compete(RoShamBo3 competitor) {
                switch (competitor) {
                    default:
                    case PAPER: return RoShamBoDemo2.OutCome.DRAW;
                    case SCISSORS: return RoShamBoDemo2.OutCome.LOSE;
                    case ROCK: return RoShamBoDemo2.OutCome.WIN;
                }
            }
        },

        SCISSORS {
            @Override
            public RoShamBoDemo2.OutCome compete(RoShamBo3 competitor) {
                switch (competitor) {
                    default:
                    case PAPER: return RoShamBoDemo2.OutCome.WIN;
                    case SCISSORS: return RoShamBoDemo2.OutCome.DRAW;
                    case ROCK: return RoShamBoDemo2.OutCome.LOSE;
                }
            }
        },

        ROCK {
            @Override
            public RoShamBoDemo2.OutCome compete(RoShamBo3 competitor) {
                switch (competitor) {
                    default:
                    case PAPER: return RoShamBoDemo2.OutCome.LOSE;
                    case SCISSORS: return RoShamBoDemo2.OutCome.WIN;
                    case ROCK: return RoShamBoDemo2.OutCome.DRAW;
                }
            }
        };

        @Override
        public abstract RoShamBoDemo2.OutCome compete(RoShamBo3 competitor);

        public static void main(String[] args) {
            RoShamBoDemo2.OutCome compete = PAPER.compete(ROCK);
            System.out.println(compete);
        }
    }


    // 压缩版, 不需要再每个方法都使用switch case方法
    enum RoShamBo4 implements Competitor<RoShamBo4> {
        ROCK {
            public RoShamBoDemo2.OutCome compete(RoShamBo4 opponent) {
                return compete(SCISSORS, opponent);
            }
        },

        SCISSORS {
            @Override
            public RoShamBoDemo2.OutCome compete(RoShamBo4 opponent) {
                return compete(PAPER, opponent);
            }
        },

        PAPER {
            @Override
            public RoShamBoDemo2.OutCome compete(RoShamBo4 opponent) {
                return compete(ROCK, opponent);
            }
        };

        // 可以打败的对手
        public RoShamBoDemo2.OutCome compete(RoShamBo4 loser, RoShamBo4 opponent) {
            // 如果一样,则返回DRAW
            if(opponent == this) {
                return RoShamBoDemo2.OutCome.DRAW;
            }
            return opponent == loser ? RoShamBoDemo2.OutCome.WIN : RoShamBoDemo2.OutCome.LOSE;
        }

        public abstract RoShamBoDemo2.OutCome compete(RoShamBo4 opponent);
    }

    interface Competitor<T extends Competitor<T>> {
        RoShamBoDemo2.OutCome compete(RoShamBo4 opponent, RoShamBo4 roShamBo4);
    }
}
