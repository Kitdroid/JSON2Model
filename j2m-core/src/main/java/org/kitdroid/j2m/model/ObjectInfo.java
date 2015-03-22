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

import com.squareup.javapoet.*;
import com.sun.xml.internal.xsom.util.TypeSet;
import org.kitdroid.j2m.config.Configuration;
import org.kitdroid.j2m.parser.JSONObject;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @User baoyz
 * @Date 15/3/22
 * @Email baoyz94@gmail.com
 * @Github baoyongzhang
 */
public class ObjectInfo {

    private Configuration config;
    private List<FieldInfo> fields;
    private String className;
    private TypeSpec.Builder builder;
    private List<ObjectInfo> objectInfoList;

    public ObjectInfo(JSONObject jsonObject, Configuration config) {
        this(config.getDefaultClassName(), jsonObject, config);
    }

    public ObjectInfo(String className, JSONObject jsonObject, Configuration config) {
        this.className = className;
        this.config = config;
        fields = new ArrayList<FieldInfo>();
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = jsonObject.get(key);
            fields.add(new FieldInfo(config, key, value));
        }


        builder = TypeSpec.classBuilder(className)
                .addModifiers(config.getModelClassModifiers());
        addFields(builder);
        if (config.isModelFieldGetterAndSetter()) {
            addGetterAndSetter(builder);
        }
    }

    private void addFields(TypeSpec.Builder builder) {
        for (FieldInfo field : fields) {
            FieldSpec fs = FieldSpec.builder(field.getFieldType(), field.getName(), config.getModelFieldModifiers()).build();
            builder.addField(fs);
            if (field.getObjectInfo() != null) {
                if (objectInfoList == null)
                    objectInfoList = new ArrayList<ObjectInfo>();
                objectInfoList.add(field.getObjectInfo());
            }
        }
    }

    private void addGetterAndSetter(TypeSpec.Builder builder) {
        for (FieldInfo field : fields) {
            // add getter method
            MethodSpec getter = MethodSpec.methodBuilder(field.getGetterMethodName())
                    .addModifiers(config.getModelGetterModifiers())
                    .returns(field.getFieldType())
                    .addStatement("return $N", field.getName())
                    .build();
            builder.addMethod(getter);

            // add setter method
            MethodSpec setter = MethodSpec.methodBuilder(field.getSetterMethodName())
                    .addModifiers(config.getModelSetterModifiers())
                    .returns(TypeName.VOID)
                    .addParameter(field.getFieldType(), field.getName())
                    .addStatement("this.$N = $N", field.getName(), field.getName())
                    .build();
            builder.addMethod(setter);
        }
    }

    public TypeSpec build(){
        return builder.build();
    }

    public List<TypeSpec> buildAll() {
        List<TypeSpec> list = new ArrayList<TypeSpec>();
        if (objectInfoList != null) {
            for (ObjectInfo objectInfo : objectInfoList) {
                list.addAll(objectInfo.buildAll());
            }
        }
        list.add(builder.build());
        return list;
    }
}
