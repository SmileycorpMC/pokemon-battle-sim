package net.smileycorp.battlesimulator.common.stat;

import java.util.HashMap;
import java.util.Map;

public class StatStageMap {

    private Map<IEnumStat, Integer> map = new HashMap<>();

    public void setStatStage(IEnumStat stat, int value) {
        if (stat == EnumStat.HP) return;
        map.put(stat, Math.max(-6, Math.min(6, value)));
    }

    public void addStatStage(IEnumStat stat, int value) {
        if (stat == EnumStat.HP) return;
        map.put(stat, Math.max(-6, Math.min(6, value + getStatStage(stat))));
    }

    public int getStatStage(IEnumStat stat) {
        if (stat == EnumStat.HP) return 0;
        return map.containsKey(stat) ? map.get(stat) : 1;
    }

    public double getStatMultiplier(IEnumStat stat) {
        if (stat == EnumStat.HP) return 1;
        int stage = getStatStage(stat);
        if (stage == 0) return 1;
        if (stat instanceof EnumStat) return stage > 0 ? (2d + stage) / 2d : 2d / (2 + Math.abs(stage));
        if (stat == EnumBattleStat.EVASION) stage = -stage;
        return stage > 0 ? (3d + stage) / 3d : 3d / (3 - stage);
    }

}
