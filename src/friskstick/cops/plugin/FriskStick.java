import java.util.Scanner;

public class EnumTest{
	private static String[] as = new String[3];
	private static int[] an = new int[3];
	private static String s;
	private static int n;
	private static String s1;
	private static int n1;
	private static String s2;
	private static int n2;
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		int times = 0;
		while(times < 3){
		System.out.println("Enter a sentence:");
		as[times] = input.nextLine();
		System.out.println("Enter a number:");
		an[times] = input.nextInt();
		as[times] = (times == 0 ? s : times == 1 ? s1 : s2);
		an[times] = (times == 0 ? n : times == 1 ? n1 : n2);
		times++;
		}
		for(e i: e.values()){
			System.out.printf("%s\t%s\t%d\n", i, i.getS(), i.getN());
		}
		input.close();
	}
	public enum e{
		e1(s, n),
		e2(s1, n1),
		e3(s2, n2);
		e(String st, int nu){
			s = st;
			n = nu;
		}
		public String getS(){
			int t = 0;
			String parString;
			if(t == 0){
				parString = s;
			}else if(t == 1){
				parString = s1;
			}else{
				parString = s2;
			}
			return parString;
		}
		public int getN(){
			int t = 0;
			int parInt;
			if(t == 0){
				parInt = n;
			}else if(t == 1){
				parInt = n1;
			}else{
				parInt = n2;
			}
			return parInt;
		}
	}
}
