import java.util.*;
class Calci {
    public static void main(String args[]) {
        int i=0;
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the no.of inputs");
        int n=sc.nextInt();
        int a[]= new int[n];
        System.out.println("enter the numbers");
        for(i=0;i<n;i++) {
            a[i]=sc.nextInt();
        }
        System.out.println("1.Addition\n"+"2.Subtraction\n"+"3.Division\n"+"4.Multiplication");
        int s=sc.nextInt();
        int result=a[0];
        for(i=1;i<n;i++) {
        switch(s) {
            case 1: result+=a[i];
            break;
            case 2: result-=a[i];
            break;
            case 3: result/=a[i];
            break;
            case 4: result*=a[i];
            break;
            default: System.out.println("Invalid Option");
        }
       

    }
    System.out.println("result of operation is"+":"+result);
    sc.close();

 }
}