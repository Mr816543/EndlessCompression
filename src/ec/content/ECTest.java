package ec.content;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.content.TechTree;
import mindustry.ctype.MappableContent;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.PowerDistributor;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.consumers.ConsumeLiquid;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static ec.content.ECBlocks.ECBlocks;
import static ec.content.ECItems.ECItems;
import static ec.content.ECLiquids.ECLiquids;
import static mindustry.content.TechTree.node;

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
    public static void getConsume(Block block) throws IllegalAccessException {
        Field[] fields =Block.class.getDeclaredFields();
        for (Field field : fields) {
            //允许通过反射访问私有变量
            field.setAccessible(true);
            //获取属性名
            String name0 = field.getName();
            //判断是否为final修饰的属性
            if (!Modifier.isFinal(field.getModifiers())) {
                //获取原物品属性的属性值
                Object value = field.get(block);
                //将新物品的属性设置为和原物品相同
                if (name0.equals("consumeBuilder")) {
                    Seq<Consume> consumeBuilder = (Seq<Consume>) value;
                    Log.info(block.localizedName + ":"+ consumeBuilder.size + " Consume");
                }
            }
        }
    }
}
