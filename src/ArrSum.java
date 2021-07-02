
//import Java Util Random
import java.util.Random;

public class ArrSum {

	public static void main(String[] args)

	{
		//setup random number
		Random rand = new Random();

		int[] arr = new int[200000000];

		for (int i = 0; i < arr.length; i++) {

			arr[i] = rand.nextInt(10) + 1;

		}

		long startTime = System.currentTimeMillis();


		long start = System.currentTimeMillis();

		System.out.println(SumMeth.sum(arr));
		System.out.println("Single: " + (System.currentTimeMillis() - start)+ " milliseconds");

		start = System.currentTimeMillis();
		System.out.println(" ");
		System.out.println(SumMeth.parallelSum(arr));
		System.out.println("Parallel: " + (System.currentTimeMillis() - start) + " milliseconds");
		
	}//end main
	
	//Methods below
	
	
	//set array
	
	static class SumMeth extends Thread {

		private int[] arr;

		private int low, high, medium;

		public SumMeth(int[] arr, int low, int high)

		{

			this.arr = arr;

			this.low = low;

			this.high = Math.min(high, arr.length);

		}

		public int getMediumSum()

		{

			return medium;

		}

		public void run()

		{

			medium = sum(arr, low, high);

		}

		public static int sum(int[] arr)

		{

			return sum(arr, 0, arr.length);

		}

		public static int sum(int[] arr, int low, int high)

		{

			int total = 0;

			for (int i = low; i < high; i++) {

				total += arr[i];

			}

			return total;

		}

		public static int parallelSum(int[] arr)

		{

			return parallelSum(arr, Runtime.getRuntime().availableProcessors());

		}

		public static int parallelSum(int[] arr, int threads)

		{

			int size = (int) Math.ceil(arr.length * 1.0 / threads);

			SumMeth[] sums = new SumMeth[threads];

			for (int i = 0; i < threads; i++) {

				sums[i] = new SumMeth(arr, i * size, (i + 1) * size);

				sums[i].start();

			}

			try {

				for (SumMeth sum : sums) {

					sum.join();

				}

			} catch (InterruptedException e) { }

			int total = 0;

			for (SumMeth sum : sums) {

				total += sum.getMediumSum();

			}

			return total;

		}

	}
	
	
}