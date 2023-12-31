package remove_stock;

import data_access.FilePortfolioDataAccessObject;
import data_access.FileUserDataAccessObject;
import entity.*;
import interface_adapter.removeStock.RemoveStockPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import use_case.removeStock.*;
import use_case.PortfolioDataAccessInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RemoveStockTest<E> {
    private final PortfolioDataAccessInterface mockPortfolioDAO = Mockito.mock(FilePortfolioDataAccessObject.class);

    private final RemoveStockOutputBoundary mockPresenter = Mockito.mock(RemoveStockPresenter.class);
    private final RemoveStockUserDataAccessInterface userDataAccessInterface = Mockito.mock(FileUserDataAccessObject.class);
    private RemoveStockInputBoundary removeStockInteractor;

    private User addUser(int numOfUsers) {
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
        return fudao.getUserFromUsername("user0");
    }

    @Before
    public void setUp(){
        this.removeStockInteractor = new RemoveStockInteractor(userDataAccessInterface, mockPortfolioDAO, mockPresenter);
    }

    @Test
    public void testRemoveOneStock(){
        User user = addUser(1);

        Stock stock = new Stock("AAPL", LocalDateTime.now(), 1, 80);
        ArrayList<Investment> stockList = new ArrayList<>();
        stockList.add(stock);

        Portfolio p = new Portfolio(stockList, 0, 0);

        RemoveStockInputData mockInputData = new RemoveStockInputData("AAPL", "user0");

        when(userDataAccessInterface.getUserFromUsername(anyString())).thenReturn(user);
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(p);

        removeStockInteractor.execute(mockInputData);
        verify(mockPresenter, times(1)).prepareSuccessView(any(RemoveStockOutputData.class));
    }

    @Test
    public void testRemoveMultipleStocks(){
        User user = addUser(1);

        Stock stock = new Stock("AAPL", LocalDateTime.now(), 1, 80);
        Stock stock2 = new Stock("GOOGL", LocalDateTime.now(), 1, 80);
        ArrayList<Investment> stockList = new ArrayList<>();
        stockList.add(stock);
        stockList.add(stock2);

        Portfolio p = new Portfolio(stockList, 0, 0);

        RemoveStockInputData mockInputData = new RemoveStockInputData("GOOGL", "user0");

        when(userDataAccessInterface.getUserFromUsername(anyString())).thenReturn(user);
        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(p);

        removeStockInteractor.execute(mockInputData);
        verify(mockPresenter, times(1)).prepareSuccessView(any(RemoveStockOutputData.class));
    }

    @Test
    public void testFailView(){
        Portfolio p = new Portfolio(new ArrayList<>(), 0, 0);

        RemoveStockInputData mockInputData = new RemoveStockInputData("GOOGL", "user0");

        when(mockPortfolioDAO.getPortfolioByID(anyInt())).thenReturn(p);

        removeStockInteractor.execute(mockInputData);
        verify(mockPresenter, times(1)).prepareFailView(anyString());
    }

}
