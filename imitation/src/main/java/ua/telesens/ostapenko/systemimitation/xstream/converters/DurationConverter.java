package ua.telesens.ostapenko.systemimitation.xstream.converters;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.Duration;

public class DurationConverter implements Converter {

    public boolean canConvert(Class clazz) {
        return Duration.class.isAssignableFrom(clazz);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        Duration duration = (Duration) value;
        writer.setValue(duration.toString());
    }

    public Object unmarshal(HierarchicalStreamReader reader,
                            UnmarshallingContext context) {
        return Duration.parse(reader.getValue());
    }
}