package ec.Tools;

import arc.func.Boolp;
import arc.util.*;
import arc.util.Timer.Task;

public class Tool {
    public static void forceRun(Boolp boolp) {
        Timer.schedule(new Task() {
                           public void run() {
                               try {
                                   if (boolp.get()) cancel();
                               } catch (Throwable e) {
                                   Log.err(e);
                                   cancel();
                               }
                           }
                       }, 0f, 0.5f, -1
        );


    }


}
