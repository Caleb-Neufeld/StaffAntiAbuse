package com.sniper.staffantiabuse.managers.sessions;

import com.sniper.staffantiabuse.objects.statistics.CommandStatistic;
import com.sniper.staffantiabuse.objects.statistics.DropStatistic;

public class PlayerSession {

    DropStatistic drop;
    CommandStatistic cmd;

    public PlayerSession(DropStatistic drop, CommandStatistic cmd) {
        this.drop = drop;
        this.cmd = cmd;
    }

    public CommandStatistic getCommandStatistic() {
        return cmd;
    }

    public DropStatistic getDropStatistic() {
        return drop;
    }


}
