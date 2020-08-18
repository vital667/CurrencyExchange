public class Main {
    public static void main(String[] args) {
        ExchangeOffice exchangeOffice = new ExchangeOffice(20000.0);
        //ExchangeOffice exchangeOffice = new ExchangeOffice();
        exchangeOffice.showFunds();
        System.out.println();
        exchangeOffice.exchange(10000, Currency.PLN, Currency.USD);
        exchangeOffice.showFunds();
    }
}
