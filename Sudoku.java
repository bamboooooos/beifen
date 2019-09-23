import java.io.*;
import java.util.*;

public class Sudoku {
	public static void main(String[] args) {
		int rank=Integer.valueOf(args[1]).intValue();
		int problem=Integer.valueOf(args[3]).intValue();
		String filein=args[5];
		String fileout=args[7];
		ArrayList<int[][]> problems=new ArrayList<>();
		ArrayList<int[][]> results=new ArrayList<>();
		try {//输入问题
			File file=new File(filein);
			BufferedReader bw=new BufferedReader(new FileReader(file));
			for(int times=0;times<problem;times++) {
				int[][] prob=new int[9][9];
				for(int i=0;i<rank;i++) {
					for(int j=0;j<rank;j++) {
						prob[i][j]=bw.read()-48;
						bw.read();
					}
					bw.readLine();
				}
				bw.readLine();
				problems.add(prob);
			}
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		for(int i=0;i<problem;i++) {//解决问题
			results.add(solve(problems.get(i),rank));
		}
		try {//输出结果
			File file=new File(fileout);
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			for(int times=0;times<results.size();times++) {
				int[][] result=results.get(times);
				for(int i=0;i<rank;i++) {
					for(int j=0;j<rank;j++) {
						bw.write(result[i][j]+"");
						bw.write(" ");
					}
					bw.newLine();
					bw.flush();
				}
				bw.newLine();
				bw.flush();
			}
			bw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static int[][] solve(int [][] problem,int rank){
		int [][] result=problem;
		int[] numX=new int[81];
		int[] numY=new int[81];
		int[] numNow=new int[81];
		int tosolve=0;
		numX[0]=0;
		numY[0]=0;
		numNow[0]=0;
		for(int i=0;i<rank;i++) {//存储需要更改的位置
			for(int j=0;j<rank;j++) {
				if(result[i][j]==0) {
					numX[tosolve]=i;
					numY[tosolve]=j;
					tosolve++;
				}
			}
		}
		int count=0;
		int x=0,y=0;
		for(;count<tosolve;) {
			x=numX[count];
			y=numY[count];
			int i=numNow[count]+1;
			for(;i<10;i++) {
				result[x][y]=i;
				if(test(result,x,y,rank)) {//替换成功
					numNow[count]=i; 
					count++;
					for(int clear=count;clear<tosolve;clear++) {
						numNow[clear]=0;
					}
					//log(result);
					break;
				}
			}
			if(i==10) {
				for(int clear=count;clear<tosolve;clear++) {//错误清除之后所有改变的项
					int changedx=numX[clear];
					int changedy=numY[clear];
					result[changedx][changedy]=0;
				}
				count--;
			}
		}
		return result;
	}
	public static boolean test(int[][] problem,int x,int y,int rank) {
		int flagx=0;
		int flagy=0;
		for(int i=0;i<rank;i++) {//行测试
			if(problem[x][y]==problem[i][y]) {
				flagx++;
			}
		}
		if(flagx==2) {
			return false;
		}
		for(int i=0;i<rank;i++) {//列测试
			if(problem[x][y]==problem[x][i]) {
				flagy++;
			}
		}
		if(flagy==2) {
			return false;
		}
		//TODO 宫测试
		return true;
	}
	public static void log(int[][] result) {
		for(int[] in:result) {
			for(int i:in) {
				System.out.print(i);
			}
			System.out.println();
		}
	}
	public static void log(int[] resultx,int[] resulty) {
		for(int i=0;i<resultx.length;i++) {
			System.out.println("("+resultx[i]+","+resulty[i]+")");
		}
	}
}
