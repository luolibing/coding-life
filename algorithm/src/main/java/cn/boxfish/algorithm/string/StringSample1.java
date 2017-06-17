package cn.tim.algorithm.string;

/**
 * Created by LuoLiBing on 17/2/22.
 * 给定一个字符串，要求把字符串前面的若干个字符移动到字符串的尾部，
 * 如把字符串“abcdef”前面的2个字符'a'和'b'移动到字符串的尾部，使得原字符串变成字符串“cdefab”。
 *
 */
public class StringSample1 {

    private char[] str;

    public StringSample1(char[] str) {
        this.str = str;
    }

    public StringSample1() {
    }

    public void reverse(int i, int j) {
        while (i < j) {
            char temp = str[i];
            str[i] = str[j];
            str[j] = temp;
            i++;
            j--;
        }
    }

    public void reverseAll(int pre) {
        reverse(0, pre - 1);
        reverse(pre, str.length-1);
        reverse(0, str.length-1);
    }

    /**
     * 先分段旋转, 然后再整体旋转
     * @param args
     */
    public static void main(String[] args) {
        StringSample1 sample1 = new StringSample1("abcdefghijklmnopq".toCharArray());
        sample1.reverseAll(5);
        System.out.println(new String(sample1.str));
    }
}
