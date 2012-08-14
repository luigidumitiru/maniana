/*
 * Copyright (C) 2011 The original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.zapta.apps.maniana.settings;

import static com.zapta.apps.maniana.util.Assertions.check;

import javax.annotation.Nullable;

import android.content.Context;
import android.graphics.Typeface;

import com.zapta.apps.maniana.R;
import com.zapta.apps.maniana.annotations.ApplicationScope;
import com.zapta.apps.maniana.util.EnumUtil;
import com.zapta.apps.maniana.util.EnumUtil.KeyedEnum;

/**
 * Represents possible values of Font preference.
 * 
 * @author Tal Dayan
 */
@ApplicationScope
public enum Font implements KeyedEnum {
    // NOTE: font keys are persisted in preferences. Do not modify.
    // @formatter:off
    CURSIVE(
        R.string.font_name_Cursive,
        "cursive",
        1.5f,
        0.75f,
        0.4f,
        null,
        "fonts/Vavont/Vavont-modified.ttf"),
            
    ELEGANT(
        R.string.font_name_Elegant,
        "elegant",
        1.6f,
        1.0f,
        0.0f,
        null,
        "fonts/Pompiere/Pompiere-Regular-modified.ttf"),
            
    SAN_SERIF(
        R.string.font_name_Sans_Serif, 
        "sans", 
        1.2f, 
        1.1f, 
        0.0f,
        Typeface.SANS_SERIF, 
        null),
            
    SERIF(
        R.string.font_name_Serif, 
        "serif", 
        1.2f, 
        1.1f, 
        0.0f,
        Typeface.SERIF, 
        null),
    
    IMPACT(
        R.string.font_name_Impact, 
        "impact", 
        1.6f,  
        0.7f, 
        0.3f,
        null, 
        "fonts/Damion/Damion-Regular.ttf");
    // @formatter:on

    /** User visible name. */
    private final int nameResourceId;

    /**
     * Preference value key. Should match the values in preference xml. Persisted in user's
     * settings.
     */
    private final String mKey;

    /** Relative scale to normalize size among font types. */
    public final float scale;

    public final float lineSpacingMultipler;
    public final float lastLineExtraSpacingFraction;

    /** The standard typeface of null if this is an custom font. */
    @Nullable
    private final Typeface mSysTypeface;

    /** Asset font file path or null if this is standard font. */
    @Nullable
    final String mAssetFilePath;

    private Font(int nameResourceId, String key, float scale, float lineSpacingMultipler,
            float lastLineExtraSpacingFraction, @Nullable Typeface sysTypeface,
            @Nullable String assertFilePath) {
        this.nameResourceId = nameResourceId;
        this.mKey = key;
        this.scale = scale;
        this.lineSpacingMultipler = lineSpacingMultipler;
        this.lastLineExtraSpacingFraction = lastLineExtraSpacingFraction;
        this.mSysTypeface = sysTypeface;
        this.mAssetFilePath = assertFilePath;

        // Exactly one of the two should be non null.
        check((mSysTypeface == null) != (mAssetFilePath == null));
    }

    @Override
    public final String getKey() {
        return mKey;
    }

    public final String getName(Context context) {
        return context.getString(nameResourceId);
    }

    /** Return value with given key, fallback value if not found. */
    @Nullable
    public final static Font fromKey(String key, @Nullable Font fallBack) {
        return EnumUtil.fromKey(key, Font.values(), fallBack);
    }

    public final Typeface getTypeface(Context context) {
        // TODO: is it ok to mix typefaces between contexts?
        if (mSysTypeface != null) {
            return mSysTypeface;
        }
        return Typeface.createFromAsset(context.getAssets(), mAssetFilePath);
    }
}