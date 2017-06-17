package cn.design.patterns.composite.composite;

import java.util.Iterator;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Created by LuoLiBing on 16/6/20.
 * 组合迭代器
 * 迭代器里面有许多的迭代器,递归遍历里面最小的单元
 */
public class CompositeIterator implements Iterator {

    // 将我们要遍历的集合放入到栈中
    private Stack stack = new Stack();

    public CompositeIterator(Iterator iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        // 判断栈是否为空
        if(stack.empty()) {
            return false;
        } else {
            // 从栈中弹出迭代器取出元素,但不pop
            Iterator it = (Iterator) stack.peek();
            // 单个元素不包含任何下一个元素,可以直接返回true,组合元素,判断是否有下一个元素,如果没有包含下一个元素,就把menu弹出栈,继续判断下一个是否存在
            if(!it.hasNext()) {
                stack.pop();
                // 继续递归
                return hasNext();
            } else {
                return true;
            }
        }
    }

    @Override
    public Object next() {
        if(hasNext()) {
            Iterator it = (Iterator) stack.peek();
            MenuComponent component = (MenuComponent) it.next();
            if(component instanceof Menu) {
                stack.push(component.createIterator());
            }
            return component;
        } else {
            return null;
        }
    }

    public void addIterator(Iterator iterator) {
        stack.push(iterator);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEachRemaining(Consumer action) {
        throw new UnsupportedOperationException();
    }
}
