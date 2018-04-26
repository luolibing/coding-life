package cn.tim.thinking.annotation;

import cn.tim.thinking.annotation.db.*;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoLiBing on 17/1/12.
 * 生成外部文件
 *
 * 像EJB3, WebService, 自定义标签库以及ORM(Hibernate和Toplink)等, 一般都需要XML描述文件, 而这些描述文件脱离了源代码之外.
 * 使用注解, 可以将所有的信息都保存在JavaBean源文件中, 为此需要一些新的注解, 用以定义Bean关联的数据库表的名字, 以及与Bean属性关联的列的名字和SQL类型.
 */
public class ExternalFileDemo {


    /***
     * 变通之道:
     * 1 使用单一的注解类@TableColumn(), 它带有一个enum元素, 该枚举类定义了STRING\INTEGER以及FLOAT等枚举类型. 这样消除了每个SQL类型都需要一个@interface的负担
     * 2 同时使用两个注解类型来注解一个域, @Constraints和响应的SQL类型. 这种方式可能会使代码有点乱, 不过编译器允许程序员对一个目标同时使用多个注解
     *
     * 注解不支持继承
     */
    @Test
    public void tableCreator() {
        Class<Member> clazz = Member.class;
        DBTable dbTable = clazz.getAnnotation(DBTable.class);
        if(dbTable == null) {
            System.out.println("No DBTable annotatons in class " + clazz.getName());
            return;
        }

        // 表名的生成
        String tableName = dbTable.name();
        if(tableName.length() < 1) {
            tableName = clazz.getName().toUpperCase();
        }

        List<String> columnDefs = new ArrayList<>();
        // 通过找到字段上的注解动态生成表名, 字段名, 以及字段属性
        for(Field field : clazz.getDeclaredFields()) {
            String columnName;
            Annotation[] annotations = field.getDeclaredAnnotations();
            if(annotations.length < 1) {
                continue;
            }

            // 判断是否是SQLInteger类型字段
            if(annotations[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) annotations[0];
                if(sInt.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sInt.name();
                }
                columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
            }

            if(annotations[0] instanceof SQLString) {
                SQLString sString = (SQLString) annotations[0];
                if(sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sString.name();
                }
                columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraints()));
            }
        }

        StringBuilder createCommand = new StringBuilder(String.format("CREATE TABLE %s(", tableName));
        for(String columnDef : columnDefs) {
            createCommand.append(String.format("\n  %s,", columnDef));
        }
        String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";
        System.out.println("Table.Creation SQL for " + clazz.getName() + " is : \n" + tableCreate);
    }

    public static String getConstraints(Constraints con) {
        String constraints = "";
        if(!con.allowNull()) {
            constraints += " NOT NULL";
        }

        if(con.primaryKey()) {
            constraints += " PRIMARY KEY";
        }

        if(con.unique()) {
            constraints += " UNIQUE";
        }
        return constraints;
    }
}
