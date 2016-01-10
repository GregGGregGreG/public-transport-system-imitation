package ua.telesens.ostapenko.systemimitation.xstream.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.LocalTime;

public class LocaleTimeConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return LocalTime.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        LocalTime localTime = (LocalTime) value;
        writer.setValue(localTime.toString());
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        return LocalTime.parse(reader.getValue());
    }
}
