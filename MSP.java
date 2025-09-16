package Pract_4;
import java.util.Scanner;

class Result
{
    int sum;
    int start;
    int end;

    Result(int sum, int start, int end)
    {
        this.sum = sum;
        this.start = start;
        this.end = end;
    }
}

public class MSP {

    // Find max crossing subarray
    public static Result cross(int[] arr, int low, int mid, int high, int constraint)
    {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = mid;

        // go left from mid
        for (int i = mid; i >= low; i--)
        {
            sum += arr[i];
            if (sum > leftSum && sum <= constraint)
            {
                leftSum = sum;
                maxLeft = i;
            }
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = mid + 1;

        // go right from mid+1
        for (int i = mid + 1; i <= high; i++)
        {
            sum += arr[i];
            if (sum > rightSum && sum <= constraint)
            {
                rightSum = sum;
                maxRight = i;
            }
        }

        // only combine if total fits
        if (leftSum != Integer.MIN_VALUE && rightSum != Integer.MIN_VALUE)
        {
            int total = leftSum + rightSum;
            if (total <= constraint)
            {
                return new Result(total, maxLeft, maxRight);
            }
        }
        return new Result(Integer.MIN_VALUE, low, high); // invalid
    }

    // Divide and conquer method
    public static Result Subarray(int[] arr, int low, int high, int constraint)
    {
        if (low == high)
        {
            if (arr[low] <= constraint)
            {
                return new Result(arr[low], low, high);
            }
            else
            {
                return new Result(Integer.MIN_VALUE, low, high); // invalid
            }
        }

        int mid = (low + high) / 2;

        Result left = Subarray(arr, low, mid, constraint);
        Result right = Subarray(arr, mid + 1, high, constraint);
        Result crossSum = cross(arr, low, mid, high, constraint);

        // pick the best valid one
        Result best = left;
        if (right.sum > best.sum) best = right;
        if (crossSum.sum > best.sum) best = crossSum;

        return best;
    }

    // Driver code
    public static void main(String args[])
    {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the number of items in array: ");
        int n = s.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
        {
            System.out.print("Enter item: ");
            arr[i] = s.nextInt();
        }

        System.out.print("Enter the constraint: ");
        int constraint = s.nextInt();

        Result result = Subarray(arr, 0, n - 1, constraint);

        if (result.sum == Integer.MIN_VALUE)
        {
            System.out.println("No valid subarray found within constraint.");
        }
        else
        {
            System.out.println("Maximum Subarray Sum = " + result.sum);
            System.out.print("Subarray = [");
            for (int i = result.start; i <= result.end; i++)
            {
                System.out.print(arr[i] + (i < result.end ? ", " : ""));
            }
            System.out.println("]");
        }
    }
}
