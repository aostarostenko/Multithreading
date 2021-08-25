import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ProcessF3 implements Runnable{

    //Об'єк класу ControlsProcess для керування потоків
    ControlsProcess controlsProcess;
    //Ім'я потоку
    String nameThread;
    //Створення об'єкта класу потоків Thread
    Thread thread;
    //Ініціалізування матриць
    double[][] MX;
    double[][] MZ;
    double[][] MR;
    double[][] MG;
    //Розмір матриць
    int nMR=0, mMR=0;
    int nMG=0, mMG=0;
    int nMZ=0, mMZ=0;
    //Об'єкт класу Scanner для зчитування із потоку вводу
    volatile Scanner scanner = new Scanner(System.in);
    //Клас для генерації випадкових чисел
    final Random random = new Random();

    //Конструктор класу
    ProcessF3(String n, ControlsProcess controlsProcess){
        this.controlsProcess=controlsProcess;
        nameThread = n;
        thread = new Thread(this, n);
        System.out.println(" Новий процес > "+nameThread);
        //Задаємо пріорітет для потоку
        thread.setPriority(4);
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
        countF3();
        controlsProcess.flag=true;
        System.out.println("Закінчен процес > " + nameThread);
        controlsProcess.waitAccess();
    }

    //Меню для обрання варіанту запису
    private void input() {
        controlsProcess.flag=false;
        System.out.println("Оберіть варіант запису даних для F3:");
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

    //Функція для запису даних із клавіатури
    private void getForScanner() {
        //Matrix MR
        System.out.print("Введіть число рядків(n) в матриці MR > ");
        nMR = scanner.nextInt();
        System.out.print("\nВведіть число столбців(m) в матриці MR > ");
        mMR=scanner.nextInt();
        MR = new double[nMR][mMR];
        //Input matrix MR
        System.out.println("Введіть елементи матриці MR:");
        for(int i=0;i<nMR;i++)
            for(int j=0;j<mMR;j++){
                MR[i][j]= Double.parseDouble(scanner.next());
            }
        //Output matrix MR
        System.out.println("Матриця MR ("+nMR+"x"+mMR+") :");
        for(int i=0;i<nMR;i++) {
            for (int j = 0; j < mMR; j++) {
                System.out.print(MR[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MG
        System.out.print("Введіть число рядків(n) в матриці MG > ");
        nMG = scanner.nextInt();
        System.out.println("За правилом множення матриць: mMG = nMR = "+nMR);
        mMG=nMR;
        MG = new double[nMG][mMG];
        //Input matrix MR
        System.out.println("Введіть елементи матриці MG:");
        for(int i=0;i<nMG;i++)
            for(int j=0;j<mMG;j++){
                MG[i][j]= Double.parseDouble(scanner.next());
            }
        //Output matrix MG
        System.out.println("Матриця MG ("+nMG+"x"+mMG+") :");
        for(int i=0;i<nMG;i++) {
            for (int j = 0; j < mMG; j++) {
                System.out.print(MG[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MZ
        System.out.println("За правилом додавання матриць:\n розмір матриці MZ дорівнює:"+nMR+"x"+mMG);
        nMZ = nMR;
        mMZ = mMG;
        MZ = new double[nMZ][mMZ];
        //Input matrix MZ
        System.out.println("Введіть елементи матриці MZ:");
        for(int i=0;i<nMZ;i++)
            for(int j=0;j<mMZ;j++){
                MZ[i][j]= Double.parseDouble(scanner.next());
            }
        //Output matrix MZ
        System.out.println("Матриця MZ ("+nMZ+"x"+mMZ+") :");
        for(int i=0;i<nMZ;i++) {
            for (int j = 0; j < mMZ; j++) {
                System.out.print(MZ[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Функція запису даних програмно
    //Генерація випадкових чисел
    private void getForProgram() {
        //Matrix MR
        nMR = random.nextInt(10)+2;
        mMR= random.nextInt(10)+2;
        MR = new double[nMR][mMR];
        System.out.println("Матриця MR ("+nMR+"x"+mMR+") :");
        //Input matrix MR
        for(int i=0;i<nMR;i++)
            for(int j=0;j<mMR;j++){
                MR[i][j]= random.nextDouble();
            }
        //Output matrix MR
        for(int i=0;i<nMR;i++) {
            for (int j = 0; j < mMR; j++) {
                System.out.print(MR[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MG
        nMG = random.nextInt(10)+2;
        mMG=nMR;
        System.out.println("Матриця MG ("+nMG+"x"+mMG+") :");
        MG = new double[nMG][mMG];
        //Input matrix MR
        for(int i=0;i<nMG;i++)
            for(int j=0;j<mMG;j++){
                MG[i][j]= random.nextDouble();
            }
        //Output matrix MG
        for(int i=0;i<nMG;i++) {
            for (int j = 0; j < mMG; j++) {
                System.out.print(MG[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MZ
        System.out.println("За правилом додавання матриць:\n розмір матриці MZ дорівнює:"+nMR+"x"+mMG);
        nMZ = nMR;
        mMZ = mMG;
        MZ = new double[nMZ][mMZ];
        System.out.println("Матриця MZ ("+nMZ+"x"+mMZ+") :");
        //Input matrix MZ
        for(int i=0;i<nMZ;i++)
            for(int j=0;j<mMZ;j++){
                MZ[i][j]= random.nextDouble();
            }
        //Output matrix MZ
        for(int i=0;i<nMZ;i++) {
            for (int j = 0; j < mMZ; j++) {
                System.out.print(MZ[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Функція для запису даних із файлу
    private void getForFile() {
        String file = "E:\\Java\\Multithreading\\f3.txt";
        File f = new File(file);
        FileReader fr = null;
        try
        {
            fr = new FileReader(f);
            Scanner scan = new Scanner(fr);
            if(scan.hasNextLine()){
                //Matrix MR
                nMR = scan.nextInt();
                mMR=scan.nextInt();
                MR = new double[nMR][mMR];
                //Input matrix MR
                for(int i=0;i<nMR;i++)
                    for(int j=0;j<mMR;j++){
                        MR[i][j]= Double.parseDouble(scan.next());
                    }

                //Matrix MG
                nMG = scan.nextInt();
                mMG=nMR;
                MG = new double[nMG][mMG];
                //Input matrix MR
                for(int i=0;i<nMG;i++)
                    for(int j=0;j<mMG;j++){
                        MG[i][j]= Double.parseDouble(scan.next());
                    }

                //Matrix MZ
                nMZ = nMR;
                mMZ = mMG;
                MZ = new double[nMZ][mMZ];
                //Input matrix MZ
                for(int i=0;i<nMZ;i++)
                    for(int j=0;j<mMZ;j++){
                        MZ[i][j]= Double.parseDouble(scan.next());
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
        System.out.println("Матриця MR ("+nMR+"x"+mMR+") :");
        //Output matrix MR
        for(int i=0;i<nMR;i++) {
            for (int j = 0; j < mMR; j++) {
                System.out.print(MR[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MG
        System.out.println("Матриця MG ("+nMG+"x"+mMG+") :");
        //Output matrix MG
        for(int i=0;i<nMG;i++) {
            for (int j = 0; j < mMG; j++) {
                System.out.print(MG[i][j] + " ");
            }
            System.out.println();
        }

        //Matrix MZ
        System.out.println("Матриця MZ ("+nMZ+"x"+mMZ+") :");
        //Output matrix MZ
        for(int i=0;i<nMZ;i++) {
            for (int j = 0; j < mMZ; j++) {
                System.out.print(MZ[i][j] + " ");
            }
            System.out.println();
        }

    }

    //Функція розрахунку заданої функції
    private void countF3(){
        // Якщо умова множення MR*MG не істина, то
        if(nMR!=mMG)
        {
            System.out.println("\tПомилка при множенні MR*MG,\n кількість столбців MR не дорівнює кількісті рядкам MG");
        } else {
            //Множення MR*MG
            double[][] RG = new double[MR.length][MG[0].length];
            for(int i=0; i<nMR;++i){
                for(int j=0; j<mMG;++j){
                    for(int k=0;k<nMG;++k) {
                        RG[i][j]+=MR[i][k]*MG[k][j];
                    }
                }
            }

            /*S
            //Частина коду для перевірки вірності множення
            //Output RG
            for(int i =0; i<RG.length;i++) {
                for (int j = 0; j < RG[0].length; j++) {
                    System.out.print(" " + RG[i][j] + " ");
                }
                System.out.println();
            }
            */

            //Якщо умова додавання MZ + RG не істина
            if(nMZ!=RG.length || mMZ!=RG[0].length)
            {
                System.out.println("Помилка при додаванні MZ та MR*MG: матриці різних розмірів!");
            } else {
                MX=new double[nMZ][mMZ];
                // Додавання MZ + RG
                for(int i=0; i<MX.length;i++){
                    for(int j=0;j<MX[0].length;j++){
                        MX[i][j]=MZ[i][j]+RG[i][j];
                    }
                }

                //Output MX
                System.out.println("F3: Матрица MX ("+MX.length+"x"+MX[0].length+") :");
                for(int i =0; i<MX.length;i++) {
                    for (int j = 0; j < MX[0].length; j++) {
                        System.out.print(" " + MX[i][j] + " ");
                    }
                    System.out.println();
                }

            }

        }

    }
}
