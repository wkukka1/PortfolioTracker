package currency_conversion;

import app.Main;
import com.fasterxml.jackson.core.JsonProcessingException;
import data_access.ExchangeRateClient;
import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.*;
import interface_adapter.logged_in.LoggedInViewModel;

import interface_adapter.logged_in.currency_conversion.CurrencyPresenter;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mockito;
import use_case.currency_conversion.*;
import use_case.signup.PortfolioDataAccessInterface;
import view.LabelTextPanel;
import view.LoggedInView;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CurrencyTest {
    private final CurrencyDataAccessInterface mockCurrencyClient = Mockito.mock(ExchangeRateClient.class);
    private final PortfolioDataAccessInterface mockPortfolioDAO = Mockito.mock(FilePortfolioDataAccessObject.class);

    private String jsonString = "{\"result\":\"success\",\"time_next_update_unix\":1701993601,\"target_code\":\"CAD\",\"time_next_update_utc\":\"Fri, 08 Dec 2023 00:00:01 +0000\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"time_last_update_unix\":1701907201,\"base_code\":\"USD\",\"time_last_update_utc\":\"Thu, 07 Dec 2023 00:00:01 +0000\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"conversion_rate\":1.3582}";
    private CurrencyInputBoundary currencyInteractor;
    private final CurrencyOutputBoundary mockPresenter = Mockito.mock(CurrencyPresenter.class);

    private final CurrencyInputBoundary mockInteractor = Mockito.mock(CurrencyInteractor.class);

    @Before
    public void setup(){
        this.currencyInteractor = new CurrencyInteractor(mockCurrencyClient, mockPresenter);
    }

    @Test
    public void testExecuteWithException() throws JsonProcessingException {
        // Arrange
        CurrencyInputData input = new CurrencyInputData("USD", "EUR");
        JSONObject mockRawCurrencyInfo = new JSONObject("{\"conversion_rate\": 1.2}");

        // Mocking behavior to throw an exception when jsonToHashMap is called
        doThrow(new JsonProcessingException("Mocked exception") {}).when(mockInteractor).jsonToHashMap(any());

        when(mockCurrencyClient.getCurrencyInfo("USD", "EUR")).thenReturn(mockRawCurrencyInfo);

        // Act and Assert
        assertThrows(JsonProcessingException.class, () -> {
            currencyInteractor.execute(input);
        });

        // Verify that the presenter was not called
        verify(mockPresenter, never()).prepareSuccessView(any());
    }

    @org.junit.Test
    public void testCurrencyButton() throws JsonProcessingException {
        // Portfolio DAO
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Investment> stockList = new ArrayList<>();

        Stock stock = new Stock("AAPL", LocalDateTime.now(), 1, 10);

        stockList.add(stock);
        fudao.save(uf.create("user", "password", LocalDateTime.now(), 1));
        fpdao.savePortfolio(new Portfolio(stockList, 10, 1));

        Main.main(null);

        LabelTextPanel[] textfield = getTextfields();
        LabelTextPanel usernameInputField = textfield[0];
        LabelTextPanel passwordInputField = textfield[1];


        usernameInputField.setText("user");
        passwordInputField.setText("password");

        getLoginBtn().doClick();

        assert currencyButtonExists();

    }

    @org.junit.Test
    public void testUsdToCad() throws JsonProcessingException {
        addUser(1);
        CurrencyInputData mockInputData = new CurrencyInputData("", "");
        JSONObject mockClientRes = new JSONObject(jsonString);
        Stock stock = new Stock("AAPL", LocalDateTime.parse("2022-03-03T00:00"), 1, 80);
        List<Investment> stockList = List.of(stock);
        Portfolio mockPortfolioRes = new Portfolio(stockList, 0, 0);
        when(mockCurrencyClient.getCurrencyInfo(anyString(), anyString())).thenReturn(mockClientRes);
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(mockPortfolioRes);

        currencyInteractor.execute(mockInputData);

        verify(mockPresenter, times(1)).prepareSuccessView(any(CurrencyOutputData.class));

    }

    private void addUser(int numOfUsers) {
        UserFactory uf = new CommonUserFactory();
        FileUserDataAccessObject fudao;
        FilePortfolioDataAccessObject fpdao;
        try {
            fudao = new FileUserDataAccessObject("./users.csv", uf);
            fpdao = new FilePortfolioDataAccessObject("./portfolios.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < numOfUsers; i++) {
            fudao.save(uf.create("user" + i, "password" + i, LocalDateTime.now(), i));
            fpdao.savePortfolio(new Portfolio(new ArrayList<>(), 0, i));
        }
    }

    private boolean currencyButtonExists() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        LoggedInView lv = (LoggedInView) jp2.getComponent(2);
        JButton currencyButton = (JButton) lv.getComponent(8);
        return currencyButton != null && currencyButton.getText().equals("Change Net Profit Currency");
    }

    private JButton getLoginBtn() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("PortfolioTracker")) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        // Assuming LoginView is at index 1 in the JPanel
        LoginView lv = (LoginView) jp2.getComponent(1);

        JPanel buttons = (JPanel) lv.getComponent(5);

        // Assuming logIn button is at index 0 in the buttons JPanel
        return (JButton) buttons.getComponent(0);
    }

    private LabelTextPanel[] getTextfields() {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("PortfolioTracker")) {
                app = (JFrame) window;
            }
        }
        assertNotNull(app);

        Component root = app.getComponent(0);
        Component cp = ((JRootPane) root).getContentPane();
        JPanel jp = (JPanel) cp;
        JPanel jp2 = (JPanel) jp.getComponent(0);
        // Assuming LoginView is at index 1 in the JPanel
        LoginView lv = (LoginView) jp2.getComponent(1);

        LabelTextPanel usernameTextField = (LabelTextPanel) lv.getComponent(1);
        LabelTextPanel passwordTextField = (LabelTextPanel) lv.getComponent(3);
        // Assuming logIn button is at index 0 in the buttons
        return new LabelTextPanel[]{usernameTextField, passwordTextField};
    }

}
