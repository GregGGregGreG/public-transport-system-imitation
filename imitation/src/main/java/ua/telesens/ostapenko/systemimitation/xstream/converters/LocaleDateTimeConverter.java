package ua.telesens.ostapenko.systemimitation.xstream.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.LocalDateTime;

public class LocaleDateTimeConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return LocalDateTime.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        LocalDateTime localDateTime = (LocalDateTime) value;
        writer.setValue(localDateTime.toString());
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        return LocalDateTime.parse(reader.getValue());
    }
}