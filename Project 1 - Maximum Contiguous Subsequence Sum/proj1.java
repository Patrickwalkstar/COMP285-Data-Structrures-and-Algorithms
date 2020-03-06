 
/**
 * Programming Project #1
 * Name: proj1.java
 * Last Modified: 02/24/2018
 * Author: Max Escutia & Patrick Walker
 * Description: Implementing 4 different algorithms for solving the "Maximum Subsequence Sum".
 *           This program reads input from a file while creating an array, and uses it to pass it through
 *           each algorithm while calculating the time it takes to execute.
 *
 * */
 
import java.io.File;
import java.util.*;
import java.util.Scanner;
import java.lang.*;
 
 
public class proj1 {
    public static void main(String[] args) {
           /*
            *  Read in file from user
            */
           System.out.println("Enter the file name you wish to read from: ");
 
           /*
            *  Read each line from the file and put values into an array
            */
           Scanner userInput = new Scanner(System.in);
           String fileName = userInput.nextLine();
           int[] a = readTextFile(fileName); //This creates an array from the text file
 
 
           /*
            *  Use created array and run it through each algorithm while
            *  calculating the time it takes to execute each algorithm
            */
           maximumSubSumOne(a);
 
           maximumSubSumTwo(a);
 
           maximumSubSumThree(a);
 
           maximumSubSumFour(a);
 
 
           /*
            *  Prompt the user with options about which algorithm he/she
            *  wants to run
            */
           char ch;
           do {
                   System.out.println("\nAlgorithms: ");
                   System.out.println("1) Algorithm 1");
                   System.out.println("2) Algorithm 2");
                   System.out.println("3) Algorithm 3");
                   System.out.println("4) Algorithm 4");
                   System.out.println("Choose a number to run: ");
                   Scanner input = new Scanner (System.in);
 
                   int choice = input.nextInt();         
                   switch (choice) {
                   case 1 :
                          maximumSubSumOne(a);
                          break;
                   case 2 :
                          maximumSubSumTwo(a);
                          break;
                   case 3 :
                          maximumSubSumThree(a);
                          break;
                   case 4 :
                          maximumSubSumFour(a);
                          break;
                   default :
                          System.out.println(choice + " is not an option");
                          break;
                   }
                   //Loop to try run the whole program again
                   System.out.println("\nDo you want to run the program again? (Type y or n)");
                   ch = input.next().charAt(0);
           } while (ch == 'Y'|| ch == 'y');
    }
 
 
    // This method will read in the integers from the text file and put them into an array
    public static int[] readTextFile(String fileName) {
           try {
                   File file = new File (fileName);
                   Scanner inputFile = new Scanner (file);
                   int count = 0;
                   while(inputFile.hasNextInt()) {
                          count ++;
                          inputFile.nextInt();
                   }
                   int[] arr = new int[count];
                   Scanner s1 = new Scanner(file);
                   for(int i = 0; i < arr.length; i ++)
                          arr[i] = s1.nextInt();
                   return arr;
           } catch (Exception e) {
                   return null;
           }   
    }
 
 
    //running time = O(n^3) --> Cubic
    public static int maximumSubSumOne(int[] a) {    
           long startTime = System.nanoTime();
           int maxSum = 0;
 
           for (int i = 0; i < a.length; i++)
                   for (int j = i; j < a.length; j++) {
                          int sum = 0;
 
                          for (int k = i; k <= j; k++)
                                 sum += a[k];
 
                          if (sum > maxSum)
                                 maxSum = sum;
                   }
           long duration = System.nanoTime() - startTime;
           System.out.println("The duration of the first algorithm is: " + duration + " nano seconds");
           return maxSum;
    }
 
 
    //Running time = O(n^2) --> Quadratic
    public static int maximumSubSumTwo(int[] a) {
           long startTime2 = System.nanoTime();
           int maxSum = 0;
 
           for (int i = 0; i < a.length; i ++) {
                   int sum = 0;
 
                   for (int j = i; j < a.length; j++) {
                          sum += a[j];
 
                          if (sum > maxSum)
                                 maxSum = sum;
                   }
           }
           long stopTime2 = System.nanoTime();
           long duration2 = stopTime2 - startTime2;
           System.out.println("The duration of the second algorithm is: " + duration2 + " nano seconds");
           return maxSum;
    }
 
 
    //Running time = O(n) --> linear
    public static int maximumSubSumThree(int[] a) {
           long startTime3 = System.nanoTime();
           int maxSum = 0;
           int sum = 0;
 
           for (int j = 0; j < a.length; j ++){
                   sum += a[j];
 
                   if (sum > maxSum)
                          maxSum = sum;
                   else if (sum < 0)
                          sum = 0;
           }
           long stopTime3 = System.nanoTime();
           long duration3 = stopTime3 - startTime3;
           System.out.println("The duration of the third algorithm is: " + duration3 + " nano seconds");
           return maxSum;
    }
 
 
    //Running time = O(nlog(n)) --> Recursive maximum subsequence sum algorithm
    public static int maximumSubSumFour (int[] a) {
           long startTime4 = System.nanoTime();
           long stopTime4 = System.nanoTime();
           long duration4 = stopTime4 - startTime4;
           System.out.println("The duration of the fourth algorithm is: " + duration4 + " nano seconds");
           return maxSumRecursive(a, 0, a.length -1);
    }
    public static int maxSumRecursive(int[] a, int left, int right) {
           if (left == right) //Base case
                   if (a[left] > 0)
                          return a[left];
                   else
                          return 0;
 
           int middle = (left + right) / 2;
           int maxLeftSum = maxSumRecursive(a, left, middle);
           int maxRightSum = maxSumRecursive(a, middle + 1, right);
 
           int maxLeftBorderSum = 0;
           int leftBorderSum = 0;
           for (int i = middle; i >= left; i--) {
                   leftBorderSum += a[i];
                   if (leftBorderSum > maxLeftBorderSum)
                          maxLeftBorderSum = leftBorderSum;
           }
 
           int maxRightBorderSum = 0;
           int rightBorderSum = 0;
           for (int i = middle + 1; i <= right; i++) {
                   rightBorderSum += a[i];
                   if (rightBorderSum > maxRightBorderSum)
                          maxRightBorderSum  = rightBorderSum;
           }
           int maximum = Math.max(maxRightBorderSum + maxLeftBorderSum, Math.max(maxLeftSum, maxRightSum));
           return maximum;
    }
}
