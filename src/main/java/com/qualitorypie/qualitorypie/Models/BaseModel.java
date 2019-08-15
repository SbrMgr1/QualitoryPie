package com.qualitorypie.qualitorypie.Models;


import java.util.Map;

public abstract class BaseModel {

    public abstract String getTableName();
    public abstract Map<String, String> getTableSchema();
    public abstract String getPrimaryField();

}
