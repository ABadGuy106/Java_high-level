# Java语言高级开发

## 多线程



### 线程的创建和使用

#### 线程创建方式

##### 继承Thread类，重写run方法

##### 实现Runnable接口，实现run方法

##### 实现Callable接口

- 与使用Runnable相比，Callable功能更强大些：
- 相比run方法，可以有返回值
- 方法可以抛出异常
- 支持泛型的返回值
- 需要借助FutureTask类，比如获取返回结果

**Future接口**

- 可以对具体Runable、Callable任务的执行结果进行取消、查询时否完成、获取结果等
- FutrueTask时Futrue接口的唯一的实现类
- FutureTask同时实现了Runnable，Futrue接口。它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CallnableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NumThread numThread = new NumThread();
        FutureTask futureTask = new FutureTask(numThread);
        new Thread(futureTask).start();

        Object o = futureTask.get();
        System.out.println(o);
    }
}
class NumThread implements Callable{

    public Object call() throws Exception {
        int sum=0;
        for (int i = 0; i <= 100 ; i++) {
            if(i%2==0){
                System.out.println(i);
                sum+=i;
            }
        }
        return sum;
    }
}
```

##### 线程池创建

背景：经常创建和销毁、使用量特别大的资源，比如并发情况下的线程对性能影响很大

思路：提前创建好多个线程，放入线程池中，使用时直接获取，使用完放回池总。可以避免频繁创建销毁、实现重复利用。类似生活中公共交通工具

好处：

提高响应速度(减少了创建新线程的时间)

降低资源消耗(重复利用线程池中线程，不需要每次都创建)

便于线程管理

​	corePoolSize:核心线程池的大小

​	maximumPoolSize:最大线程数

​	keepAliveTime:线程没有任务时最多保持多长时间后会终止

###### 线程池创建线程的方法

**ExecutorService**:真正的线程池接口。常见子类ThreadPoolExecutor

- void execute(Runnable command): 执行任务/命令，没有返回值，一般用来执行Runnable
- <T>Future<T> submint(Callable<T> task): 执行任务，又返回值，一般用来执行Callable
- void shutdown(): 关闭连接池



**Executors**:工具类、线程池的工厂类，用于创建并返回不同类型的线程池

- Executors.newCachedThreadPool(): 创建一个可根据需要创建新线程的线程池
- Executors.newFixedThreadPool(n): 创建一个可重用固定线程数的线程池
- Executors.newSingleThreadExecutor(): 创建一个只有一个线程的线程池
- Executors.newScheduledThreadPool(n): 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行

```java
public class ThreadPool {
    public static void main(String[] args) {
        //提供指定线程数量的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //设置线程的属性
        ThreadPoolExecutor threadPoolExecutor= (ThreadPoolExecutor) executorService;

        threadPoolExecutor.setCorePoolSize(10);
        
        //执行指定的线程操作
        executorService.execute(new NumberThread());//适用于Runnable接口
        executorService.execute(new NumberThread1());
//        executorService.submit();//适用于Callable接口

        //关闭连接池
        executorService.isShutdown();
    }
}


class NumberThread implements Runnable{

    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}
