// Use synchronise to control access.

class SumArray2 {
  private int sum;

  int sumArray(int nums[]) {
    //synchronized int sumArray(int nums[]) {
    sum = 0;  // reset sum

    for(int i: nums) {
      sum += i;
      System.out.println("Adding "+ i + ". Running total for " + Thread.currentThread().getName() + " is " + sum);
      try { Thread.sleep(10); }
      catch (InterruptedException exc) { System.out.println("Thread interrupted."); }
    }
    return sum;
  }
}

class MyThread9 implements Runnable {
  Thread thrd;
  static SumArray2 sa = new SumArray2();
  int a[];
  int answer;

  // Construct a new thread.
  MyThread9(String name, int nums[]) {
    thrd = new Thread(this, name);
    a = nums;
  }

  // A factory method that creates and starts a thread.
  public static MyThread9 createAndStart(String name, int nums[]) {
    MyThread9 myThrd = new MyThread9(name, nums);
    myThrd.thrd.start();  // start the thread
    return myThrd;
  }

  // Entry point of thread.
  public void run() {
    int sum;

    System.out.println(thrd.getName() + " starting.");

    // synchronize calls to sumArray()
    synchronized (sa) { answer = sa.sumArray(a); }

    System.out.println("Sum for " + thrd.getName() + " is " + answer);
    System.out.println(thrd.getName() + " terminating.");
  }
}

class Sync2 {
  public static void main(String[] args) {
    int a[] = { 1, 2, 3, 4, 5 };

    MyThread9 mt1 = MyThread9.createAndStart("Child #1", a);
    MyThread9 mt2 = MyThread9.createAndStart("Child #2", a);

    try {
      mt1.thrd.join();
      mt2.thrd.join();
    } catch (InterruptedException exc) { System.out.println("Main thread interrupted."); }
  }
}
