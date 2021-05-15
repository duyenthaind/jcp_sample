package com.thaind.race_condition.lazyinit;

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance(){
        if(instance == null){
            return new ExpensiveObject();
        }
        return instance;
    }
}

class ExpensiveObject{}
