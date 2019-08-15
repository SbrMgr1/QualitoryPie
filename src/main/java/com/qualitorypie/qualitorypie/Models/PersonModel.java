package com.qualitorypie.qualitorypie.Models;

import java.util.HashMap;
import java.util.Map;

public class PersonModel extends BaseModel{
    //defining the field names
    private Integer id;
    private Integer person_type;
    private String person_name;
    private String phone_no = "";
    private String address = "";
    private String remarks = "";
    private String added_at;
    private Float remaining_amt;
    private Integer is_sinked = 0;
    private Integer deleted = 0;
    @Override
    public String getTableName() {
        return "person_list";
    }

    @Override
    public Map<String, String> getTableSchema() {
        Map<String,String> schema = new HashMap<String, String>();
        schema.put(this.getPrimaryField(),"INTEGER PRIMARY KEY AUTOINCREMENT");
        schema.put("person_type","TINYINT");
        schema.put("person_name","VARCHAR(50)");
        schema.put("phone_no","VARCHAR(50)");
        schema.put("address","VARCHAR(50)");
        schema.put("remarks","TEXT");
        schema.put("added_at","DATETIME");
        schema.put("remaining_amt","DECIMAL(10,2)");
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

    public void setPerson_type(Integer person_type) {
        this.person_type = person_type;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setRemaining_amt(Float remaining_amt) {
        this.remaining_amt = remaining_amt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPerson_type() {
        return person_type;
    }

    public String getPerson_name() {
        return person_name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getAddress() {
        return address;
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

    public Float getRemaining_amt() {
        return remaining_amt;
    }

    public Map<Integer,String> getPersonTypes(){
        Map<Integer,String> person_type = new HashMap<Integer,String>();
        person_type.put(1,"Retailer");
        person_type.put(2,"customer");
        return person_type;
    }
}
