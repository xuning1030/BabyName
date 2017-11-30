package babyname.babyname.Utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/4/12.
 *
 * 数据库操作类
 */

public class DatabaseUtils {
    private static  MyOpenHelper mHelper;

    private DatabaseUtils(){
    }

    /**
     * 一般来说这里的initHelper放到application中去初始化
     * 当然也可以在项目运行阶段初始化
     */
    public static void initHelper(Context context, String name){
        if(mHelper == null){
            mHelper = new MyOpenHelper(context,name);
        }
    }
    public static MyOpenHelper getHelper(){
        if(mHelper == null){
            new RuntimeException("MyOpenHelper is null,No init it");
        }
        return mHelper;
    }


    /**
     * 添加数据测试新建一个user.db文件
     *
     * //必须先初始化
     DatabaseUtils.initHelper(this,"user.db");
     //创建学生类
     Student student1 = new Student("张三","1001",12);
     //将学生类保存到数据库
     DatabaseUtils.getHelper().save(student1);
     *
     */


    /**
     *
     * 添加bean结构类
     */


    /**
     * 测试添加数据比如学生类
     *  List<Student> list = new ArrayList<>();
     list.add(new Student("李四","1002",13));
     list.add(new Student("王五","1003",23));
     list.add(new Student("赵六","1004",21));
     list.add(new Student("钱七","1005",20));
     DatabaseUtils.getHelper().saveAll(list);
     *
     *
     */

    /**
     * 测试查询数据
     * List<Student> list = DatabaseUtils.getHelper().queryAll(Student.class);
     Log.d("TAG", "onCreate: " + list.size());//查询Student类的总长度
     for (Student student : list) {
     Log.d("TAG", "onCreate: " + student);//分别打印student类中的信息
     }
     *
     *
     */

}
