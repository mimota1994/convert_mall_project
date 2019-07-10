package com.mimota.util;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("decimalConverter")
public class BigDecimalConverter extends TypeConverter implements SimpleValueConverter {

    public BigDecimalConverter() {
        super(BigDecimal.class);
    }

    @Override
    public Object encode(Object value, MappedField optionalExtraInfo) {
        BigDecimal val = (BigDecimal) value;
        if (val == null)
            return null;
        return val.toPlainString();
    }

    @Override
    public Object decode(Class targetClass, Object fromDBObject,
                         MappedField optionalExtraInfo) {
        if (fromDBObject == null)
            return null;
        BigDecimal dec = new BigDecimal(fromDBObject.toString());
        return dec;
    }

}
