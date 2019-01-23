## spring事务##
####Transaction rolled back because it has been marked as rollback-only 异常处理 ####
在spring事务中， 事务嵌套的话会出现这个异常，例如

    @Transactional</br>
    void methodA() {
    	System.out.println("methodA");
    }

	@Transactional</br>
    void methodB() {
    	int a = 1 / 0 //会抛出异常
    }
	@Transactional</br>
    void adduUser() {
    	methodA();
		try {
			methodB
		} catch() {
			....
		}
    }
在执行事务adduUser中，先是执行事务methodA， 然后执行事务methodB， 
在执行methodB时，抛出异常被捕捉到，但是adduser事务已经被添加到事务回滚的状态，
最后addUser无异常抛出，准备提交事务，但是事务已经是回滚状态，所有会报次异常。

解决方案：在一个事务中，异常要抛到方法的顶层
