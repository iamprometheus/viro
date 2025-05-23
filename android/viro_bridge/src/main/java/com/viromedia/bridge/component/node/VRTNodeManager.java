//  Copyright © 2016 Viro Media. All rights reserved.
//
//  Permission is hereby granted, free of charge, to any person obtaining
//  a copy of this software and associated documentation files (the
//  "Software"), to deal in the Software without restriction, including
//  without limitation the rights to use, copy, modify, merge, publish,
//  distribute, sublicense, and/or sell copies of the Software, and to
//  permit persons to whom the Software is furnished to do so, subject to
//  the following conditions:
//
//  The above copyright notice and this permission notice shall be included
//  in all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
//  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
//  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
//  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
//  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.viromedia.bridge.component.node;

import android.provider.MediaStore;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.JavaOnlyMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.yoga.YogaConstants;
import com.viro.core.Material;
import com.viro.core.VideoTexture;
import com.viromedia.bridge.component.VRTComponent;
import com.viromedia.bridge.component.VRTViroViewGroupManager;
import com.viromedia.bridge.module.MaterialManager;
import com.viromedia.bridge.module.MaterialManager.MaterialWrapper;
import com.viromedia.bridge.utility.DynamicUtil;
import com.viromedia.bridge.utility.Helper;
import com.viromedia.bridge.utility.ViroEvents;
import com.viromedia.bridge.utility.ViroLog;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Abstract NodeManager for setting {@link VRTNode} Control properties.
 * NOTE: Always extend from this class for all Node Viro controls.
 */
public abstract class VRTNodeManager<T extends VRTNode> extends VRTViroViewGroupManager<T> {

    private static String TAG = VRTNodeManager.class.getSimpleName();

    public static final float s2DUnitPer3DUnit = 1000;
    private static final String WIDTH_NAME = "width";
    private static final String HEIGHT_NAME = "height";
    private static final String PADDING_NAME = "padding";
    private static final float[] DEFAULT_ZERO_VEC = new float[]{0,0,0};

    public VRTNodeManager(ReactApplicationContext context) {
        super(context);
    }

