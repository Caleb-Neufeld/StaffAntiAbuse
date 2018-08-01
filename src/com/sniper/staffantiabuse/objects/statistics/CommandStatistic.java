package com.sniper.staffantiabuse.objects.statistics;

import com.sniper.staffantiabuse.objects.MathUtils;
import com.sniper.staffantiabuse.objects.StringSimilarity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandStatistic {

    private static ArrayList<CommandStatistic> commands = new ArrayList<>();

    private UUID id;
    private HashMap<UUID, Integer> playerFrequency;
    private HashMap<UUID, String> commandFrequency;
    private HashMap<UUID, String> nameFrequency;

    public CommandStatistic(UUID id) {
        this.id = id;
    }

    public HashMap<UUID, Integer> getPlayerFrequency() {
        return playerFrequency;
    }

    public HashMap<UUID, String> getCommandFrequency() {
        return commandFrequency;
    }

    public static ArrayList<CommandStatistic> getList() {
        return commands;
    }

    public HashMap<UUID, Double> getPlayerFrequencyTable() {
        HashMap<UUID, Double> table = new HashMap<>();
        int count = 0;
        for(Map.Entry<UUID, Integer> entry : getPlayerFrequency().entrySet())
            count += entry.getValue();
        for(Map.Entry<UUID, Integer> entry : getPlayerFrequency().entrySet()) {
            table.put(entry.getKey(), MathUtils.getPercentage(entry.getValue(), count));
        }
        return table;
    }

    public HashMap<String, Double> getCommandFrequencyTable() {
        HashMap<String, Double> table = new HashMap<>();
        int count = 0;
        for(Map.Entry<UUID, String> entry : getCommandFrequency().entrySet())
            for(Map.Entry<UUID, String> entry2 : getCommandFrequency().entrySet())
            if(StringSimilarity.similarity(entry.getValue(), entry2.getValue())>75)
            count++;
        for(Map.Entry<UUID, String> entry : getCommandFrequency().entrySet())
            for(Map.Entry<UUID, String> entry2 : getCommandFrequency().entrySet()) {
                int same = 0;
                if (StringSimilarity.similarity(entry.getValue(), entry2.getValue()) > 75)
                    same++;
                table.put(entry.getValue(), MathUtils.getPercentage(same, count));
            }
        return table;
    }

}
