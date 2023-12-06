package use_case.add_stock;

import entity.Investment;

import java.util.NoSuchElementException;

public interface InvestmentFactory {
    Investment createInvestment(AddStockInputData addStockInputData) throws NoSuchElementException;
}
