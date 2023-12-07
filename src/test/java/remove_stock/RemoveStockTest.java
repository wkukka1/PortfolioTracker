package remove_stock;

import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.StockInfoClient;
import entity.*;
import entity.Short;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.removeStock.RemoveStockPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import use_case.removeStock.*;
import use_case.show.StockPriceDataAccessInterface;
import use_case.PortfolioDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RemoveStockTest {
    private final PortfolioDataAccessInterface mockPortfolioDAO = Mockito.mock(FilePortfolioDataAccessObject.class);

    private final RemoveStockOutputBoundary mockPresenter = Mockito.mock(RemoveStockPresenter.class);
    private final RemoveStockUserDataAccessInterface userDataAccessInterface = Mockito.mock(RemoveStockUserDataAccessInterface.class);
    private RemoveStockInputBoundary removeStockInteractor;

    private void addUser(int numOfUsers) {
        Stock stock = new Stock("AAPL", LocalDateTime.now(), 1, 80);
        ArrayList<Investment> stockList = new ArrayList<>();
        stockList.add(stock);
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
            fpdao.savePortfolio(new Portfolio(stockList, 0, i));
        }
    }

    @Before
    public void setUp(){
        this.removeStockInteractor = new RemoveStockInteractor(userDataAccessInterface, mockPortfolioDAO, mockPresenter);
    }

    @Test
    public void testRemoveOneStock(){
        addUser(1);
        RemoveStockInputData mockInputData = new RemoveStockInputData("AAPL", "user0");
        removeStockInteractor.execute(mockInputData);
        verify(mockPresenter, times(1)).prepareSuccessView(any(RemoveStockOutputData.class));

    }
}
