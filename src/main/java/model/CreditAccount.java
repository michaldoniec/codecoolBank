package model;

import java.time.LocalDate;

public class CreditAccount extends AbstractAccount {
    private Integer id;
    private Customer customer;
    private String number;
    private String statusName;
    private String statusDescription;
    private String typeDescription;
    private LocalDate openDate;
    private long balance;
    private long debitLine;
    private Integer interest;

    public CreditAccount(Integer id, Customer customer, String number, String typeDescription, String statusName,
        String statusDescription, LocalDate openDate, long balance,
        long debitLine, Integer interest) {

        super(id,customer, number, typeDescription, statusName, statusDescription, openDate, balance, debitLine,
            interest);
    }

    public CreditAccount(Customer customer, String number, String typeDescription, String statusName,
        String statusDescription, LocalDate openDate, long balance,
        long debitLine, Integer interest) {

        super(customer, number, typeDescription, statusName, statusDescription, openDate, balance, debitLine,
            interest);
    }
}

