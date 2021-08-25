import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class ProcessF2 implements Runnable{

    //Об'єк класу ControlsProcess для керування потоків
    ControlsProcess controlsProcess;
    //Ім'я потоку
    String nameThread;
    //Створення об'єкта класу потоків Thread
    Thread thread;
    //Ініціалізування змінних данихS
    double eRez=0.0;
    volatile Vector A = new Vector();
    volatile Vector C = new Vector();
    double[][] MB; //MB[n][m]
    int n = 0;
    int m = 0;
    int sizeA = 0;
    int sizeC = 0;
    //Об'єкт класу Scanner для зчитування із потоку вводу
    volatile Scanner scanner = new Scanner(System.in);
    //Клас для генерації випадкових чисел
    final Random random = new Random();

    //Конструктор класу
    ProcessF2(String n, ControlsProcess controlsProcess){
        this.controlsProcess=controlsProcess;
        nameThread = n;
        thread = new Thread(this, n);
        System.out.println(" Новий процес > "+nameThread);
        //Задаємо пріорітет для потоку
        thread.setPriority(3);
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
        countF2();
        controlsProcess.flag=true;
        System.out.println("Закінчен процес > " + nameThread);
        controlsProcess.waitAccess();
    }

    //Меню для обрання варіанту запису
    private void input() {
        controlsProcess.flag=false;
        System.out.println("Оберіть варіант запису даних для F2:");
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
                System.out.println("");
                input();
            }
        }
    }

    //Функція запису даних із клавіатури
    private void getForScanner() {
        //Matrix MB
        System.out.print("Введіть число рядків(n) в матриці МВ > ");
        n = scanner.nextInt();
        System.out.print("\nВведіть число столбців(m) в матриці МВ > ");
        m=scanner.nextInt();
        MB = new double[n][m];
        //Input matrix MB
        System.out.println("Введіть елементи матриці MB:");
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++){
                MB[i][j]= Double.parseDouble(scanner.next());
            }
        //Output matrix MB
        System.out.println("Матриця MB ("+n+"x"+m+"):");
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(MB[i][j] + " ");
            }
            System.out.println();
        }

        //Vector A
        System.out.println("\n За мат.правилами довжина вектора A буде m - " + m);
        sizeA = m;
        //Input A
        System.out.println("Введіть значення элементів вектора A:");
        for(int i =0;i<sizeA;i++){
            A.addElement(Double.parseDouble(scanner.next()));
        }
        //Output A
        Enumeration vEnum = A.elements();
        System.out.print("Vector A : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();

        //Vector C
        System.out.println("\n За мат.правилами довжина вектора C буде m - "+ m);
        sizeC = m;
        //Input C
        System.out.println("Введіть значення елементів вектора C:");
        for(int i =0;i<sizeC;i++){
            C.addElement(Double.parseDouble(scanner.next()));
        }
        //Output C
        vEnum = C.elements();
        System.out.print("Vector C : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();
    }

    //Функція формування даних програмно
    private void getForProgram() {
        n=random.nextInt(8)+3;
        m=random.nextInt(8)+3;
        MB = new double[n][m];
        //input MB
        for(int i=0;i<n;i++)
            for(int j=0;j<m;j++){
                MB[i][j]= random.nextDouble();
            }
        //Output matrix MB
        System.out.println("Матриця MB ("+n+"x"+m+"):");
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(MB[i][j] + " ");
            }
            System.out.println();
        }

        //Vector A
        System.out.println("\n За мат.правилами довжина вектора A буде m - " + m);
        sizeA = m;
        //Input A
        for(int i =0;i<sizeA;i++){
            A.addElement(random.nextDouble());
        }
        //Output A
        Enumeration vEnum = A.elements();
        System.out.print("Vector A : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();

        //Vector C
        System.out.println("\n За мат.правилами довжина вектора C будет m - "+m);
        sizeC = m;
        //Input C
        for(int i =0;i<sizeC;i++){
            C.addElement(random.nextDouble());
        }
        //Output C
        vEnum = C.elements();
        System.out.print("Vector C : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();

    }

    //Функція зчитування даних із файлу
    private void getForFile() {
        String file = "E:\\Java\\Multithreading\\f2.txt";
        File f = new File(file);
        FileReader fr = null;
        try
        {
            fr = new FileReader(f);
            Scanner scan = new Scanner(fr);
            if(scan.hasNextLine()){
                //Matrix MB
                n = scan.nextInt();
                m = scan.nextInt();
                MB = new double[n][m];
                //Input matrix MB
                for(int i=0;i<n;i++) {
                    for (int j = 0; j < m; j++) {
                        MB[i][j] = Double.parseDouble(scan.next());
                    }
                }

                //Vector A
                sizeA = m;
                for (int i = 0; i < sizeA; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    A.addElement(x);
                }
                //Vector C
                sizeC=m;
                for (int i = 0; i < sizeC; i++) {
                    String line = scan.next();
                    double x = Double.parseDouble(line);
                    C.addElement(x);
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

        //Вивід даних
        //Output matrix MB
        System.out.println("Матриця MB ("+n+"x"+m+"):");
        for(int i=0;i<n;i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(MB[i][j] + " ");
            }
            System.out.println();
        }

        //Output A
        Enumeration vEnum = A.elements();
        System.out.print("Vector A : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();

        //Output C
        vEnum = C.elements();
        System.out.print("Vector C : ");
        while(vEnum.hasMoreElements()){
            System.out.print(vEnum.nextElement()+" ");
        }
        System.out.println();

    }

    //Функція розрахунку заданої функції
    private void countF2(){
        //IF for A*MB
        if(sizeA!=m){
            System.out.println("Помилка множення A на MB!\n Розмір вектора А не співпадає з кількістю столбців MB");
        } else {
            //A*MB
            double[] AMB = new double[A.size()];
            for(int i=0; i<AMB.length;i++){
                AMB[i]=0.0;
                for(int j=0; j<n;j++){
                    AMB[i] +=MB[j][i]*(Double)A.elementAt(i);
                }
            }

            //IF for AMB - C
            if(AMB.length!=C.size()){
                System.out.println("Помилка операції A*MB - C! Різні розміри!");
            } else{
                double[] resMinus = new double[C.size()];
                for(int i=0; i<C.size(); i++){
                    resMinus[i]=AMB[i]-(double)C.elementAt(i);
                }

                /*
                //частина коду для перевірки віднімання
                //Output resMinus
                for(int i=0;i<resMinus.length;i++)
                    System.out.print(resMinus[i] + " ");
                System.out.println();
                */

                //Знаходження MAX значення
                for(int i=1; i<resMinus.length;i++){
                    eRez=getMax(resMinus[i-1],resMinus[i]);
                }
                //Виід результату
                System.out.println("F2: e = "+eRez);
            }

        }

    }

    //Функція знаходження максимального числа
    private double getMax(double a, double b){
        return (a>b?a:b);
    }
}