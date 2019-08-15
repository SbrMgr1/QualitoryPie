package com.qualitorypie.qualitorypie.Models;


import java.util.HashMap;
import java.util.Map;

public class BorrowModel extends BaseModel{

    private static String table_name = "borrow_list";
    private Integer id;
    private  Integer user_id;
    private Integer prod_id;
    private Float borrow_amt;
    private String remarks;
    private String added_at;
    private Integer is_sinked;
    private Integer deleted;

    private String product_name;

    public String getTableName() {
        return table_name;
    }

    @Override
    public Map<String, String> getTableSchema() {
        Map<String, String> schema = new HashMap<String, String>();
        schema.put(this.getPrimaryField(),"INTEGER PRIMARY KEY AUTOINCREMENT");
        schema.put("user_id","VARCHAR(50)");
        schema.put("prod_id","INT(11)");
        schema.put("borrow_amt","DECIMAL(10,2)");
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

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setProd_id(Integer prod_id) {
        this.prod_id = prod_id;
    }

    public void setBorrow_amt(Float borrow_amt) {
        this.borrow_amt = borrow_amt;
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

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getProd_id() {
        return prod_id;
    }

    public Float getBorrow_amt() {
        return borrow_amt;
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

    public String getProduct_name() {
        return product_name;
    }

    public Integer getDeleted() {
        return deleted;
    }
}
