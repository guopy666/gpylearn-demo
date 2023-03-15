package com.gpy.springalltong.mongodemo.converter;

import org.bson.Document;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.core.convert.converter.Converter;

/**
 * @ClassName MoneyReadConverter
 * @Description
 * @Author guopy
 * @Date 2023/3/9 17:49
 */
public class MoneyReadConverter implements Converter<Document, Money> {
    @Override
    public Money convert(Document document) {
        Document money = (Document)document.get("money");
        double amount = Double.parseDouble(money.getString("amount"));
        String currency = ((Document) money.get("currency")).getString("code");
        return Money.of(CurrencyUnit.of(currency), amount);
    }
}
