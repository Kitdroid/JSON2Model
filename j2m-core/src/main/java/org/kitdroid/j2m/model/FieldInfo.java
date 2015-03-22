/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 baoyz@kitdroid <baoyz@kitdroid.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.kitdroid.j2m.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.kitdroid.j2m.config.Configuration;
import org.kitdroid.j2m.parser.JSONArray;
import org.kitdroid.j2m.parser.JSONObject;
import org.kitdroid.j2m.utils.StringUtils;

/**
 * @User baoyz
 * @Date 15/3/22
 * @Email baoyz94@gmail.com
 * @Github baoyongzhang
 */
public class FieldInfo {

    private Configuration config;
    private String name;
    private Object value;
    private ObjectInfo objectInfo;

    public FieldInfo(Configuration config, String name, Object value) {
        this.config = config;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getGetterMethodName() {
        StringBuilder sb = new StringBuilder();
        String prefix = "get";
        if (config.isModelFieldBasicType() && value instanceof Boolean) {
            prefix = "is";
        }
        sb.append(prefix).append(StringUtils.toFirstUppercase(name));
        return sb.toString();
    }

    public String getSetterMethodName() {
        StringBuilder sb = new StringBuilder();
        sb.append("set").append(StringUtils.toFirstUppercase(name));
        return sb.toString();
    }

    public TypeName getFieldType() {
        TypeName type = null;
        if (config.isModelFieldBasicType()) {
            if (value instanceof Boolean)
                type = TypeName.BOOLEAN;
            if (value instanceof Integer)
                type = TypeName.INT;
            if (value instanceof Double)
                type = TypeName.DOUBLE;
            if (value instanceof Long)
                type = TypeName.LONG;
        }
        if (type != null)
            return type;
        String typeName = value.getClass().getSimpleName();
        if ("JSONArray".equals(typeName)) {
            String className = StringUtils.toFirstUppercase(name);

            // generics type
            type = ParameterizedTypeName.get(ClassName.get("java.util", "List"), ClassName.get(config.getPackageName(), className));

            // TODO Array嵌套？
            JSONArray jsonArray = (JSONArray) value;
            if (!jsonArray.isNull(0)){
                Object o = jsonArray.get(0);
                if (o instanceof JSONObject) {
                    objectInfo = new ObjectInfo(className, (JSONObject) o, config);
                }
            }
        } else if ("JSONObject".equals(typeName)) {
            JSONObject jsonObject = (JSONObject) value;
            String className = StringUtils.toFirstUppercase(name);
            // Field Model Class
            objectInfo = new ObjectInfo(className, jsonObject, config);
            type = ClassName.get(config.getPackageName(), className);
        } else {
            type = TypeName.get(value.getClass());
        }
        return type;
    }

    public ObjectInfo getObjectInfo() {
        return objectInfo;
    }
}