class NumberThread1 implements Runnable{

    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        }
    }
}
```

#### Thread类相关方法

- void start():	启动线程，执行对象的run()方法
- run():	线程在被调度时执行操作
- String getName():	返回线程名称
- void setName(String name):	设置该线程名称
- static Thread currentThread():	返回当前线程，在Thread子类中就是this,通常用于主线程和Runnable实现类
- yield():    释放当前线程的CPU执行权
- join() :     在线程A中调用线程B的join()，此时线程A就进入阻塞状态，直到线程B完全执行玩抽，线程A才结束阻塞状态
- stop():     强制线程生命周期结束，不推介使用
- sleep(Long millitime):    让当前线程“睡眠”执行的millitime毫秒
- boolean isAlive():    判断当前线程是否存活
- wait():   使得当前线程进入阻塞状态，释放锁。必须使用在同步代码块或者同步方法中
- notify():    唤醒被wait的一个线程，如果哟u多个线程被wait,就唤醒优先级较高的那个线程。必须使用在同步代码块或者同步方法中
- notifyAll():    唤醒所有被wait的线程。必须使用在同步代码块或者同步方法中

#### sleep()和wait()的异同

相同点：一旦执行方法，都可以使得当前的线程进入阻塞状态。

不同点：1）两个方法声明的位置不同：Thread类中声明sleep()，Object类中声明wait(); 2)调用的范围要求不同：sleep()可以在任何需要的场景下调用，wait()必须使用在同步代码块或者同步方法中；3)关于是否释放同步监视器：如果两个方法都是用在同步代码块或同步方法中，sleep()不会释放锁，wait()会释放锁

#### 线程的生命周期

**JDK中用Thread.State类定义了线程的几种状态**

想要实现多线程，必须在主线程中创建新的线程对象。Java语言使用Thread类及其子类的对象来表示线程，在他的一个完整的生命周期中通常要经历如下的五种状态：

1. **新建**：当一个Thread类或其子类的对象被声明并创建时，新生的线程对象处于创建状态
2. **就绪**：当新建状态的线程被start()后，将进入线程队列等待CPU时间片，此时它以及具备了运行的条件，只是没有分配到CPU资源
3. **运行**：当就绪的线程被调度并获得CPU资源时，便进入运行状态，run()方法定义了线程的操作和功能
4. **阻塞**：在某种特殊情况下，被人为挂起或执行输入输出操作时，让出CPU并临时终止自己的执行，进入阻塞状态
5. **死亡**：线程完成了它的全部工作或线程被提前强制性地终止或出现异常导致结束

#### 线程的调度

##### 调度策略：

时间片

抢占模式: 高优先级的线程抢占CPU

##### Java的调度方法

同优先级线程组成先进先出队列(先到先服务)，使用时间片策略

对高优先级，使用优先调度的抢占式策略

##### 线程的优先级

**线程的优先等级**

- MAX_PRIORITY: 10
- MIN_PRIORITY: 1
- NORM_PRIORITY: 5

**涉及方法**

- getPriority():    返回线程优先值
- setPriority(int newPriority):    改变线程的优先级

**说明**

- 线程创建时继承父线程的优先级
- 低优先级只是或的调度的概率低，并非一定时在高优先级线程之后才被调用

### 线程的同步

##### 线程死锁问题

**死锁**

- 不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源，就形成了线程的死锁
- 出现死锁后，不会出现异常，不会出现提示，只是所有线程都处于阻塞状态，无法继续

**解决方法**

- 专门的算法、原则
- 尽量减少同步资源的定义
- 尽量避免嵌套同步

##### Lock(锁)

- 从JDK5.0开始，Java提供了更强大的线程同步机制——通过显示定义同步锁对象来实现同步。同步锁使用Lock对象充当
- java.util.concurrent.locks.Lock接口时控制多个线程对贡献资源进行访问的攻击。锁提供了对共享资源的独占访问，每次只能有一个线程对Lock对象加锁，线程开始访问共享资源之前应先获得Lock对象
- ReentrantLock类实现了Lock,它拥有与syschronized相同的并发性和内存语义，在实现线程安全的控制中，比较常用ReentrantLock，可以显示枷锁、释放锁

```java
class Window implements Runnable{

    private int ticket=100;

    //ReentrantLock创建对象时，传true表示为公平锁，先进先出。默认是false,不公平锁
    private ReentrantLock lock=new ReentrantLock(true);

    public void run() {
        while (true){
            try {
                //调用加锁方法
                lock.lock();
                if(ticket>0){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName()+": 售票,票号："+ticket);
                    ticket--;
                }else {
                    break;
                }
            }finally {
                //调用解锁方法
                lock.unlock();
            }
        }
    }
}


public class LockTest {
    public static void main(String[] args) {
        Window window = new Window();

        Thread t1 = new Thread(window);
        Thread t2 = new Thread(window);
        Thread t3 = new Thread(window);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

```

##### synchronized与lock的异同

**同：**

​	二者都可以解决线程安全问题

**不同：**

synchronized机制在执行完成相应的同步代码以后 ，自动释放同步监视器

lock需要手动的启动同步(lock())，同时结束同步也需要手动实现(unlock())

### 线程通信

生产者/消费者问题

生产者(Productor)将产品交给店员(Clerk),而消费者(Customer)从店员处取走产品，店员一次只能持有固定数量的产品(比如：20)，如果生产者试图生成更多的产品，店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者取走产品

```java
package net.abadguy;


/**
 * 生产者(Productor)将产品交给店员(Clerk),而消费者(Customer)从店员处取走产品，
 * 店员一次只能持有固定数量的产品(比如：20)，如果生产者试图生成更多的产品，
 * 店员会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；
 * 如果店中没有产品了，店员会告诉消费者等一下，如果店中有产品了再通知消费者取走产品
 *
 *
 * 分析：
 * 1、是否是多线程问题？是，生产者线程，消费者线程
 * 2、是否有共享数据？是，店员（或产品）
 * 3、如何解决线程安全问题？同步机制，有三种方法
 * 4、是否涉及线程通信？是
 */
public class ProductTest {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor p1 = new Productor(clerk);
        p1.setName("生产者1");
        Customer c1 = new Customer(clerk);
        c1.setName("消费者1");

        p1.start();
        c1.start();

    }
}

class Clerk{
    private int productCount=0;

