// Demonstrate thread priorities.

class Priority implements Runnable {
  int count;
  Thread thrd;

  static boolean stop = false;
  static String currentName;

  // Construct a new thread.
  Priority(String name) {
    thrd = new Thread(this, name);
    count = 0;
    currentName = name;
  }

  // Entry point of thread.
  public void run() {
    System.out.println(thrd.getName() + " starting.");
    do {
      count++;
      if(currentName.compareTo(thrd.getName()) != 0) {
        currentName = thrd.getName();
        //System.out.println("In " + currentName);
      }
    } while (stop == false && count < 10000000);
    stop = true;

    System.out.println(thrd.getName() + " terminating.");
  }
}

class PriorityDemo {
  public static void main(String[] args) {
    Priority mt1 = new Priority("High priority");
    Priority mt2 = new Priority("Low priority");
    Priority mt3 = new Priority("Normal priority");
    Priority mt4 = new Priority("Normal priority");
    Priority mt5 = new Priority("Normal priority");

    // set the priorities
    mt1.thrd.setPriority(Thread.MAX_PRIORITY);
    mt2.thrd.setPriority(Thread.MIN_PRIORITY);
    // leave mt3, mt4 and mt5 at the default, normal priority level

    // start the threads
    mt1.thrd.start();
    mt2.thrd.start();
    mt3.thrd.start();
    mt4.thrd.start();
    mt5.thrd.start();

    try {
      mt1.thrd.join();
      mt2.thrd.join();
      mt3.thrd.join();
      mt4.thrd.join();
      mt5.thrd.join();
    } catch(InterruptedException exc) { System.out.println("Main thread interrupted."); }

    System.out.println();
    System.out.println("High priority thread counted to " + mt1.count);
    System.out.println("Low priority thread counted to " + mt2.count);
    System.out.println("1st Normal priority thread counted to " + mt3.count);
    System.out.println("2nd Normal priority thread counted to " + mt4.count);
    System.out.println("3rd Normal priority thread counted to " + mt5.count);
  }
}
