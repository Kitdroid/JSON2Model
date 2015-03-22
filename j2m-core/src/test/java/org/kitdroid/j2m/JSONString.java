package org.kitdroid.j2m;

/**
 * Created by baoyz on 15/3/22.
 */
public class JSONString {

    public static final String SIMPLE = "{\"name\" : \"jack\", \"age\" : 20, height : 183.2, is : true, items : [1, 2, 3]}";
    public static final String MULTI_OBJECT = "{\"name\" : \"牛奶\", \"price\" : 20.5, \"sum\" : 2, \"vip\" : true, \"category\" : {\"name\" : \"饮品\", \"code\" : 10010}}";
//    {"category" : {"name" : "食品", "code" : "10020"}, "products" : [{"name" : "方便面", "price" : 4.5}, {"name" : "火腿肠", "price" : 2.5}]}
    public static final String PRODUCTS = "{\"category\" : {\"name\" : \"食品\", \"code\" : \"10020\"}, \"products\" : [{\"name\" : \"方便面\", \"price\" : 4.5}, {\"name\" : \"火腿肠\", \"price\" : 2.5}]}";
}
