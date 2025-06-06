/**
 * Copyright (c) 2017-present, Viro Media, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * @providesModule ViroSpatialSound
 * @flow
 */
"use strict";
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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ViroSpatialSound = void 0;
const React = __importStar(require("react"));
const react_native_1 = require("react-native");
// @ts-ignore
const resolveAssetSource_1 = __importDefault(require("react-native/Libraries/Image/resolveAssetSource"));
const ViroProps_1 = require("./Utilities/ViroProps");
class ViroSpatialSound extends React.Component {
    _component = null;
    _onFinish = (event) => {
        this.props.onFinish && this.props.onFinish(event);
    };
    _onError = (event) => {
        this.props.onError && this.props.onError(event);
    };
    setNativeProps = (nativeProps) => {
        this._component?.setNativeProps(nativeProps);
    };
    render() {
        (0, ViroProps_1.checkMisnamedProps)("ViroSpatialSound", this.props);
        var soundSrc = this.props.source;
        if (typeof soundSrc === "number") {
            soundSrc = (0, resolveAssetSource_1.default)(soundSrc);
        }
        else if (typeof soundSrc === "string") {
            /**
             * @todo
             *
             * This throws a typescript error:
             * Type '{ name: never; }' is not assignable to type 'ImageSourcePropType'.
             * Object literal may only specify known properties, and 'name' does not
             * exist in type 'ImageURISource | ImageURISource[]'.
             *
             * I assume that this works correctly for Viro, but we would need to standardize
             * this or remove this usage. The usage should be {uri: string} or require format
             * to be consistent with images/video.
             */
            soundSrc = { name: soundSrc };
        }
        let nativeProps = Object.assign({}, this.props);
        nativeProps.source = soundSrc;
        nativeProps.onErrorViro = this._onError;
        nativeProps.onFinishViro = this._onFinish;
        nativeProps.ref = (component) => {
            this._component = component;
        };
        return <VRTSpatialSound {...nativeProps}/>;
    }
    getTransformAsync = async () => {
        return await react_native_1.NativeModules.VRTNodeModule.getNodeTransform((0, react_native_1.findNodeHandle)(this));
    };
    getBoundingBoxAsync = async () => {
        return await react_native_1.NativeModules.VRTNodeModule.getBoundingBox((0, react_native_1.findNodeHandle)(this));
    };
    seekToTime = (timeInSeconds) => {
        switch (react_native_1.Platform.OS) {
            case "ios":
                react_native_1.NativeModules.VRTSpatialSoundManager.seekToTime((0, react_native_1.findNodeHandle)(this), timeInSeconds);
                break;
            case "android":
                react_native_1.NativeModules.UIManager.dispatchViewManagerCommand((0, react_native_1.findNodeHandle)(this), react_native_1.NativeModules.UIManager.VRTSpatialSound.Commands.seekToTime, [timeInSeconds]);
                break;
        }
    };
}
exports.ViroSpatialSound = ViroSpatialSound;
var VRTSpatialSound = (0, react_native_1.requireNativeComponent)("VRTSpatialSound", 
// @ts-ignore
ViroSpatialSound, {
    nativeOnly: {
        onFinishViro: true,
        onErrorViro: true,
    },
});
