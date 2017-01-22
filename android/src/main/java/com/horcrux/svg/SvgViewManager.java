/**
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */


package com.horcrux.svg;

import android.graphics.Bitmap;

import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNodeAPI;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * ViewManager for RNSVGSvgView React views. Renders as a {@link SvgView} and handles
 * invalidating the native view on shadow view updates happening in the underlying tree.
 */
public class SvgViewManager extends BaseViewManager<SvgView, SvgViewShadowNode> {

    private static final String REACT_CLASS = "RNSVGSvgView";
    private static final YogaMeasureFunction MEASURE_FUNCTION = new YogaMeasureFunction() {
        @Override
        public long measure(
                YogaNodeAPI node,
                float width,
                YogaMeasureMode widthMode,
                float height,
                YogaMeasureMode heightMode) {
            throw new IllegalStateException("SurfaceView should have explicit width and height set");
        }
    };

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public Class<SvgViewShadowNode> getShadowNodeClass() {
        return SvgViewShadowNode.class;
    }

    @Override
    public SvgViewShadowNode createShadowNodeInstance() {
        SvgViewShadowNode node = new SvgViewShadowNode();
        node.setMeasureFunction(MEASURE_FUNCTION);
        return node;
    }

    @Override
    public void onDropViewInstance(SvgView view) {
        SvgInstancesManager.unregisterInstance(view.getId());
    }

    @Override
    protected SvgView createViewInstance(ThemedReactContext reactContext) {
        return new SvgView(reactContext);
    }

    @Override
    public void updateExtraData(SvgView root, Object extraData) {
        root.setBitmap((Bitmap) extraData);
    }

}