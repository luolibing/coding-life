package cn.hotspot.memory;

/**
 * Created by LuoLiBing on 17/1/21.
 * 栈帧的结构
 * 1 局部变量表
 * 2 操作数堆栈
 * 3 动态链接
 * 4 正常方法完成
 * 5 突发方法完成
 *
 */
public class StackFrame {

    /**
     * 一 局部变量表
     * 每个帧都包含一个称为局部变量表的变量数组. 帧的局部变量数组的长度在编译期就确定了, 并以类或接口的二进制表示法以及帧相关的方法的代码提供.
     * 单个局部变量可以保存基本类型和引用类型或者returnAddress类型的值. long或double类型的值需要2个slot槽进行保存.
     * 局部变量通过索引来寻址, 第一个局部变量为0. long或double类型将占用两个连续的局部变量slot. 这样的值只能使用较小的索引来解决. 例如n和n+1存储long. 但是索引n+1处的局部变量不能从中加载.
     * JVM不需要n为偶数, 不需要在局部变量数组中64位对齐.
     * JVM使用局部变量来传递方法调用的参数. 在类方法调用中, 任何参数都在从局部变量0开始的连续局部变量中传递. 在实例方法调用中, 局部变量0总是用于传递this, 任何参数都是从局部变量1开始的连续局部变量中传递.
     *
     * 局部变量表包含:
     * 1 实例方法, 第一个是this, 索引值为0
     * 2 参数
     * 3 方法调用过程中的中间变量
     *
     */
    public void variableTable() {

    }


    /**
     * 二 操作数堆栈(Operand Stacks)
     * 每个帧包含称为其操作数栈的后进先出堆栈, 帧的操作数堆栈的最大深度在编译时确定, 并且与用于与帧相关联的方法的代码一起提供.
     * 当包含操作数堆栈的栈帧时, 操作数堆栈为空. JVM提供了将常量或值从局部变量或字段加载到操作数栈上的指令. 其他Jvm指令从操作数堆栈取操作数, 对他们进行操作, 并将结果推回到操作数堆栈. 操作数堆栈还用于准备要传递给方法和接收方法结果的参数.
     * 操作数栈的操作
     * 1 将常量或值从局部变量或字段加载到操作数栈       aload_0, bipush 100 (加载常量)
     * 2 从操作数栈取操作数
     * 3 将结果推回到操作数栈
     * 4 用来传递参数给方法
     * 5 接收结果然后返回                           bipush 100;
     *
     * 来自操作数堆栈的值必须以符合其类型的方式操作. 例如, 不可能推送两个int值, 然后将他们视为long或推送两个浮点值, 然后使用iadd指令将他们相加.
     * 少量JVM指令(dup和swap)在运行时数据区上做原始值操作, 而不考虑其特定类型. 这些限制通过类文件验证来实现.
     * 在任何时间点, 操作数堆栈具有相关联的深度, 其中long和double的值对深度贡献两个单位, 其他任何类型的值贡献一个单位.
     */
    public void operandStacks() {
        /**
         * aload_0, 将this引用加载到操作数栈当中
         *
         * stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
         */

        /**
         * bipush, 加载常量
         * 0: aload_0
         1: bipush        100
         3: putfield      #8                  // Field f:I
         6: return
         */

        /**
         * 从操作数堆栈中取操作数, iadd会将刚才加载到操作数堆栈的操作数取出, 然后进行iadd操作
         * 17: iload_1
         18: iload_2
         19: iadd
         */

        /**
         * pop将栈顶元素pop出返回
         *
         */
    }


    /**
     * 三 动态链接: 针对多态
     * 每个帧都包含对运行时常量池的引用, 用于支持方法代码的动态链接的当前方法的类型(方法调用的实际类型).
     * 动态链接将代码和符号引用转换为具体的方法引用, 根据需要加载类以解析尚未定义的符号, 并将变量访问转换为与这些变量的运行时位置相关联的存储结构中的适当偏移量.
     * 这种方法和变量的后期绑定使得方法使用不太可能破坏该代码的其他类的更改.
     */
    public void dynamicLink() {

    }


    /**
     * 四 正常方法调用完成 (Normal Method Invocation Completion)
     * 如果调用不直接从Java虚拟机或由于执行显式throw语句而导致抛出异常, 则方法调用通常完成. 如果当前方法的调用正常完成, 则可以将返回值返回到调用方法的操作数栈.
     * 当被调用的方法执行返回指令(invokevirtual, invokeinterface, invokespecial, invokestatic, invokedynamic)之一时, 会发生这种情况, 其选择必须适合于返回值的类型.
     *
     */
    public void normalMethod() {

    }


    /**
     * 五 突发方法调用完成
     * 如果方法中的java虚拟机指令的执行导致Java虚拟机抛出异常, 并且在该方法中未处理该异常, 则方法调用突然完成, 执行athrow指令也会导致明确抛出异常. 突然完成的方法调用从不向其调用者返回值
     */
    public void abruptMethod() {

    }


    public int getNum() {
        return 10;
    }

    public int add(int a, int b) {
        return (a + b) * getNum();
    }

    public static void main(String[] args) {
        StackFrame stackFrame = new StackFrame();
        int sum = stackFrame.add(10, 20);
        System.out.println(sum);
    }
}
