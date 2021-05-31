# 一款能够根据指定目录找到 Java 文件生成 UML 类图的工具


<table>
  <thead>
    <tr>
      <th>最近音乐</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><a href="http://dj.dj520.com/diffuucc/20210430/608b79af08a1a.m4a"><img src="http://wwv.dj520.com/520img/500_500/50eb02925e98.jpg" width="100" height="60"></a></td>
    </tr>
    <tr></tr> <!-- hide gray row -->
    <tr>
      <td><a href="http://dj.dj520.com/diffuucc/20210430/608b79af08a1a.m4a"><img src="http://wwv.dj520.com/520img/500_500/50eb02925e98.jpg" width="100" height="60"></a></td>
    </tr>
    <tr></tr> <!-- hide gray row -->
    <tr>
      <td><a href="http://dj.dj520.com/diffuucc/20210430/608b79af08a1a.m4a"><img src="http://wwv.dj520.com/520img/500_500/50eb02925e98.jpg" width="100" height="60"></a></td>
    </tr>
  </tbody>
</table>



## 1：使用方法：



环境要求：

1. 256M内存，一核心CPU，显示器、鼠标、键盘。
2. jdk1.8、idea 2020、maven 3.6



下载源码，maven 拉下 jar 包

Main.java 是启动类，提供一个 java 的目录，提供一个 uml 图片输出的目录。



bug 不少，也能使用。。。

参考文献：

​	https://plantuml.com/zh/class-diagram



jar 包你要是真的下载的慢，就去这里搞一个 plantuml.jar：

​	https://repo1.maven.org/maven2/net/sourceforge/plantuml/plantuml/1.2021.7/

当然也可以去 maven 仓库：

​	https://mvnrepository.com/artifact/net.sourceforge.plantuml/plantuml

jar 包使用方法：

1. 把 Main.java 返回的字符串，写到某个文件夹下，后缀 .txt。
2. 把 jar 包也放在那个文件夹下，java -jar plantuml.jar xxx.txt



当前项目结构：

![](https://github.com/zhemeduogewangtianyi/Carrot-UML-Generator/blob/main/src/main/resources/images/carrotUml.png?raw=true)



迭代变化：

2021-05-30	支持分多文件生成，解决大图片展示不全问题。

2021-05-29 	发布代码，修复字段变量，方法生成时候溢出问题。
