package cn.tim.thinking.access;

import cn.tim.thinking.ArrayListUtils;

/**
 * Created by LuoLiBing on 16/12/13.
 * 访问权限修饰的目的是为了将变动的事物和保持不变的事物区分开来. 达到重构代码不会影响到代码客户端使用者,又能让类库开发者能够方便的修改重构代码.
 * 权限从大到小分别为: public\protected\包访问权限\和private.
 *
 * 包: 库单元
 * 管理名字空间的机制, 用包对属于同一个群组的构件进行封装. java包机制利用了文件系统目录文件结构来解决.
 *
 * java解释器的运行过程:
 * 首先找到环境变量CLASSPATH, 该变量包含一个或多个目录,用作查找.class文件的根目录.
 * 从根目录开始,解释器获取包的名称并将每个句点替换成斜杠,并且加上CLASSPATH中的目录,
 * 这样就可以找到对应的.class文件是否存在, 并且找到对应的.class文件
 * 例如本类:
 * /Users/tim/Documents/samples-tim/thinking-in-java/target/classes/cn/tim/thinking/access/AccessDemo1.class
 *
 * 当编写一个java源代码文件.这个文件称为编译单元,每个编译单元最多只能有一个public类,并且文件名与public类名必须一样
 *
 *
 * JAVA访问权限修饰词
 * 包访问权限:
 * 默认访问权限没有任何关键字,即为包访问权限, 包访问权限允许将包内所有相关的类组合起来,以使它们彼此之间可以轻松相互访问.
 *
 * 取得对某成员的访问权的唯一途径
 * 1 使该成员称为public.无论是谁,无论在哪里都可以访问该成员
 * 2 将类至于同一个包中,使用默认的包访问权限
 * 3 继承, 继承即可以访问public成员也可以访问protected成员,但private不行
 * 4 get/set方法
 *
 *
 * public : 接口访问权限
 *
 * private: 你无法访问
 *
 * protected: 继承访问权限, (同时包含了包访问权限)
 *
 *
 * 接口与实现:
 * 访问权限的控制称为具体实现的隐藏.把数据和方法包装进类中,以及具体实现的隐藏,共同称为封装
 * 封装的原因:
 * 1 设定客户端程序员可以使用和不可以使用的界限. 这样类库开发者可以建立自己的内部机制,而不必担心客户端程序员会偶然地将内部机制当做他们可以使用接口的一部分
 * 2 将接口和具体实现进行分离. 这样客户端程序员除了向接口发送信息之外什么都不可以做, 这样我们就可以随意改变实现,而不会破坏客户端代码.
 *
 * 代码规范: 一般先 public/ 包访问 / protected/ private成员
 *
 * 类访问权限: 只有包访问权限和public
 * 类访问权限的限制:
 * 1 每个编译单元多只能有一个public类
 * 2 public类的名称必须与含有该编译单元的文件名完全匹配
 * 3 编译单元内不带public类也是可以的
 *
 *
 *
 *
 */
public class AccessDemo1 {

    public static void main(String[] args) {

        ArrayListUtils.sayHello();
    }

    static class Cookie {
        public Cookie() {
            System.out.println("Cookie constructor");
        }

        protected void bite() {
            System.out.println("bite");
        }
    }

    static class ChocolateChip2 extends Cookie {
        public ChocolateChip2() {
            System.out.println("ChocolateChip2 constructor");
        }

        // 通过提供一个修饰访问向外暴露protected方法
        public void chomp() {
            bite();
        }

        public static void main(String[] args) {
            ChocolateChip2 x = new ChocolateChip2();
            x.chomp();
        }
    }

    static class Soup1 {
        private Soup1() {}

        public static Soup1 makeSoup() {
            return new Soup1();
        }
    }

    static class Soup2 {
        private static Soup2 soup2 = new Soup2();

        private Soup2() {}

        public static Soup2 access() {
            return soup2;
        }

        public void f() {}
    }
}
