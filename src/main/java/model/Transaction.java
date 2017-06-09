package model;

import java.time.LocalDate;

public class Transaction {
    private Integer id;
    private LocalDate dateOfTransaction;
    private String transactionTypeName;
    private String transactionTypeDescription;
    private long value;
    private String description;
    private String transactionStatusName;
    private String transactionStatusDescription;
    private AbstractAccount sourceAccount;
    private Card sourceCard;
    private AbstractAccount destinationAccount;


    public Transaction(Integer id, LocalDate dateOfTransaction, String transactionTypeName,
        String transactionTypeDescription, long value, String description,
        String transactionStatusName, String transactionStatusDescription, AbstractAccount sourceAccount,
        Card sourceCard, AbstractAccount destinationAccount) {
        this.id = id;
        this.dateOfTransaction = dateOfTransaction;
        this.transactionTypeName = transactionTypeName;
        this.transactionTypeDescription = transactionTypeDescription;
        this.value = value;
        this.description = description;
        this.transactionStatusName = transactionStatusName;
        this.transactionStatusDescription = transactionStatusDescription;
        this.sourceAccount = sourceAccount;
        this.sourceCard = sourceCard;
        this.destinationAccount = destinationAccount;
    }


    public Transaction(LocalDate dateOfTransaction, String transactionTypeName,
        String transactionTypeDescription, long value, String description,
        String transactionStatusName, String transactionStatusDescription, AbstractAccount sourceAccount,
        Card sourceCard, AbstractAccount destinationAccount) {
        this.dateOfTransaction = dateOfTransaction;
        this.transactionTypeName = transactionTypeName;
        this.transactionTypeDescription = transactionTypeDescription;
        this.value = value;
        this.description = description;
        this.transactionStatusName = transactionStatusName;
        this.transactionStatusDescription = transactionStatusDescription;
        this.sourceAccount = sourceAccount;
        this.sourceCard = sourceCard;
        this.destinationAccount = destinationAccount;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDateOfTransaction() {
        return dateOfTransaction;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public String getTransactionTypeDescription() {return transactionTypeDescription;}

    public long getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionStatusName() {
        return transactionStatusName;
    }

    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    public AbstractAccount getSourceAccount() {
        return sourceAccount;
    }


    public Card getSourceCard() {
        return sourceCard;
    }

    public AbstractAccount getDestinationAccount() {
        return destinationAccount;
    }

}
