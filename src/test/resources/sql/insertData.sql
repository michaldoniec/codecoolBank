INSERT INTO Customers (FirstName, LastName, Login, Password, CreateDate, IsActive, LastLogin) VALUES(
'Mike', 'Smith', 'Abc', 'Def', '2017-01-01', 1, '2017-03-03');
INSERT INTO Customers (FirstName, LastName, Login, Password, CreateDate, IsActive, LastLogin) VALUES(
'Tax Office', 'Tax Office', 'Tax', 'LoveTax', '2016-01-01', 1, '2017-04-03');
INSERT INTO Accounts (CustomerID, Number, AccountTypeID, AccountStatusID, OpenDate, Balance, DebitLine, Interest) VALUES
(1, '12233444ds', 1, 1, '2017-01-01', 80000, 6000, 5);
INSERT INTO Accounts (CustomerID, Number, AccountTypeID, AccountStatusID, OpenDate, Balance, DebitLine, Interest) VALUES
(2, '666NBP', 1, 1, '2016-01-01', 800000000000000, 60000000000000, 10);
INSERT INTO AccountStatuses (Name, Description) VALUES(
'Active account', 'Active account status');
INSERT INTO AccountStatuses (Name, Description) VALUES(
'Disabled account', 'Disabled account status');
INSERT INTO AccountTypes (Name, Description) VALUES(
'Saving account', 'Saving account description');
INSERT INTO AccountTypes(Name, Description) VALUES(
'Credit account', 'Credit account description');
INSERT INTO CardStatuses (Name, Description) VALUES(
'Active card', 'Active card status');
INSERT INTO CardStatuses (Name, Description) VALUES(
'Disabled card', 'Disabled card status');
INSERT INTO CardTypes (Name, Description) VALUES(
'Credit card', 'Credit card description');
INSERT INTO CardTypes (Name, Description) VALUES(
'Debit card', 'Debit card description');
INSERT INTO TransactionStatuses (Name, Description) VALUES(
'Completed transaction', 'Completed transaction description');
INSERT INTO TransactionStatuses (Name, Description) VALUES(
'Waiting transaction', 'Waiting transaction description');
INSERT INTO TransactionTypes (Name, Description) VALUES(
'IRS transaction', 'Tax transaction');
INSERT INTO TransactionTypes (Name, Description) VALUES(
'ZUS transaction', 'Social security transaction');
INSERT INTO TransactionTypes (Name, Description) VALUES(
'Other transaction', 'Not tax or social security transation');
INSERT INTO Transactions (DateOfTransaction, TransactionTypeID, Value, Description, TransactionStatusID, SourceAccountID,
 SourceCardID, DestinationAccountID) VALUES(
'2017-01-01', 1, 2000, 'Income tax for 2017', 1, 1, 1, 2);
INSERT INTO Transactions (DateOfTransaction, TransactionTypeID, Value, Description, TransactionStatusID, SourceAccountID,
 SourceCardID, DestinationAccountID) VALUES(
'2017-01-01', 1, 2000, 'Social insurance 03.2017', 2, 2, 1, 2);
INSERT INTO Cards (CardNumber, CardTypeID, Validity, BuyingLimit, CashWithdrawLimit, `Limit`, AccountID) VALUES(
'12334', 1, '2018-01-01', 2000, 3000, 5000, 1);
