package cn.tim.thinking.collection.simple11;

/**
 * Created by LuoLiBing on 16/9/21.
 * 1 数组将数字和对象联系起来,数组一旦生成,其容量就不能改变
 * 2 Collection保存单一元素,而Map保存相关联的键值对.各种Collection和各种Map都可以在添加更多元素时,自动扩容,但是会损耗性能.容器不能持有基本类型,但是可以自动拆装箱自动进行切换
 * 3 和数组一样,List也简历数字索引和对象之间的关联,数组和List都是排好序的容器,List能够自动扩容
 * 4 如果要进行大量随机访问,就使用ArrayList;如果要经常从表中间插入或者删除元素,则使用LinkedList
 * 5 各种Queue和栈的行为,由LinkedList提供支持
 * 6 Map是一种将对象与对象相关联的设计(数组是数字和对象).HashMap设计用来快速访问;TreeMap保持键始终处于排序状态;LinkedHashMap保持元素插入的顺序;
 * 7 Set不接受重复元素,HashSet提供最快的查询速度,而TreeSet保持元素处于排序状态;LinkedHashSet以插入顺序保存元素
 * 8 Vector,HashTable,Stack都已经过时
 */
public class Summary {
}
