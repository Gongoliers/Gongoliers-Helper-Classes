package com.thegongoliers.output.drivetrain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A base drive module from which new drive modules should extend. Includes support for the values map.
 */
public abstract class BaseDriveModule implements DriveModule {

    protected Map<String, Object> values;

    public BaseDriveModule(){
        values = new HashMap<>();
    }

    @Override
    public abstract DriveSpeed run(DriveSpeed currentSpeed, DriveSpeed desiredSpeed, double deltaTime);

    @Override
    public abstract String getName();

    @Override
    public Object getValue(String name) {
        return values.get(name);
    }

    @Override
    public void setValue(String name, Object value) {
        if (values.containsKey(name)){
            values.put(name, value);
        }
    }

    @Override
    public Set<String> getValueNames(){
        return values.keySet();
    }

}