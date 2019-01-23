2018/12/18 15:00:28 
zc
## spring aop，spring和springmvc父子容器##
###问题：spring aop注解配置代理controller无效  ###
原因：spring和springmvc父子容器的问题，造成在spring容器中配置的AOP无法找到在springmvc容器中配置的controller，从而AOP配置无法使用。

解决：`<aop:aspectj-autoproxy proxy-target-class="true"/> `放置在spring的配置文件中配置，现在这行配置要改成在springmvc配置文件中配置

其他：spring aop的maven依赖配置
	`
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjrt</artifactId>
		<version>1.8.0</version>
	</dependency>
	<dependency>
		<groupId>org.aspectj</groupId>
		<artifactId>aspectjweaver</artifactId>
		<version>1.8.0</version>
	</dependency>
	</br><dependency>
	    <groupId>aopalliance</groupId>
	    <artifactId>aopalliance</artifactId>
	    <version>1.0</version>
	</dependency>`
aspectjweaver.jar和aspectjrt.jar（AspectJ库）</br>
aopalliance.jar（Spring AOP的依赖）</br>
spring-aop.jar


----------
## spring和springmvc父子容器浅析 ##
优秀博文：[https://blog.csdn.net/user_xiangpeng/article/details/52181710](https://blog.csdn.net/user_xiangpeng/article/details/52181710 "优秀文章")

描述：spring和springmvc扫描的bean分别是放到各自的context中，
子上下文（springnvc）可以访问父上下文（spring），父上下文访问不到子上下文

开发中遇到的问题一个是上面aop代理controller的问题，一个是事务的问题，如果service在springmvc配置中注册，事务配置在spring配置中注册，则事务不会有作用，所以一般会在spring配置中去除controller的配置，springmvc中只配置controller</br>




