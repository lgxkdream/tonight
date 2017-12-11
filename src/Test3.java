import java.util.Arrays;


public class Test3 {
	final int MAX=5000000;
	boolean run=true;
	int maxN=1000;
	int n1=1;
	int n2=1;
	int n3=1;
	public void test(double a1,double a2,double a3){

		double b1=2*n1;
		double b2=2*n2;
		double b3=2*n3;
		
		double c1;
		double c2;
		double c3;
		
		c1=a1*b1;
		c2=a2*b2;
		c3=a3*b3;
		
//		if(b1==6 && b2==4 && b3==2){
//			System.out.println(1);
//		}
		System.out.println("b======="+b1+","+b2+","+b3);
		
		double[] array=new double[]{c1,c2,c3};
		Arrays.sort(array);
		double min=array[0];
		if(min>=(b1+b2+b3)){
			System.out.println("n========"+n1+"\t"+n2+"\t"+n3);
			System.out.println("c========"+c1+"\t"+c2+"\t"+c3);
			System.out.println("b========"+b1+"\t"+b2+"\t"+b3);
			System.out.println("==============================================");
			run=false;
		}
		
	}
	public void exe(double a1,double a2,double a3){
		//第一遍循环
		for(;maxN<=MAX;maxN=maxN+maxN){
			for(;n1<=maxN;n1++){
				//System.out.println("1--loop");
				if(run)
				for(;n2<=maxN;n2++){
					//System.out.println("2--loop");
					if(run)
					for(;n3<=maxN;n3++){
						//System.out.println("3--loop");
						//System.out.println("n========"+n1+","+n2+","+n3);
						if(run)
						test( a1, a2, a3);
					}
					n3=0;
				}
				n2=0;
				n3=0;
			}
		
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test3 t=new Test3();
		t.exe(1.23,2.34,3.45);
		
		
	}

}
