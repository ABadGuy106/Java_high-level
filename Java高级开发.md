# Java语言高级开发

## 多线程



### 线程的创建和使用

#### 线程创建方式

继承Thread类，重写run方法





#### Thread类相关方法

- void start():	启动线程，执行对象的run()方法
- run():	线程在被调度时执行操作
- String getName():	返回线程名称
- void setName(String name):	设置该线程名称
- static Thread currentThread():	返回当前线程，在Thread子类中就是this,通常用于主线程和Runnable实现类
- yield():    释放当前线程的CPU执行权
- join() :     在线程A中调用线程B的join() 