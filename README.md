# 编译原理大作业

- 课程名称：编程语言原理与编译
- 实验项目：microc改进
- 专业班级：计算机1801
- 学生学号：31802328
- 学生姓名：滕飞
- 实验指导教师: 郭鸣

## 简介

编译原理大作业，基于microc对其进行改进完善，功能实现参考了C语言和Rust语言



## 结构

- 编译器：`F#语言实现 `
  - `Lex.fsl`生成`Lex.fs`词法分析
  - `Par.fsy`生成`Par.fs`语法分析
  - `AstractSyn.fs`抽象语法树
  - `Machine.fs`定义指令集
  - `Contcomp.fs`进行编译

- 虚拟机：`Java实现`
  - `Machine.java`生成`Machine.class`和`Machinetrace.class`

- 示例代码：`test文件夹`
- 运行结果：`img文件夹`



## 用法

- `dotnet "C:\Users\Administrator\.nuget\packages\fslexyacc\10.2.0\build\/fslex/netcoreapp3.1\fslex.dll" -o "Lex.fs" --module Lex --unicode Lex.fsl`生成词法分析器·

- `dotnet "C:\Users\Administrator\.nuget\packages\fslexyacc\10.2.0\build\/fsyacc/netcoreapp3.1\fsyacc.dll"  -o "Par.fs" --module Par Par.fsy`生成语法分析器
- `dotnet run -p microcc.fsproj test/xxx.c`编译test文件夹下的C语言程序

- `Java Machine ../test/xxx.out`在虚拟机中运行编译生成的.out文件

## 功能实现

- 自增自减

  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int a = 2;
      a++;
      int b = 5;
      --b;
      int c = 0;
      c++;
      int d = 10;
      d--;
      print a;
      print b;
      print c;
      print d;
  }
  ```

  - 运行结果

  <img src="img/autoPlusAndMinus.png" alt="image-20210625202301704" style="zoom: 67%;" />



- float类型

  - 介绍
  - 测试用例

  ```c
  int main()
  {
      float a = 1.1;
      int b;
      b = 2;
      print a+b;
  }
  ```

  - 运行结果

  <img src="img/float.png" alt="image-20210625203341581" style="zoom:67%;" />



- char类型

  - 介绍
  - 测试用例

  ```c
  int main()
  {
      char a = 't';
      print a;
  }
  ```

  - 运行结果

  <img src="img/char.png" alt="image-20210625203642675" style="zoom:67%;" />







- 变量定义赋值
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int a = 1;
      int b = 2.2;
      int c = 'c';
      print a;
      print b;
      print c;
  }
  ```

  - 运行结果

  <img src="img/var_init_assign.png" alt="image-20210625204054350" style="zoom:67%;" />







- doWhile循环
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int n = 5;
      do {
          --n;
          print n;
      } while(n > 10);
  }
  ```

  - 运行结果

  <img src="img/doWhile.png" alt="image-20210625204238551" style="zoom:67%;" />







- for循环
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i;
      for(i = 0; i < 10; i++) {
          print i;
      }
  }
  ```

  - 运行结果

  <img src="img/for.png" style="zoom:67%;" />





- for range循环
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i;
      for i in (0..5) {
          print i;
      }
  }
  ```

  - 运行结果

  <img src="img/for_range.png" style="zoom:67%;" />





- loop循环
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i = 1;
      loop {
          print i;
          i++;
          if(i == 11)
              break;
      }
  }
  ```

  - 运行结果

  <img src="img/loop.png" alt="image-20210625205230557" style="zoom:67%;" />





- break功能

  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i;
      for(i = 0; i < 10; i++) {
          print i;
          if (i > 5)
              break;
      }
  }
  ```

  - 运行结果

  <img src="img/break.png" alt="image-20210625211015991" style="zoom:67%;" />





- continue循环

  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i;
      for(i = 0; i < 10; i++) {
          if(i % 2 == 0)
              continue;
          print i;
      }
  }
  ```

  - 运行结果

  <img src="img/continue.png" alt="image-20210625211146238" style="zoom:67%;" />

  





- ? : 表达式
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int a = 1;
      int b = 2;
      int c = a>b ? a:b;
      print c;
  }
  ```

  - 运行结果

  <img src="img/ternary.png" alt="image-20210625210811478" style="zoom: 67%;" />

  



- switch-case功能
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i = 0;
      int n = 2;
      switch(n) {
          case 0: print i+0;
          case 1: print i+1;
          case 2: print i+2;
          case 3: print i+3;
      }
  }
  ```

  - 运行结果

  <img src="img/switch.png" alt="image-20210625211342212" style="zoom:67%;" />





- default功能
  - 介绍
  - 测试用例

  ```c
  int main()
  {
      int i = 0;
      int n = 5;
      switch(n) {
          case 0: print i+0;
          case 1: print i+1;
          case 2: print i+2;
          case 3: print i+3;
          case 5: print i+5;
          case 6: print i+6;
          default: print n*n;     
      }
  }
  ```

  - 运行结果

  <img src="img/default.png" alt="image-20210625212817780" style="zoom:67%;" />

  

  



