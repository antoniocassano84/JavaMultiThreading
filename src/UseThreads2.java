class UseThreads2 {

    public static void main(String[] args) {
        String mainT = Thread.currentThread().getName();
        System.out.println(">>> " + mainT + " thread starting.");

        new Thread( () -> { // This is the new Thread
            String thrdName = "Child #1";
            System.out.println("\t >>> " + thrdName + " starting.");
            try {
                for(int i =0; i<10; i++) {
                    Thread.sleep(400);
                    System.out.println("\t" + thrdName + ": count is " + i);
                }
            } catch (InterruptedException exc) { System.out.println(thrdName + " interrupted."); }
            System.out.println("\t >>> " + thrdName + " terminating.");
        } // End Child Thread
        ).start();

        for(int i=0; i<50; i++) {
            System.out.println(mainT + " thread: pass #" + i);

            // Try to comment out the following two lines and see what happens!
            // Hint: Main thread terminates before Child Thread ...
            try { Thread.sleep(100); }
            catch(InterruptedException exc) { System.out.println(mainT + " thread interrupted"); }
        }
        System.out.println(">>> " + mainT + " thread ending.");
    }
}
