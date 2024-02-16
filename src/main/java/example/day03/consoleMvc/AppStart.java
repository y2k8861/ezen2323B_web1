package example.day03.consoleMvc;

public class AppStart {
    public static void main(String[] args) {
        // 1.
        MainView mainView = new MainView();
        mainView.home();

        // 2.
        // new MainView().home();

        // 3. 싱글톤
        // MainView.getInstance().home();

        // 4. 메소드가 정적메소드 일때
        // MainView.home();

        // 5.IOC 제어 역전, DI의존성주입
    }
}
