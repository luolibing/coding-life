package cn.tim.groovy1.mop1.mixin

/**
 * Created by LuoLiBing on 15/10/17.
 */
abstract class Writer {
    abstract void write(String message)
}

class StringWriter extends Writer {

    def target = new StringBuilder()

    @Override
    void write(String message) {
        target.append(message)
    }

    String toString() { target.toString() }

}
