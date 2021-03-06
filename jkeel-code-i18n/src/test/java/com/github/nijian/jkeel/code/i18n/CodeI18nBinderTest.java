package com.github.nijian.jkeel.code.i18n;

import com.github.nijian.jkeel.code.entity.Code;
import com.github.nijian.jkeel.code.entity.CodeDef;
import com.github.nijian.jkeel.commons.feature.FeatureBinder;
import com.github.nijian.jkeel.i18n.I18nFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CodeI18nBinderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCodeI18n() {

        I18nFeature i18nFeature = new I18nFeature();
        CodeI18nBinder codeI18nBinder = new CodeI18nBinder(i18nFeature);

        Code code = new Code();

        List<FeatureBinder> binderList = new ArrayList<>();
        code.setFeatureBinderList(binderList);
        binderList.add(codeI18nBinder);

        CodeDef codeDef = new CodeDef();
        code.setCodeDef(codeDef);

        codeDef.setCode("xxx");
        codeDef.setKey("bbb");
        codeDef.setName("fff");

        code.applyFeatures();

        System.out.println(code.getDisplayName());

    }
}