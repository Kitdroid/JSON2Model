package org.kitdroid.j2m;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import junit.framework.TestCase;
import org.kitdroid.j2m.config.Configuration;
import org.kitdroid.j2m.model.ObjectInfo;
import org.kitdroid.j2m.parser.JSONObject;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

/**
 * Created by baoyz on 15/3/22.
 */
public class ObjectInfoTest extends TestCase {

    public void testAddFields() {
        JSONObject jsonObject = new JSONObject(JSONString.SIMPLE);
        ObjectInfo oi = new ObjectInfo(jsonObject, Configuration.builder()
                .applyDefault()
                .setModelFieldGetterAndSetter(false)
                .setModelFieldModifiers(Modifier.PUBLIC)
                .build());

        print(oi.build());
    }

    public void testGetterAndSetter() {
        JSONObject jsonObject = new JSONObject(JSONString.SIMPLE);
        ObjectInfo oi = new ObjectInfo(jsonObject, Configuration.builder()
                .applyDefault()
                .build());

        print(oi.build());
    }

    public void testMultiObject() {
        JSONObject jsonObject = new JSONObject(JSONString.MULTI_OBJECT);
        ObjectInfo oi = new ObjectInfo(jsonObject, Configuration.builder()
                .applyDefault()
                .build());

        List<TypeSpec> list = oi.buildAll();
        for (TypeSpec typeSpec : list) {
            print(typeSpec);
        }
    }

    public void testMultiArray(){
        JSONObject jsonObject = new JSONObject(JSONString.PRODUCTS);
        ObjectInfo oi = new ObjectInfo(jsonObject, Configuration.builder()
                .applyDefault()
                .build());

        List<TypeSpec> list = oi.buildAll();
        for (TypeSpec typeSpec : list) {
            print(typeSpec);
        }
    }

    public void print(TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder("com.example", typeSpec)
                .build();
        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
