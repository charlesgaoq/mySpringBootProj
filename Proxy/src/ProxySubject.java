
public class ProxySubject implements Subject{
	private Subject realSubject;

	
	
	
	public void setRealSubject(Subject realSubject) {
		this.realSubject = realSubject;
	}

	public void sailBook() {
		dazhe();
		if(realSubject == null) {
			System.out.println("Ϊ�գ����ڳ�ʼ��");
			realSubject = new RealSubject();
		}
		realSubject.sailBook();
		give();
	}
	
	public void dazhe() {
		System.out.println("����");
	}
	
	public void give() {
		System.out.println("���ʹ���ȯ");
	}
}
