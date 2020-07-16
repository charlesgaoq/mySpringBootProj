
public class ProxySubject implements Subject{
	private Subject realSubject;

	
	
	
	public void setRealSubject(Subject realSubject) {
		this.realSubject = realSubject;
	}

	public void sailBook() {
		dazhe();
		if(realSubject == null) {
			System.out.println("为空，现在初始化");
			realSubject = new RealSubject();
		}
		realSubject.sailBook();
		give();
	}
	
	public void dazhe() {
		System.out.println("打折");
	}
	
	public void give() {
		System.out.println("赠送代金券");
	}
}
