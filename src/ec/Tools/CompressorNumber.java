package ec.Tools;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Texture;
import arc.graphics.g2d.PixmapRegion;
import arc.graphics.g2d.TextureAtlas;
import arc.graphics.g2d.TextureRegion;
import arc.struct.ObjectMap;
import mindustry.type.Item;
import mindustry.type.Liquid;

import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;

public class CompressorNumber {
    /*
    public static void Number(){
        int size = 40;
        for(var l : new Liquid[]{water, slag, oil, cryofluid,
                arkycite, gallium, neoplasm,
                ozone, hydrogen, nitrogen, cyanogen}){
            if(l.hidden) continue;
            ObjectMap<Integer, Cons<Liquid>> cons = new ObjectMap<>();
            for(int i = 1; i < 10; i++){
                int finalI = i;
                cons.put(i, ld -> {
                    PixmapRegion base = Core.atlas.getPixmap(l.uiIcon);
                    var mix = base.crop();
                    var number = Core.atlas.find(name("number-" + finalI));
                    if(number.found()) {
                        PixmapRegion region = TextureAtlas.blankAtlas().getPixmap(number);

                        mix.draw(region.pixmap, region.x, region.y, region.width, region.height, 0, base.height - size, size, size, false, true);
                    }

                    ld.uiIcon = ld.fullIcon = new TextureRegion(new Texture(mix));
                });
            }
            liquid(cons, l.name, l.color, l.explosiveness, l.flammability, l.heatCapacity, l.viscosity, l.temperature);
        }

        for(var item : new Item[]{scrap, copper, lead, graphite, coal, titanium, thorium, silicon, plastanium,
                phaseFabric, surgeAlloy, sporePod, sand, blastCompound, pyratite, metaglass,
                beryllium, tungsten, oxide, carbide, fissileMatter, dormantCyst}){
            if(item.hidden) continue;
            ObjectMap<Integer, Cons<Item>> cons = new ObjectMap<>();
            for(int i = 1; i < 10; i++){
                int finalI = i;
                cons.put(i, it -> {
                    PixmapRegion base = Core.atlas.getPixmap(item.uiIcon);
                    var mix = base.crop();
                    var number = Core.atlas.find(name("number-" + finalI));
                    if(number.found()) {
                        PixmapRegion region = TextureAtlas.blankAtlas().getPixmap(number);

                        mix.draw(region.pixmap, region.x, region.y, region.width, region.height, 0, base.height - size, size, size, false, true);
                    }

                    it.uiIcon = it.fullIcon = new TextureRegion(new Texture(mix));

                    it.buildable = item.buildable;
                    it.hardness = item.hardness + finalI;
                    it.lowPriority = item.lowPriority;
                });
            }
            item(cons, item.name, item.color, item.explosiveness, item.flammability, item.cost, item.radioactivity, item.charge, item.healthScaling);
        }
    }

     */
}
