import java.io.*;
import java.util.Scanner;

abstract class Version implements Playable<String> {
    private String versionNumber;

    public Version(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public String toString() {
        return "Version Number: " + versionNumber;
    }

    abstract void display();
}

interface Playable<T> {
    static int g = 0;

    void play(T media);

    void pause();

    void stop();
}

class Media<T> {
    private T mediaInfo;

    public Media(T mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public T getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(T mediaInfo) {
        this.mediaInfo = mediaInfo;
    }
}

class Video<T> extends Version implements Playable<String> {
    private String videoName;

    public Video(String versionNumber, String videoName) {
        super(versionNumber);
        this.videoName = videoName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return super.toString() + ", Video Name: " + videoName;
    }

    @Override
    protected void display() {
        System.out.println("Version Number: " + getVersionNumber());
        System.out.println("Video Name: " + videoName);
    }

    @Override
    public void play(String media) {
        System.out.println("Playing media: " + media);
    }

    @Override
    public void pause() {
        System.out.println("Pausing video: " + videoName);
    }

    @Override
    public void stop() {
        System.out.println("Stopping video: " + videoName);
    }
}

class View<T> extends Video<T> {
    private int views;

    public View(String versionNumber, String videoName, int views) {
        super(versionNumber, videoName);
        this.views = views;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return super.toString() + ", Views: " + views;
    }

    @Override
    protected void display() {
        super.display();
        System.out.println("Views: " + views);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        Media<Version>[] versions = new Media[10];
        int versionIndex = 0;

        while (!exit) {
            System.out.println("=== Меню ===");
            System.out.println("1. Создать объект класса Video");
            System.out.println("2. Вывести всю информацию");
            System.out.println("3. Запись данных в файл");
            System.out.println("4. Чтение данных из файла");
            System.out.println("5. Выход");
            System.out.print("Выберите пункт меню: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Введите номер версии: ");
                    String versionNumber = scanner.next();
                    System.out.print("Введите название видео: ");
                    String videoName = scanner.next();
                    System.out.print("Введите количество просмотров: ");
                    int numViews = scanner.nextInt();

                    versions[versionIndex] = new Media<>(new View<>(versionNumber, videoName, numViews));

                    System.out.println("Объект класса Video создан");
                    versionIndex++;
                    break;
                case 2:
                    for (int i = 0; i < versionIndex; i++) {
                        Version version = versions[i].getMediaInfo();
                        version.display();
                    }
                    break;

                case 3:
                    try {
                        FileWriter fileWriter = new FileWriter("data.txt");
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        for (int i = 0; i < versionIndex; i++) {
                            bufferedWriter.write(versions[i].getMediaInfo().toString());
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                        System.out.println("Данные успешно записаны в файл.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        FileReader fileReader = new FileReader("data.txt");
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println("Прочитанные данные из файла: " + line);
                        }
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    exit = true;
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Недопустимый вариант. Попробуйте еще раз.");
                    break;
            }

            System.out.println(); // Дополнительная пустая строка для разделения выводов меню
        }

        scanner.close();
    }
}
