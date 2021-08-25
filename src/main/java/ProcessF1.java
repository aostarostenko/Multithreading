import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class ProcessF1 implements Runnable{

    //Об'єк класу ControlsProcess для керування потоків
    ControlsProcess controlsProcess;
    //Ім'я потоку
    String nameThread;
    //Створення об'єкта класу потоків Thread
    Thread thread;
    //Ініціалізування зміних даних
    double yRez = 0.0;
    volatile Vector X = new Vector();
    volatile Vector Y = new Vector();
    volatile Vector G = new Vector();
    volatile Vector T = new Vector();
    int size = 0;
    //Об'єкт класу Scanner для зчитування із потоку вводу
    volatile Scanner scanner = new Scanner(System.in);
    //Клас для генерації випадкових чисел
    final Random random = new Random();

    //Конструкторк класу
    ProcessF1(String n, ControlsProcess controlsProcess){
        this.controlsProcess=controlsProcess;
        nameThread = n;
        thread = new Thread(this, n);
        System.out.println(" Новий процес > "+nameThread);
        //Задаємо пріорітет для потоку
        thread.setPriority(7);
        //Запускаємо потік
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Початок процесу > " + nameThread);
        //Затримка на 0.01с
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controlsProcess.waitAccess();
        controlsProcess.flag=false;
        //Ввод даних
        input();
        //Розрахунок функції
        countF1();
        controlsProcess.flag=true;
        System.out.println("Закінчен процес > " + nameThread);
        controlsProcess.waitAccess();
    }

    //Меню для обрання варіанту запису
    private void input() {
        controlsProcess.flag=false;
        System.out.println("Оберіть варіант запису даних для F1:");
        System.out.println("1) з клавіатури;");
        System.out.println("2) формірування даних у програмі;");
        System.out.println("3) використовуя файл із даними.");
        System.out.println("Введіть номер обранного варіанту та натисніть Enter..");
        System.out.print(">> ");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> getForScanner();
            case 2 -> getForProgram();
            case 3 -> getForFile();
            default -> {
                System.out.println();
                input();
            }
        }
    }

    //Функція запису даних із клавіатури
    private void getForScanner() {
        System.out.print("Введіть розмір векторів > ");
        size = scanner.nextInt();
        //Input X
        System.out.println("Введіть значення елементів вектора X:");
        for(int i =1;i<=size;i++){
            String line = scanner.next();
            double x = Double.parseDouble(line);
            X.add(x);
        }
        //Output X
        Enumeration vEnum = X.elements();
        System.out.print("Vector X : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Input Y
        System.out.println("Введіть значення елементів вектора Y:");
        for(int i =1;i<=size;i++){
            String line = scanner.next();
            double x = Double.parseDouble(line);
            Y.add(x);
        }
        //Output Y
        vEnum = Y.elements();
        System.out.print("Vector Y : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Input G
        System.out.println("Введіть значення елементів вектора G:");
        for(int i =1;i<=size;i++){
            String line = scanner.next();
            double x = Double.parseDouble(line);
            G.add(x);
        }
        //Output G
        vEnum = G.elements();
        System.out.print("Vector G : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Input T
        System.out.println("Введіть значення елементів вектора T:");
        for(int i =1;i<=size;i++){
            String line = scanner.next();
            double x = Double.parseDouble(line);
            T.add(x);
        }
        //Output T
        vEnum = T.elements();
        System.out.print("Vector T : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
    }

    //Функція формування даних програмно
    //Генерація випадкових даних
    private void getForProgram() {
        int n = random.nextInt(10)+4;
        size = n;
        System.out.println("Розмір векторів: "+size);
        //Random vectors
        for(int i =1;i<=n;i++){
            X.addElement(random.nextDouble());
        }
        for(int i =1;i<=n;i++){
            Y.addElement(random.nextDouble());
        }
        for(int i =1;i<=n;i++){
            G.addElement(random.nextDouble());
        }
        for(int i =1;i<=n;i++){
            T.addElement(random.nextDouble());
        }
        //Output XS
        Enumeration vEnum = X.elements();
        System.out.print("Vector X : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output Y
        vEnum = Y.elements();
        System.out.print("Vector Y : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output G
        vEnum = G.elements();
        System.out.print("Vector G : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output T
        vEnum = T.elements();
        System.out.print("Vector T : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
    }

    //Функція запису даних із файлу
    private void getForFile() {
        String file = "E:\\Java\\Multithreading\\f1.txt";
        File f = new File(file);
        FileReader fr = null;
        try
        {
            fr = new FileReader(f);
            Scanner scan = new Scanner(fr);
            if(scan.hasNextLine()) {
                size = scan.nextInt();
                System.out.println("Розмір векторів: " + size);

                for (int i = 0; i < size; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    X.addElement(x);
                }
                for (int i = 0; i < size; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    Y.addElement(x);
                }
                for (int i = 0; i < size; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    G.addElement(x);
                }
                for (int i = 0; i < size; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    T.addElement(x);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(fr != null){
                    fr.close();
                }
            }catch (IOException e){
                System.err.println("Помилка закриття файлу: "+e);
            }
        }
        //Output X
        Enumeration vEnum = X.elements();
        System.out.print("Vector X : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output Y
        vEnum = Y.elements();
        System.out.print("Vector Y : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output G
        vEnum = G.elements();
        System.out.print("Vector G : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
        //Output T
        vEnum = T.elements();
        System.out.print("Vector T : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
    }

    //Розрахунок заданої функції
    private void countF1() {
        //Операція додавання X+Y+G
        double[] aXYG = new double[size];
        for(int i = 0; i<size; i++){
            aXYG[i]=(double)X.elementAt(i) + (double)G.elementAt(i) + (double)Y.elementAt(i);
        }
        //Операція віднімання T-X
        double[] bTX = new double[size];
        for(int i=0;i<size;i++){
            bTX[i]=(double)T.elementAt(i) - (double)X.elementAt(i);
        }
        //Розрахунок y
        for (int i=0; i<size; i++){
            yRez+=aXYG[i]*bTX[i];
        }
        //Вивід результату
        System.out.println("F1: y = "+ yRez);
    }

}