    public synchronized void produceProduct() {
       if(productCount<20){
           productCount++;
           System.out.println(Thread.currentThread().getName()+"生产第"+productCount+"个产品");
           notify();
       }else {
           try {
               wait();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    public synchronized void consumerProduct()  {
        if(productCount>0){
            System.out.println(Thread.currentThread().getName()+"消费第"+productCount+"个产品");
            productCount--;
            notify();
        }else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Productor extends Thread{//生产者

    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk=clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始生产产品.......");
        while (true){
            clerk.produceProduct();
        }
    }
}

class Customer extends Thread{

    private Clerk clerk;

    public Customer(Clerk clerk) {
        this.clerk=clerk;
    }

    @Override
    public void run() {
        System.out.println(getName()+"开始消费产品....");
        while (true){
            clerk.consumerProduct();
        }
    }
}
```

## Java常用类

### 字符串相关类

#### String

##### **String的特性**

- String类：代表字符串。Java程序中的所有字符串字面值(如"abc")都作为此类的实例实现
- String是一个final类，代表不可变的字符序列。
- 字符串时常量，用双引号引起来表示。它们的值在创建之后不能改变
- String对象的字符内容时存储在一个字符数组value[]中的

##### String对象的创建

String str="hello";

String s1=new String();		//本质上this.value=new char[0];

String s2=new String(String original);		//this.value=original.value

String s3=new String(char[] a);		//this.value=Arrays.copyOf(value,value.lenght);

String s4=new String(char[] a,int startIndex,int count);

##### String的实例化方式

```java
public class StringTest {
    public static void main(String[] args) {
        //通过字面量定义的方式：此时的s1和s2的数据javaEE声明在方法区中的字符串常量池中
        String s1="javaEE";
        String s2="javaEE";
        //通过new+构造器的方式：此时的s3和s4保存的地址值，是数据在堆空间开辟空间以后对应的地址值
        String s3=new String("javaEE");
        String s4=new String("javaEE");

        System.out.println(s1==s2);//true
        System.out.println(s1==s3);//false
        System.out.println(s1==s4);//false
        System.out.println(s3==s2);//false
        System.out.println(s3==s4);//false

        Person p1 = new Person("tom", 12);
        Person p2 = new Person("tom", 12);

        System.out.println(p1.getName()==p2.getName());//true
    }
}

class Person{
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

##### String字符串拼接

```java
public class StringTest01 {
    public static void main(String[] args) {
        String s1="javaEE";
        String s2="hadoop";

        String s3="javaEEhadoop";
        String s4="javaEE"+"hadoop";
        String s5=s1+"hadoop";
        String s6="javaEE"+s2;
        String s7=s1+s2;
        //字面量拼接的情况下，地址值是常量池中的地址值
        System.out.println(s3==s4);//true
        //字面量和变量值拼接或者变量值和变量值拼接，地址值是堆中的地址值
        System.out.println(s3==s5);//false
        System.out.println(s3==s6);//false
        System.out.println(s3==s7);//false
        System.out.println(s4==s5);//false
        System.out.println(s5==s6);//false

        String s8=s5.intern();
        System.out.println(s3==s8);//true
    }
}
```

结论：

- 常量与常量的拼接结果在常量池，其常量池中不会存在相同内容的常量
- 只要其中一个是变量，结果就在堆中
- 如果拼接的结果调用intern()方法，返回值就在常量池中

##### String常用方法

- int length()：	返回字符串的长度
- char charAt(int index)：	返回索引处的字符
- boolean isEmpty():	判断是否是空字符串
- String toLowerCase():	将String中的所有字符转换为小写
- String toUpperCase():	将String中所有字符转换为大写
- String trim(): 	返回字符串的副本，忽略前导空白和尾部恐怖
- boolean equalsIgnoreCase(String anotherString):	与equals方法类似忽略大小写
- String concat(String str):	将指定字符串连接到此字符串的结尾。等价于用"+"
- int compareTo(String anotherString):	比较两个字符串的大小
- String substring(int beginIndex):	返回一个新的字符串，他是此字符串的从beginIndex开始截取到最后一个子字符串
- String substring(int beginIndex,int endIndex):	返回一个新字符串，他是此字符串从beginIndex开始截取到endIndex(不包含)的一个字符串
- boolean endsWith(String suffix):     测试此字符串是否以指定的后缀结束
- boolean startsWith(String prefix):    测试此字符串是否以指定的前缀开始
- boolean startsWith(String prefix,int toffset):    测试此字符串从指定索引开始的字符串是否以指定前缀开始
- boolean contaions(CharSequence s):    当且仅当此字符串保护指定的char值序列时，返回ture
- int indexOf(String):    返回指定字符串在此字符串中第一次出现的索引
- int indexOf(String str,int fromIndex):    返回指定字符串在此字符串中第一次出现的索引，从指定的索引开始
- int lastIndexOf(String str):    返回指定字符串在此字符串中最右边出现的索引
- int lastIndexOf(String str,int fromIndex):    返回指定字符串在此字符串中最后一次出现的索引，从指定的索引开始反向搜索

注：indexOf和lastIndexOf方法s如果未找到都是返回-1

- String replace(char oldChar,char newChar):	返回一个新的字符串，它是通过newChar替换此字符串中出现的所有oldChar得到的

- String replace(CharSequence target,CharSequence replacement):	使用指定的字面值替换序列磁环此字符串中所有匹配字面值目标序列的子字符串

- String replaceAll(String regex,String replacement):	使用给定的replacement替换此字符串所有匹配给定的正则表达式的子字符串

- String replaceFirst(String regex,String replacement):	使用给定的replacement替换此字符串所有匹配给定的正则表达式的第一个子字符串
- boolean matches(String regex):	告知此字符串是否匹配给定的正则比倒是
- String[] split(String regex):	根据给定正则表达式匹配拆分此字符串
- String[] split(String regex,int limit):	更加匹配给定的正则表达式来拆分字符串，最多不不超过limit个，如果超过了，剩下的全部放到最后一个元素中

#### StringBuffer&&StringBuilder

String、StringBuffer和StringBuilder三者的异同？

- String:不可变的字符序列
- StringBuffer:可变字符序列；线程安全的，效率低
- StringBuilder:可变字符序列；线程不安全，效率高

##### StringBuffer类的常用方法

- StringBuffer append(xxx):  提供了很多的append()方法，用于进行字符串拼接
- StringBuffer delete(int start,int end):  删除指定位置的内容
- StringBuffer replace(int start,int end,String str):  把[start,end)位置替换为str
- StringBuffer Insert(int offset,xxx):  指定位置插入XXX
- StringBuffer reverse():  把当前字符序列逆转

#### JDK8日期时间API

##### LocalDate\LocalTIme\LocalDateTime

```java
public class JDK8DateTimeTest {

    public static void main(String[] args) {
        //now():获取当前的日期、时间、日期+时间
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(localDateTime);

        //of(): 设置指定的年、月、日、时、分、秒
        LocalDateTime of = LocalDateTime.of(2020, 7, 9, 23, 12, 34);
        System.out.println(of);

        //getXxx()
        int dayOfMonth = localDateTime.getDayOfMonth();
        System.out.println(dayOfMonth);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        System.out.println(dayOfWeek);
        int dayOfYear = localDateTime.getDayOfYear();
        System.out.println(dayOfYear);
        int monthValue = localDateTime.getMonthValue();
        System.out.println(monthValue);
        int minute = localDateTime.getMinute();
        System.out.println(minute);

        //withXxx()
        LocalDate localDate1 = localDate.withDayOfMonth(28);
        System.out.println(localDate);
        System.out.println(localDate1);


        LocalDate localDate2 = localDate.plusDays(22);
        System.out.println(localDate);
        System.out.println(localDate2);
        LocalDate localDate3 = localDate.plusMonths(8);
        System.out.println(localDate);
        System.out.println(localDate3);

        LocalDate localDate4 = localDate.minusDays(3);
        System.out.println(localDate);
        System.out.println(localDate4);

    }
}
```

#### DateTimeFormatter

```java
/**
 * DateTimeFormatter:格式化或解析日期、时间
 */
public class DateTimeFormatterTest {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SS");
        String format = dateTimeFormatter.format(now);
        System.out.println(format);
    }
}
```

## Java集合

Java集合可分为Collection和Map两种体系

Collection接口：单列数据，定义了存取一组对象的方法集合

​	Listt: 元素有序、可重复的集合

​	Set: 元素无序、不可重复的集合

Map接口：双列数据，保存具有映射关系"key-value对"的集合