    @ReactProp(name = "position")
    public void setPosition(T view, ReadableArray position) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            // Skip property update for detached or torn down views
            return;
        }
        try {
            view.setPosition(Helper.toFloatArray(position, DEFAULT_ZERO_VEC));
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating position property: " + e.getMessage());
        }
    }

    @ReactProp(name = "rotation")
    public void setRotation(VRTNode view, ReadableArray rotation) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setRotation(Helper.toFloatArray(rotation, DEFAULT_ZERO_VEC));
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating rotation property: " + e.getMessage());
        }
    }

    @ReactProp(name = "scale")
    public void setScale(VRTNode view, ReadableArray scale) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setScale(Helper.toFloatArray(scale, new float[]{1,1,1}));
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating scale property: " + e.getMessage());
        }
    }

    @ReactProp(name = "rotationPivot")
    public void setRotationPivot(VRTNode view, ReadableArray scale) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setRotationPivot(Helper.toFloatArray(scale, DEFAULT_ZERO_VEC));
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating rotationPivot property: " + e.getMessage());
        }
    }

    @ReactProp(name = "scalePivot")
    public void setScalePivot(VRTNode view, ReadableArray scale) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setScalePivot(Helper.toFloatArray(scale, DEFAULT_ZERO_VEC));
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating scalePivot property: " + e.getMessage());
        }
    }

    @ReactProp(name = "opacity", defaultFloat = 1f)
    public void setOpacity(VRTNode view, float opacity) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setOpacity(opacity);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating opacity property: " + e.getMessage());
        }
    }

    @ReactProp(name = "visible", defaultBoolean = true)
    public void setVisible(VRTNode view, boolean visibility) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setVisible(visibility);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating visible property: " + e.getMessage());
        }
    }

    @ReactProp(name = "renderingOrder", defaultInt = 0)
    public void setRenderingOrder(VRTNode view, int renderingOrder) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setRenderingOrder(renderingOrder);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating renderingOrder property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canHover", defaultBoolean = VRTNode.DEFAULT_CAN_HOVER)
    public void setCanHover(VRTNode view, boolean canHover) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanHover(canHover);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canHover property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canClick", defaultBoolean = VRTNode.DEFAULT_CAN_CLICK)
    public void setCanClick(VRTNode view, boolean canClick) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanClick(canClick);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canClick property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canTouch", defaultBoolean = VRTNode.DEFAULT_CAN_TOUCH)
    public void setCanTouch(VRTNode view, boolean canTouch) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanTouch(canTouch);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canTouch property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canScroll", defaultBoolean = VRTNode.DEFAULT_CAN_SCROLL)
    public void setCanScroll(VRTNode view, boolean canScroll) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanScroll(canScroll);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canScroll property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canSwipe", defaultBoolean = VRTNode.DEFAULT_CAN_SWIPE)
    public void setCanSwipe(VRTNode view, boolean canSwipe) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanSwipe(canSwipe);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canSwipe property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canDrag", defaultBoolean = VRTNode.DEFAULT_CAN_DRAG)
    public void setCanDrag(VRTNode view, boolean canDrag) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanDrag(canDrag);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canDrag property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canFuse", defaultBoolean = VRTNode.DEFAULT_CAN_FUSE)
    public void setCanFuse(VRTNode view, boolean canFuse) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanFuse(canFuse);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canFuse property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canPinch", defaultBoolean = VRTNode.DEFAULT_CAN_PINCH)
    public void setCanPinch(VRTNode view, boolean canPinch) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanPinch(canPinch);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canPinch property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canRotate", defaultBoolean = VRTNode.DEFAULT_CAN_ROTATE)
    public void setCanRotate(VRTNode view, boolean canRotate) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanRotate(canRotate);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canRotate property: " + e.getMessage());
        }
    }

    @ReactProp(name = "timeToFuse", defaultFloat = VRTNode.DEFAULT_TIME_TO_FUSE_MILLIS)
    public void setTimeToFuse(VRTNode view, float durationMillis) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setTimeToFuse(durationMillis);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating timeToFuse property: " + e.getMessage());
        }
    }

    @ReactProp(name = "dragType")
    public void setDragType(VRTNode view, String dragType) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setDragType(dragType);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating dragType property: " + e.getMessage());
        }
    }

    @ReactProp(name = "dragPlane")
    public void setDragPlane(VRTNode view, ReadableMap dragPlane) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setDragPlane(dragPlane);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating dragPlane property: " + e.getMessage());
        }
    }

    @ReactProp(name = "animation")
    public void setAnimation(VRTNode view, @androidx.annotation.Nullable ReadableMap map) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setAnimation(map);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating animation property: " + e.getMessage());
        }
    }

    @ReactProp(name = "ignoreEventHandling", defaultBoolean = VRTNode.DEFAULT_IGNORE_EVENT_HANDLING)
    public void setIgnoreEventHandling(VRTNode view, boolean ignore) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setIgnoreEventHandling(ignore);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating ignoreEventHandling property: " + e.getMessage());
        }
    }

    @ReactProp(name = "materials")
    public void setMaterials(VRTNode view, @Nullable ReadableArray materials) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            // get material manager
            MaterialManager materialManager = getContext().getNativeModule(MaterialManager.class);

            ArrayList<Material> nativeMaterials = new ArrayList<>();
            if (materials != null) {
                for (int i = 0; i < materials.size(); i++) {
                    Material nativeMaterial = materialManager.getMaterial(materials.getString(i));
                    if (materialManager.isVideoMaterial(materials.getString(i))) {
                        if (!(nativeMaterial.getDiffuseTexture() instanceof VideoTexture)) {
                            // Recreate the material with the proper context.
                            if (view.getViroContext() != null) {
                                MaterialWrapper materialWrapper = materialManager.getMaterialWrapper(materials.getString(i));
                                VideoTexture videoTexture = new VideoTexture(view.getViroContext(), materialWrapper.getVideoTextureURI());
                                materialWrapper.recreate(videoTexture);
                                nativeMaterial = materialWrapper.getNativeMaterial();
                            }
                        }
                    }

                    if (nativeMaterial == null) {
                        throw new IllegalArgumentException("Material [" + materials.getString(i) + "] not found. Did you create it?");
                    }

                    nativeMaterials.add(nativeMaterial);
                }
            }
            view.setMaterials(nativeMaterials);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating materials property: " + e.getMessage());
        }
    }

    @ReactProp(name = "transformBehaviors")
    public void setTransformBehaviors(VRTNode view, @Nullable ReadableArray transformBehaviors) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            String[] behaviors = new String[0];
            if (transformBehaviors != null) {
                behaviors = new String[transformBehaviors.size()];
                for (int i = 0; i < transformBehaviors.size(); i++) {
                    behaviors[i] = transformBehaviors.getString(i);
                }
            }
            view.setTransformBehaviors(behaviors);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating transformBehaviors property: " + e.getMessage());
        }
    }

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        return new FlexEnabledShadowNode();
    }

    @Override
    public Class<? extends LayoutShadowNode> getShadowNodeClass() {
        return FlexEnabledShadowNode.class;
    }

    /**
     * This shadow node is so that views associated with FlexViews (and FlexViews themselves) have
     * their properties properly converted from 3D to 2D units. It's easiest if we just make all Nodes
     * have FlexEnabledShadowNodes, and the components can choose whether or not
     */
    protected class FlexEnabledShadowNode extends ViroLayoutShadowNode {
        private final String TAG = ViroLog.getTag(VRTNodeManager.class);

        @ReactProp(name = "width", defaultFloat = 1)
        public void setWidth(Dynamic width) {
            if (width.getType() == ReadableType.String) {
                super.setWidth(width);
            } else if (width.getType() == ReadableType.Number){
                JavaOnlyMap map = JavaOnlyMap.of(WIDTH_NAME, width.asDouble() * s2DUnitPer3DUnit);
                Dynamic newWidth = DynamicUtil.create(map, WIDTH_NAME);
                super.setWidth(newWidth);
            } else {
                ViroLog.warn(TAG, "Width is not of type Number or String. Doing nothing.");
            }
        }

        @ReactProp(name = "height", defaultFloat = 1)
        public void setHeight(Dynamic height) {
            if (height.getType() == ReadableType.String) {
                super.setHeight(height);
            } else if (height.getType() == ReadableType.Number) {
                JavaOnlyMap map = JavaOnlyMap.of(HEIGHT_NAME, height.asDouble() * s2DUnitPer3DUnit);
                Dynamic newHeight = DynamicUtil.create(map, HEIGHT_NAME);
                super.setHeight(newHeight);
            } else {
                ViroLog.warn(TAG, "Height is not of type Number or String. Doing nothing.");
            }
        }

        @ReactPropGroup(names = {
                ViewProps.PADDING,
                ViewProps.PADDING_VERTICAL,
                ViewProps.PADDING_HORIZONTAL,
                ViewProps.PADDING_LEFT,
                ViewProps.PADDING_RIGHT,
                ViewProps.PADDING_TOP,
                ViewProps.PADDING_BOTTOM,
        }, defaultFloat = YogaConstants.UNDEFINED)
        public void setPaddings(int index, Dynamic padding) {
            if (padding.getType() == ReadableType.String) {
                super.setPaddings(index, padding);
            } else if (padding.getType() == ReadableType.Number) {
                JavaOnlyMap map = JavaOnlyMap.of(PADDING_NAME, padding.asDouble() * s2DUnitPer3DUnit);
                Dynamic newPadding = DynamicUtil.create(map, PADDING_NAME);
                super.setPaddings(index, newPadding);
            } else {
                ViroLog.warn(TAG, "Padding is not of type Number of String. Doing nothing.");
            }
        }

        @ReactPropGroup(names = {
                ViewProps.BORDER_WIDTH,
                ViewProps.BORDER_LEFT_WIDTH,
                ViewProps.BORDER_RIGHT_WIDTH,
                ViewProps.BORDER_TOP_WIDTH,
                ViewProps.BORDER_BOTTOM_WIDTH,
        }, defaultFloat = YogaConstants.UNDEFINED)
        public void setBorderWidths(int index, float borderWidth) {
            super.setBorderWidths(index, borderWidth * s2DUnitPer3DUnit);
        }
    }

    /*
     The only evnets that should be defined in here are input/touch events that bubble up, that way
     we don't have to "nativePropOnly" a ton of events...
     */
    @Override
    public Map getExportedCustomDirectEventTypeConstants() {
        Map events = super.getExportedCustomDirectEventTypeConstants();

        events.put(ViroEvents.ON_HOVER, MapBuilder.of("registrationName", ViroEvents.ON_HOVER));
        events.put(ViroEvents.ON_CLICK, MapBuilder.of("registrationName", ViroEvents.ON_CLICK));
        events.put(ViroEvents.ON_TOUCH, MapBuilder.of("registrationName", ViroEvents.ON_TOUCH));
        events.put(ViroEvents.ON_SWIPE, MapBuilder.of("registrationName", ViroEvents.ON_SWIPE));
        events.put(ViroEvents.ON_SCROLL, MapBuilder.of("registrationName", ViroEvents.ON_SCROLL));
        events.put(ViroEvents.ON_FUSE, MapBuilder.of("registrationName", ViroEvents.ON_FUSE));
        events.put(ViroEvents.ON_PINCH, MapBuilder.of("registrationName", ViroEvents.ON_PINCH));
        events.put(ViroEvents.ON_ROTATE, MapBuilder.of("registrationName", ViroEvents.ON_ROTATE));
        events.put(ViroEvents.ON_DRAG, MapBuilder.of("registrationName", ViroEvents.ON_DRAG));
        events.put(ViroEvents.ON_COLLIDED, MapBuilder.of("registrationName", ViroEvents.ON_COLLIDED));
        events.put(ViroEvents.ON_TRANSFORM_DELEGATE, MapBuilder.of("registrationName", ViroEvents.ON_TRANSFORM_DELEGATE));
        events.put(ViroEvents.ON_ANIMATION_START, MapBuilder.of("registrationName", ViroEvents.ON_ANIMATION_START));
        events.put(ViroEvents.ON_ANIMATION_FINISH, MapBuilder.of("registrationName", ViroEvents.ON_ANIMATION_FINISH));

        return events;
    }

    @ReactProp(name = "physicsBody")
    public void setPhysicsBody(VRTNode view, ReadableMap map) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setPhysicsBody(map);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating physicsBody property: " + e.getMessage());
        }
    }

    @ReactProp(name = "canCollide", defaultBoolean = VRTNode.DEFAULT_CAN_FUSE)
    public void setCanCollide(VRTNode view, boolean canCollide) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setCanCollide(canCollide);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating canCollide property: " + e.getMessage());
        }
    }

    @ReactProp(name = "viroTag")
    public void setViroTag(VRTNode view, String tag) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setViroTag(tag);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating viroTag property: " + e.getMessage());
        }
    }

    @ReactProp(name = "hasTransformDelegate", defaultBoolean = false)
    public void setViroTag(VRTNode view, boolean hasDelegate) {
        if (view == null || view.isTornDown() || !view.isAttachedToWindow()) {
            return;
        }
        try {
            view.setOnNativeTransformDelegate(hasDelegate);
        } catch (Exception e) {
            ViroLog.error(TAG, "Error updating hasTransformDelegate property: " + e.getMessage());
        }
    }

}
