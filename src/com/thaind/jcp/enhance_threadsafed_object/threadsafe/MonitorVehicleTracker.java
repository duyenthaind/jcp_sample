package com.thaind.jcp.enhance_threadsafed_object.threadsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.thaind.jcp.enhance_threadsafed_object.not_threadsafe.MutablePoint;
import com.thaind.race_condition.lazyinit.ThreadSafe;

//threadsafe
@ThreadSafe
public class MonitorVehicleTracker {
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> iMap) {
        this.locations = deepCopy(iMap);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return this.locations;
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint location = locations.get(id);
        return location == null ? null : new MutablePoint(location);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint location = this.locations.get(id);
        if (location == null) {
            throw new IllegalArgumentException("No such id : " + id);
        }
        location.x = x;
        location.y = y;
    }

    private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> iMap) {
        Map<String, MutablePoint> result = new HashMap<>();
        for (String id : iMap.keySet()) {
            result.put(id, new MutablePoint(iMap.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}
