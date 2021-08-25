public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("\tПроцес розрахунку функцій Fi стартував!");
        ControlsProcess controlsProcess = new ControlsProcess();
        Thread.yield();
        ProcessF1 threadProcessF1 = new ProcessF1("F1: y=(X+Y+G)*(T-X)", controlsProcess);
        ProcessF2 threadProcessF2 = new ProcessF2("F2: e=max(A*MB-C)", controlsProcess);
        ProcessF3 threadProcessF3 = new ProcessF3("F3: MX=MZ+MR*MG",controlsProcess);

        threadProcessF1.thread.join();
        threadProcessF2.thread.join();
        threadProcessF3.thread.join();
        System.out.println("\n\tПроцес розрахунку функцій Fi завершено!");
    }

}
