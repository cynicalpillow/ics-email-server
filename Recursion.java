public class Recursion {
    public static long factorial(int n){
	if(n == 0){
	    return 1;
	}
	return n*factorial(n-1);
    }
    public static long fibonacci(int n){
	if(n == 1 || n == 2)return 1;
	return fibonacci(n-1)+fibonacci(n-2);
    }
    public static double ballDistance(int n){
	if(n == 0)return 0;
	return (2*(8*(0.7*(n-1) == 0 ? 1 : 0.7*(n-1))))+ballDistance(n-1);
    }
    public static void main(String args[]){
	System.out.println(ballDistance(6));
    }
}
