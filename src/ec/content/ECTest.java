package ec.content;

import arc.util.Log;
import mindustry.ctype.MappableContent;
import mindustry.world.Block;

import java.lang.reflect.Field;

public class ECTest {
    public static void Fileds(MappableContent object,Class<?>[] clazzs) throws IllegalAccessException {
        Log.info(object.name);
        for (Class<?> clazz : clazzs){
            Log.info(clazz.getName());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(object);
                Log.info(name+":"+value);
            }
        }
    }
    public static void Fileds(MappableContent object,Class<?> clazz) throws IllegalAccessException {
        Fileds(object,new Class[]{clazz});
    }
}
