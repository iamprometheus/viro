"use strict";
/**
 * Copyright (c) 2015-present, Viro, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * @providesModule ViroCamera
 * @flow
 */
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || (function () {
    var ownKeys = function(o) {
        ownKeys = Object.getOwnPropertyNames || function (o) {
            var ar = [];
            for (var k in o) if (Object.prototype.hasOwnProperty.call(o, k)) ar[ar.length] = k;
            return ar;
        };
        return ownKeys(o);
    };
    return function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null) for (var k = ownKeys(mod), i = 0; i < k.length; i++) if (k[i] !== "default") __createBinding(result, mod, k[i]);
        __setModuleDefault(result, mod);
        return result;
    };
})();
Object.defineProperty(exports, "__esModule", { value: true });
exports.ViroCamera = void 0;
const React = __importStar(require("react"));
const react_native_1 = require("react-native");
const ViroSceneContext_1 = require("./ViroSceneContext");
class ViroCamera extends React.Component {
    _component;
    static contextType = ViroSceneContext_1.ViroSceneContext;
    componentDidMount() {
        this.context.cameraDidMount(this);
    }
    componentWillUnmount() {
        this.context.cameraWillUnmount(this);
    }
    componentDidUpdate(prevProps, _prevState) {
        if (prevProps.active != this.props.active) {
            this.context.cameraDidUpdate(this, this.props.active);
        }
    }
    setNativeProps = (nativeProps) => {
        this._component?.setNativeProps(nativeProps);
    };
    _onAnimationStart = (_event) => {
        this.props.animation &&
            this.props.animation.onStart &&
            this.props.animation.onStart();
    };
    _onAnimationFinish = (_event) => {
        this.props.animation &&
            this.props.animation.onFinish &&
            this.props.animation.onFinish();
    };
    render() {
        // Uncomment this to check props
        //checkMisnamedProps("ViroCamera", this.props);
        return (<VRTCamera ref={(component) => {
                this._component = component;
            }} {...this.props} onAnimationStartViro={this._onAnimationStart} onAnimationFinishViro={this._onAnimationFinish}/>);
    }
}
exports.ViroCamera = ViroCamera;
var VRTCamera = (0, react_native_1.requireNativeComponent)("VRTCamera", 
// @ts-ignore
ViroCamera, {
    nativeOnly: {
        scale: [1, 1, 1],
        materials: [],
        visible: true,
        canHover: true,
        canClick: true,
        canTouch: true,
        canScroll: true,
        canSwipe: true,
        canDrag: true,
        canPinch: true,
        canRotate: true,
        onPinchViro: true,
        onRotateViro: true,
        onHoverViro: true,
        onClickViro: true,
        onTouchViro: true,
        onScrollViro: true,
        onSwipeViro: true,
        onDragViro: true,
        transformBehaviors: true,
        canFuse: true,
        onFuseViro: true,
        timeToFuse: true,
        viroTag: true,
        scalePivot: true,
        rotationPivot: true,
        canCollide: true,
        onCollisionViro: true,
        onNativeTransformDelegateViro: true,
        hasTransformDelegate: true,
        physicsBody: true,
        dragType: true,
        onAnimationStartViro: true,
        onAnimationFinishViro: true,
        ignoreEventHandling: true,
        dragPlane: true,
        renderingOrder: true,
    },
});
