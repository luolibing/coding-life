package cn.tim.thinking.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Created by LuoLiBing on 16/12/11.
 * Preferences用于存储和读取用户的偏好以及程序配置项的设置
 * Preferences是一个键值对集合(类似映射),存储在一个节点层次结构中.
 * Preferences数据是利用合适的系统中员完成了这个任务,这些配置都保存在操作系统中,所以下一次运行的时候,这些配置还能照样读取到
 *
 * IO总结
 * IO流类库的确能满足我们的基本需求: 我们可以通过控制台,文件,内存块,设置网络进行读写.通过集成我们可以创建新类型的输入和输出对象.并且通过重新定义toString()方法,我们可以对流接受的对象进行简单的扩充.
 * IO流类库也有一些没有解决的问题.例如当我们打开一个文件用于输出时,我们可以指定一旦试图覆盖该文件就抛出一个异常. 而在java中并不会这样,我们以FileOutputStream或者FileWriter打开,那么它肯定会被覆盖.
 * IO流类库使用了过多的装饰器模式,这样会导致写一个读取或者写入嵌套太多层装饰.
 */
public class PreferencesDemo {

    public static void main1(String[] args) throws BackingStoreException {
        // 这里用的是userNodeForPackage(),也可以选择用systemNodeForPackage();
        Preferences preferences = Preferences.userNodeForPackage(PreferencesDemo.class);
        preferences.put("Location", "Oz");
        preferences.put("Footwear", "Ruby Slippers");
        preferences.putInt("Companions", 4);
        preferences.putBoolean("Are there witches?", true);
        // 0为默认值
        int usageCount = preferences.getInt("UsageCount", 0);
        usageCount++;
        // UsageCount会每次都自增1
        preferences.putInt("UsageCount", usageCount);

        for(String key : preferences.keys()) {
            System.out.println(key + ": " + preferences.get(key, null));
        }
        System.out.println("How many Companions does Dorothy have? " + preferences.getInt("Companions", 0));
    }


    public static void main(String[] args) throws IOException {
        Preferences preferences = Preferences.userNodeForPackage(PreferencesDemo.class);
        String directory = preferences.get("base directory", "Not defined");
        System.out.println("directory: " + directory);
//        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a new directory: ");
        directory = reader.readLine();
        preferences.put("base directory", directory);
        System.out.println("\ndirectory " + directory);
    }
}
