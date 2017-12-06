package cn.tim.thinking.enums;

import org.junit.Test;

import java.util.EnumSet;

/**
 * Created by LuoLiBing on 16/12/12.
 * EnumSet
 *
 * Set不允许重复对象, 而Enum中不能删除或添加元素.
 * JAVA5引入EnumSet,是为了通过enum创建一种替代品,以替代传统的基于int的"位标志".
 * 这种标志可以用来表示某种"开/关"信息
 * 使用EnumSet的有点是: 它在说明一个二进制位是否存在时,具有更好的表达能力,并且无需担心性能.
 *
 * EnumSet实现原理:
 * 判断Enum元素数量,如果小于等于64, 则使用一个long值来标志, long值可以拆分为8字节64位二进制, 然后通过0/1来标记有或者没有
 * 如果大于64,则使用long[]数组来标志
 *
 * EnumSet底层:
 *
 * elementType枚举类型
 * universe所有组成T的元素(缓存提升性能)
 * 使用elements位操作移位的方式实现添加或者删除元素等等操作
 *
 */
public class EnumSetDemo1 {

    enum AlarmPoints {
        START1, START2, LOBBY, OFFICE1, OFFICE2, OFFICE3, OFFICE4, BATHROOM, UTILITY, KITCHEN;
    }

    @Test
    public void enumSets() {
        // 返回一个空的EnumSet
        EnumSet<AlarmPoints> points = EnumSet.noneOf(AlarmPoints.class);
        System.out.println("noneOf = " + points);
        // 添加的时候讲BATHROOM标记为1
        points.add(AlarmPoints.BATHROOM);
        // addAll添加所有元素, EnumSet.of()
        points.addAll(EnumSet.of(AlarmPoints.START1, AlarmPoints.START2, AlarmPoints.KITCHEN));
        System.out.println("addAll = " + points);
        // 获取所有的Enum元素, allOf()
        points = EnumSet.allOf(AlarmPoints.class);
        System.out.println("allOf = " + points);
        // removeAll()删除所有, EnumSet.range(from, to)获取from(包含)到to(包含)之间所有的枚举元素
        points.removeAll(EnumSet.range(AlarmPoints.OFFICE1, AlarmPoints.OFFICE4));
        System.out.println("removeAll = " + points);
        // 获取补集
        points = EnumSet.complementOf(points);
        System.out.println("complementOf = " + points);
    }
}
