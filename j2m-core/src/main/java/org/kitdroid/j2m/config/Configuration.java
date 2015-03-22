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
package org.kitdroid.j2m.config;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @User baoyz
 * @Date 15/3/22
 * @Email baoyz94@gmail.com
 * @Github baoyongzhang
 */
public class Configuration {

    private Configuration() {
        modelClassModifiers = new ArrayList<Modifier>();
        modelFieldModifiers = new ArrayList<Modifier>();
    }

    private List<Modifier> modelClassModifiers;
    private List<Modifier> modelFieldModifiers;
    private List<Modifier> modelGetterModifiers;
    private List<Modifier> modelSetterModifiers;
    private boolean modelFieldGetterAndSetter;
    private boolean modelFieldBasicType;
    private String packageName;
    private String defaultClassName;

    public Modifier[] getModelClassModifiers() {
        return modelClassModifiers.toArray(new Modifier[]{});
    }

    public Modifier[] getModelFieldModifiers() {
        return modelFieldModifiers.toArray(new Modifier[]{});
    }

    public boolean isModelFieldGetterAndSetter() {
        return modelFieldGetterAndSetter;
    }

    public boolean isModelFieldBasicType() {
        return modelFieldBasicType;
    }

    public Modifier[] getModelGetterModifiers() {
        return modelGetterModifiers.toArray(new Modifier[]{});
    }

    public Modifier[] getModelSetterModifiers() {
        return modelSetterModifiers.toArray(new Modifier[]{});
    }

    public String getPackageName() {
        return packageName;
    }

    public String getDefaultClassName() {
        return defaultClassName;
    }

    public static class Builder {

        private final Configuration config;

        public Builder() {
            config = new Configuration();
        }

        public Builder setModelClassModifiers(Modifier modifiers) {
            config.modelClassModifiers = new ArrayList<Modifier>(Arrays.asList(modifiers));
            return this;
        }

        public Builder setModelFieldModifiers(Modifier... modifiers) {
            config.modelFieldModifiers = new ArrayList<Modifier>(Arrays.asList(modifiers));
            return this;
        }

        public Builder setModelGetterModifiers(Modifier... modifiers) {
            config.modelGetterModifiers = new ArrayList<Modifier>(Arrays.asList(modifiers));
            return this;
        }

        public Builder setModelSetterModifiers(Modifier... modifiers) {
            config.modelSetterModifiers = new ArrayList<Modifier>(Arrays.asList(modifiers));
            return this;
        }

        public Builder setModelFieldGetterAndSetter(boolean modelFieldGetterAndSetter) {
            config.modelFieldGetterAndSetter = modelFieldGetterAndSetter;
            return this;
        }

        public Builder setModelFieldBasicType(boolean modelFieldBasicType) {
            config.modelFieldBasicType = modelFieldBasicType;
            return this;
        }

        public Builder setPackageName(String packageName) {
            config.packageName = packageName;
            return this;
        }

        public Builder setDefaultClassName(String className) {
            config.defaultClassName = className;
            return this;
        }

        public Configuration build() {
            return config;
        }

        public Builder applyDefault() {
            setModelClassModifiers(Modifier.PUBLIC);
            setModelFieldModifiers(Modifier.PRIVATE);
            setModelGetterModifiers(Modifier.PUBLIC);
            setModelSetterModifiers(Modifier.PUBLIC);
            setModelFieldGetterAndSetter(true);
            setModelFieldBasicType(true);
            setPackageName("com.example");
            setDefaultClassName("Example");
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
