


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;
/**
 * 
	 * 统计一个文件夹下有多少个 .java文件.多少个. class文件。并且统计出.java文件的行数
	 * 注意：包含所有的子文件夹
	 * 
	 * 统计出来所有的类出现的次数　并且统计出，出现次数最多的类名
	 * 
	 * Person=1
	 * User=2
	 * Dept=3
	 * 
	 * 出现次数最多的类是Dept
	 * 
	 * 把所有的结果，写到文件中
 * @author Administrator
 *
 *
 *将一个图片文件复制（字节流）边读边写
 */

public class CounterFile {
	private int countJava;
	private int countClass;
	private int countJavaRows;
	private String countMaxClassName;
	private int countMaxClassValue;//0
	private Properties classes=new Properties();
	
	
	public static void main(String[] args) throws Exception {
		CounterFile counterFile=new CounterFile();
		counterFile.countFile(new File("I:/BaiduYunDownload/2.4/2.4/【源码】mysql版本_spring4.0/FHMYSQL"));
		
		counterFile.printCountFileProperty(new File("c:\\pro.txt"));
		counterFile.countMaxClassNameAndClassValue();
		counterFile.printCountFile(new File("c:\\java.txt"));


		

	
	}
	public void printCountFile(File file) throws Exception{
		PrintStream ps=new PrintStream(file);
		ps.println("共有java文件个数："+countJava);
		ps.println("共有class文件个数："+countClass);
		ps.println("共有java文件的行数为数："+countJavaRows);
		ps.println("出现次数最多的类是："+countMaxClassName);
		ps.println("出现次数最多的类次数为："+countMaxClassValue);
		
	
		
	}
	public void printCountFileProperty(File file) throws Exception{
		OutputStream out=new FileOutputStream(file);
		classes.store(out, "hello ");
		out.close();
		
		
	}
	public void printCountClasses(File file) throws Exception{
		OutputStream out=new FileOutputStream(file);
		classes.store(out, "this is comments");
	}
	public  void countFile(File file) throws IOException{
		File[] files=file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()){
				if(files[i].getName().endsWith(".java")){
					countJava++;
					countRows(files[i]);
				}
				if(files[i].getName().endsWith(".class")){
					
					/**
					 * Test.class(调用setProperties 方法)
					 */
					setProperties(files[i].getName());
					countClass++;
				}
			}else{
				countFile(files[i]);//递归
			}
		}
	}
	public void countRows(File file) throws IOException {//xxx.java
		BufferedReader br=new BufferedReader(new FileReader(file));//装饰设计模式
		while(br.readLine()!=null){
			countJavaRows++;
		}
		br.close();
	}
	public void setProperties(String fileName){//Test.class  Person.class
		//Test
		
		String propertyName=fileName.substring(0, fileName.length()-6);//Test
		if(classes.getProperty(propertyName)!=null){//Test
		classes.setProperty(propertyName, Integer.parseInt(classes.getProperty(propertyName))+1+"");
		}else{
			classes.setProperty(propertyName, 1+"");
		}
		
		
		
		/**
		 * 1.先取主文件名例 Test.class   Test
		 * 2.先判断Properties里有没有这个Key如果没有 Key=1如果要是有Key=1+1
		 * 3.Maxclassvalue=0;
		 * Person=2
		 * Test=1
		 */
		
		
	}
	/**
	 * 统计出出现次数最多的类名和个数
	 */
	public void countMaxClassNameAndClassValue(){
		 Enumeration e=	classes.propertyNames();
		 while(e.hasMoreElements()){
			String key=(String) e.nextElement();//Test Teacher
			int value=Integer.parseInt(classes.getProperty(key));//1 2
			if(value>countMaxClassValue){  //7
				countMaxClassName=key;//Teacher
				countMaxClassValue=value;//2

			}
		 }
		
	}


}
