package cn.tim.thinking.enums;

import java.util.EnumMap;

/**
 * Created by LuoLiBing on 16/12/13.
 * 使用EnumMap实现多路分发
 * 类似于使用成员RoShamBoDemo2,只是他使用的是成员变量,而这里使用的是EnumMap
 */
public class RoShamBoDemo4 {

    // 使用EnumMap嵌套的方式解决
    enum RoShamBo5 implements Competitor<RoShamBo5> {
        PAPER, SCISSORS, ROCK;

        static EnumMap<RoShamBo5, EnumMap<RoShamBo5, RoShamBoDemo2.OutCome>> table = new EnumMap<>(RoShamBo5.class);

        static {
            for(RoShamBo5 it : values()) {
                table.put(it, new EnumMap<>(RoShamBo5.class));
            }
            initRow(PAPER, RoShamBoDemo2.OutCome.DRAW, RoShamBoDemo2.OutCome.LOSE, RoShamBoDemo2.OutCome.WIN);
            initRow(SCISSORS, RoShamBoDemo2.OutCome.WIN, RoShamBoDemo2.OutCome.DRAW, RoShamBoDemo2.OutCome.LOSE);
            initRow(ROCK, RoShamBoDemo2.OutCome.LOSE, RoShamBoDemo2.OutCome.WIN, RoShamBoDemo2.OutCome.DRAW);
        }

        static void initRow(RoShamBo5 it, RoShamBoDemo2.OutCome vPaper,
                            RoShamBoDemo2.OutCome vScissors, RoShamBoDemo2.OutCome vRock) {
            EnumMap<RoShamBo5, RoShamBoDemo2.OutCome> row = RoShamBo5.table.get(it);
            row.put(PAPER, vPaper);
            row.put(SCISSORS, vScissors);
            row.put(ROCK, vRock);
        }

        @Override
        public RoShamBoDemo2.OutCome compete(RoShamBo5 roShamBo4) {
            // 一句代码中两次分发
            return table.get(this).get(roShamBo4);
        }

        public static void main(String[] args) {
            System.out.println(PAPER.compete(ROCK));
        }
    }

    interface Competitor<T extends Competitor<T>> {
        RoShamBoDemo2.OutCome compete(RoShamBo5 roShamBo4);
    }
}
