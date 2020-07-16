
public class MainClass {
	public static void main(String[] args) {
		Subject realSub = new RealSubject();
		ProxySubject proxySubject = new ProxySubject();
		proxySubject.setRealSubject(realSub);
		proxySubject.sailBook();
	}
}
