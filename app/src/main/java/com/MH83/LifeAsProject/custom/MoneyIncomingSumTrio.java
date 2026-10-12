package com.MH83.LifeAsProject.custom;

import com.MH83.LifeAsProject.model.Money.MoneyAccountingSum;

public class MoneyIncomingSumTrio
{
    private MoneyAccountingSum _incomMAS;
    private MoneyAccountingSum _walletMAS;

    public void SetIncomMoneyAccountingSum (MoneyAccountingSum incomMAS)
    {
        _incomMAS = incomMAS;
        _walletMAS = null;
    }

    public void SetWalletMoneyAccountingSum (MoneyAccountingSum walletMAS)
    {
        _incomMAS = null;
        _walletMAS = walletMAS;
    }

    public MoneyAccountingSum GetIncomMoneyAccountingSum() { return _incomMAS; }

    public MoneyAccountingSum GetWalletMoneyAccountingSum() { return _walletMAS; }
}
