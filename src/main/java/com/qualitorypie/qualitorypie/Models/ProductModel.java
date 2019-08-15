package com.qualitorypie.qualitorypie.Models;

import java.util.HashMap;
import java.util.Map;

public class ProductModel extends BaseModel{

    private final String  table_name = "products";
    private Integer id;
    private String product_name;
    private String wholesale_price;
    private String retail_price;
    private String remarks;
    private String added_at;
    private Integer is_sinked;
    private Integer deleted;


    public String getTableName(){
        return table_name;
    }
    @Override
    public Map<String, String> getTableSchema() {
        Map<String, String> schema = new HashMap<String, String>();
        schema.put(this.getPrimaryField(),"INTEGER PRIMARY KEY AUTOINCREMENT");
        schema.put("product_name","VARCHAR(50)");
        schema.put("wholesale_price","DECIMAL(10,2)");
        schema.put("retail_price","DECIMAL(10,2)");
        schema.put("remarks","TEXT");
        schema.put("added_at","DATETIME");
        schema.put("is_sinked","BIT(1)");
        schema.put("deleted","BIT(1)");
        return schema;
    }

    @Override
    public String getPrimaryField() {
        return "id";
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getWholesale_price() {
        return wholesale_price;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getAdded_at() {
        return added_at;
    }

    public Integer getIs_sinked() {
        return is_sinked;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setWholesale_price(String wholesale_price) {
        this.wholesale_price = wholesale_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public void setIs_sinked(Integer is_sinked) {
        this.is_sinked = is_sinked;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
